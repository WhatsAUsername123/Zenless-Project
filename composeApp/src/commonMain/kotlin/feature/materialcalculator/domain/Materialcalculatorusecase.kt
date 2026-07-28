/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.materialcalculator.domain

import feature.materialcalculator.model.AgentUpgradeRequest
import feature.materialcalculator.model.MaterialCalculationResult
import feature.materialcalculator.model.MaterialRequirement
import feature.materialcalculator.model.TieredCost
import feature.materialcalculator.model.WeaponUpgradeRequest
import utils.AgentAttribute
import utils.AgentSpecialty
import utils.ZzzRarity

/**
 * Turns a current->target request into a total material/Denny cost, using the agent's already-
 * synced attribute/specialty (from MyAgentDetail) to resolve which real materials apply.
 *
 * No repository/network needed here - this is pure calculation over static tables, which is why
 * it isn't wired into UseCaseModule.kt as a `single` yet. Add it there once the ViewModel that
 * calls this exists (feature.materialcalculator.presentation.MaterialCalculatorViewModel).
 *
 * IMPORTANT GAP: `weeklyMaterialId` (the per-agent unique boss-drop material needed at skill
 * level 12 and Core Passive D/E/F) is NOT currently available on MyAgentDetail - it only exists
 * on the static AgentListItem/AgentsListItemEntity from the agent gallery feature. Until
 * MyAgentDetail carries this (or you cross-reference by agent id against the local Agent DB),
 * callers must pass it in manually - see the null-handling below, which flags the result as
 * incomplete rather than silently omitting that material.
 */
class MaterialCalculatorUseCase {

    fun calculateAgentUpgrade(
        request: AgentUpgradeRequest,
        specialty: AgentSpecialty,
        attribute: AgentAttribute,
        weeklyMaterialId: Int?
    ): MaterialCalculationResult {
        val ascensionMaterialIds = MaterialLookup.ascensionMaterialIds(specialty)
        val skillMaterialIds = MaterialLookup.skillMaterialIds(attribute)
        var incomplete = MaterialLookup.isMaterialDataIncomplete(attribute, specialty)

        val ascensionSteps = CostTables.ascensionBreakpoints
            .filter { it > request.currentLevel && it <= request.targetLevel }
            .map { level -> CostTables.ascensionCost(level) }

        val skillSteps = request.targetSkillLevels.flatMap { (skillType, targetLevel) ->
            val currentLevel = request.currentSkillLevels[skillType] ?: 0
            ((currentLevel + 1)..targetLevel).map { level -> CostTables.skillCost(skillType, level) }
        }

        val (ascensionCosts, ascensionIncomplete) =
            resolveCosts(ascensionSteps, ascensionMaterialIds, weeklyMaterialId)
        val (skillCosts, skillIncomplete) =
            resolveCosts(skillSteps, skillMaterialIds, weeklyMaterialId)
        incomplete = incomplete || ascensionIncomplete || skillIncomplete

        return combine(ascensionCosts + skillCosts, incomplete)
    }

    fun calculateWeaponUpgrade(
        request: WeaponUpgradeRequest,
        specialty: AgentSpecialty,
        rarity: ZzzRarity
    ): MaterialCalculationResult {
        val materialIds = MaterialLookup.wEngineMaterialIds(specialty)
        var incomplete = specialty == AgentSpecialty.None

        val steps = CostTables.ascensionBreakpoints
            .filter { it > request.currentLevel && it <= request.targetLevel }
            .map { level -> CostTables.weaponModifyCost(rarity, level) }

        val (costs, stepsIncomplete) = resolveCosts(steps, materialIds, weeklyMaterialId = null)
        incomplete = incomplete || stepsIncomplete

        return combine(costs, incomplete)
    }

    /** Resolves a list of (possibly-null, tier-indexed) costs into real MaterialRequirements.
     * A null STEP means that step's data hasn't been sourced yet - it's skipped from the total
     * and flips `incomplete` to true rather than silently under-counting. A step that NEEDS the
     * weekly boss material but has no `weeklyMaterialId` available does the same. */
    private fun resolveCosts(
        steps: List<TieredCost?>,
        materialIds: List<Int>,
        weeklyMaterialId: Int?
    ): Pair<List<UpgradeCostResolved>, Boolean> {
        var incomplete = false
        val resolved = steps.mapNotNull { step ->
            if (step == null) {
                incomplete = true
                return@mapNotNull null
            }
            val tierMaterials = step.tierQuantities.mapIndexedNotNull { index, quantity ->
                val id = materialIds.getOrNull(index)
                if (id == null || quantity == 0) null else MaterialRequirement(id, quantity)
            }
            val coreMaterial = if (step.coreMaterialQuantity > 0) {
                val id = MaterialLookup.CORE_ENHANCEMENT_MATERIAL_ID
                if (id == null) {
                    incomplete = true
                    null
                } else {
                    MaterialRequirement(id, step.coreMaterialQuantity)
                }
            } else {
                null
            }
            val weeklyMaterial = if (step.weeklyBossMaterialQuantity > 0) {
                if (weeklyMaterialId == null) {
                    incomplete = true
                    null
                } else {
                    MaterialRequirement(weeklyMaterialId, step.weeklyBossMaterialQuantity)
                }
            } else {
                null
            }
            UpgradeCostResolved(step.denny, listOfNotNull(coreMaterial, weeklyMaterial) + tierMaterials)
        }
        return resolved to incomplete
    }

    private fun combine(costs: List<UpgradeCostResolved>, incomplete: Boolean): MaterialCalculationResult {
        val totalDenny = costs.sumOf { it.denny }
        val totalMaterials = costs.flatMap { it.materials }
            .groupBy { it.materialId }
            .map { (id, reqs) -> MaterialRequirement(id, reqs.sumOf { it.quantity }) }
        return MaterialCalculationResult(totalDenny, totalMaterials, incomplete)
    }

    private data class UpgradeCostResolved(val denny: Int, val materials: List<MaterialRequirement>)
}
