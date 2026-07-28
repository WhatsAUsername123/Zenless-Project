/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.materialcalculator.model

/** A single material and how many of it are needed. materialId matches the existing asset IDs
 * used in AgentsListMapper.kt / getMaterialUrl(id). */
data class MaterialRequirement(val materialId: Int, val quantity: Int)

/** The cost of a single upgrade step (e.g. one ascension breakpoint, or one skill level). */
data class UpgradeCost(val denny: Int, val materials: List<MaterialRequirement>)

/**
 * A tiered cost as sourced from a table (e.g. zenless.tools), BEFORE it's resolved to real
 * material IDs. `tierQuantities` is index-aligned to MaterialLookup's 3-material lists
 * (index 0 = low tier, 1 = mid, 2 = high) - e.g. listOf(0, 4, 2) means "0 low, 4 mid, 2 high".
 *
 * Some steps also need materials OUTSIDE the tiered system:
 * - `coreMaterialQuantity`: a universal material used by Core Passive upgrades ("Higher
 *   Dimensional Data"), same material regardless of the agent's attribute/specialty.
 * - `weeklyBossMaterialQuantity`: a material that's UNIQUE PER AGENT (e.g. a specific boss drop),
 *   needed at max skill level and the higher Core Passive tiers. This is the same concept as
 *   `weeklyMaterialId` on the static AgentListItem - see the note in MaterialCalculatorUseCase
 *   about MyAgentDetail not currently carrying that ID.
 *
 * MaterialCalculatorUseCase resolves all of this into an UpgradeCost with real material IDs once
 * it knows the agent's attribute/specialty (and, for the weekly material, its unique ID).
 */
data class TieredCost(
    val denny: Int,
    val tierQuantities: List<Int>,
    val coreMaterialQuantity: Int = 0,
    val weeklyBossMaterialQuantity: Int = 0
)

/** Aggregated result across every step between current and target. */
data class MaterialCalculationResult(
    val totalDenny: Int,
    val totalMaterials: List<MaterialRequirement>,
    /**
     * True when the agent/weapon's specialty or attribute resolved to None, or the requested
     * level/skill range isn't covered by the cost tables yet. Surface a warning in the UI when
     * this is true instead of silently showing a total of 0 - see the None-guard discussion.
     */
    val isIncomplete: Boolean
)

/** What the user wants to calculate for a single agent - current values come pre-filled from the
 * synced MyAgentDetail, target values are picked by the user in the UI. */
data class AgentUpgradeRequest(
    val currentLevel: Int,
    val targetLevel: Int,
    val currentSkillLevels: Map<SkillType, Int>,
    val targetSkillLevels: Map<SkillType, Int>
)

/** Same shape for a W-Engine. `star` (refinement) is intentionally excluded - it's duplicate-based,
 * not material-based, so it has no place in a cost calculation. */
data class WeaponUpgradeRequest(val currentLevel: Int, val targetLevel: Int)
