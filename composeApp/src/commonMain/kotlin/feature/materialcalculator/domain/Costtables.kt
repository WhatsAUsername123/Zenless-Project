/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.materialcalculator.domain

import feature.materialcalculator.model.SkillType
import feature.materialcalculator.model.TieredCost
import utils.ZzzRarity

/**
 * Cost tables sourced from zenless.tools (per-agent structure confirmed universal - only material
 * identity varies by attribute/specialty, which MaterialLookup handles separately).
 *
 * Tier order for tierQuantities is always [low, mid, high] matching MaterialLookup's 3-material
 * lists, e.g. for ascension: [Basic, Advanced, Ruler/Arbiter].
 *
 * Still TODO: B/C/D-rank W-Engine Modify costs (only S-Rank and A-Rank were sourced so far), and
 * the real material ID for CORE_ENHANCEMENT_MATERIAL_ID in MaterialLookup.
 */
object CostTables {

    /** Ascension promotion breakpoints. Key = the level you're promoting TO. */
    private val ascensionCosts: Map<Int, TieredCost?> = mapOf(
        20 to TieredCost(denny = 24_000, tierQuantities = listOf(4, 0, 0)),
        30 to TieredCost(denny = 56_000, tierQuantities = listOf(0, 12, 0)),
        40 to TieredCost(denny = 120_000, tierQuantities = listOf(0, 20, 0)),
        50 to TieredCost(denny = 200_000, tierQuantities = listOf(0, 0, 10)),
        60 to TieredCost(denny = 400_000, tierQuantities = listOf(0, 0, 20))
    )

    /** Skill levels 2-12 (the level you're upgrading TO). Applies to Basic/Dodge/Assist/Special/
     * Chain - NOT Core Passive, which has its own table below. Level 12 also needs the per-agent
     * unique weekly boss material ("Hamster Cage Pass" for this particular agent). */
    private val skillCosts: Map<Int, TieredCost?> = mapOf(
        2 to TieredCost(denny = 2_000, tierQuantities = listOf(4, 0, 0)),
        3 to TieredCost(denny = 3_000, tierQuantities = listOf(6, 0, 0)),
        4 to TieredCost(denny = 6_000, tierQuantities = listOf(0, 2, 0)),
        5 to TieredCost(denny = 9_000, tierQuantities = listOf(0, 3, 0)),
        6 to TieredCost(denny = 12_000, tierQuantities = listOf(0, 4, 0)),
        7 to TieredCost(denny = 18_000, tierQuantities = listOf(0, 6, 0)),
        8 to TieredCost(denny = 45_000, tierQuantities = listOf(0, 0, 5)),
        9 to TieredCost(denny = 67_500, tierQuantities = listOf(0, 0, 8)),
        10 to TieredCost(denny = 90_000, tierQuantities = listOf(0, 0, 10)),
        11 to TieredCost(denny = 112_500, tierQuantities = listOf(0, 0, 12)),
        12 to TieredCost(
            denny = 135_000,
            tierQuantities = listOf(0, 0, 15),
            weeklyBossMaterialQuantity = 1
        )
    )

    /** Core Passive uses letter tiers (A-F), not numeric levels - raw values 2-7 map to A-F (see
     * MyAgentSkillCard's convertNumberToDisplayChar). Uses the universal core material
     * (CORE_ENHANCEMENT_MATERIAL_ID) instead of tiered chips, plus the per-agent weekly boss
     * material at the higher tiers. */
    private val corePassiveCosts: Map<Int, TieredCost?> = mapOf(
        2 to TieredCost(denny = 5_000, tierQuantities = listOf(0, 0, 0)), // A - no materials
        3 to TieredCost(denny = 12_000, tierQuantities = listOf(0, 0, 0), coreMaterialQuantity = 2), // B
        4 to TieredCost(denny = 28_000, tierQuantities = listOf(0, 0, 0), coreMaterialQuantity = 4), // C
        5 to TieredCost( // D
            denny = 60_000,
            tierQuantities = listOf(0, 0, 0),
            coreMaterialQuantity = 9,
            weeklyBossMaterialQuantity = 2
        ),
        6 to TieredCost( // E
            denny = 100_000,
            tierQuantities = listOf(0, 0, 0),
            coreMaterialQuantity = 15,
            weeklyBossMaterialQuantity = 3
        ),
        7 to TieredCost( // F
            denny = 200_000,
            tierQuantities = listOf(0, 0, 0),
            coreMaterialQuantity = 30,
            weeklyBossMaterialQuantity = 4
        )
    )

    /** W-Engine "Modify" (ascension-style). Cost DIFFERS by W-Engine rarity (S vs A confirmed so
     * far - B/C/D not sourced yet, hence the null fallback). Keyed by [rarity][targetLevel]. */
    private val weaponModifyCosts: Map<ZzzRarity, Map<Int, TieredCost?>> = mapOf(
        ZzzRarity.RARITY_S to mapOf(
            20 to TieredCost(denny = 12_000, tierQuantities = listOf(4, 0, 0)),
            30 to TieredCost(denny = 28_000, tierQuantities = listOf(0, 12, 0)),
            40 to TieredCost(denny = 60_000, tierQuantities = listOf(0, 20, 0)),
            50 to TieredCost(denny = 100_000, tierQuantities = listOf(0, 0, 10)),
            60 to TieredCost(denny = 200_000, tierQuantities = listOf(0, 0, 20))
        ),
        ZzzRarity.RARITY_A to mapOf(
            20 to TieredCost(denny = 9_600, tierQuantities = listOf(3, 0, 0)),
            30 to TieredCost(denny = 22_400, tierQuantities = listOf(0, 10, 0)),
            40 to TieredCost(denny = 48_000, tierQuantities = listOf(0, 16, 0)),
            50 to TieredCost(denny = 80_000, tierQuantities = listOf(0, 0, 8)),
            60 to TieredCost(denny = 160_000, tierQuantities = listOf(0, 0, 16))
        )
        // TODO: RARITY_B, RARITY_C, RARITY_D not sourced yet - weaponModifyCost() returns null
        // for those ranks until added here, which correctly flags the result as incomplete.
    )

    fun ascensionCost(targetLevel: Int): TieredCost? = ascensionCosts[targetLevel]

    fun skillCost(skillType: SkillType, targetLevel: Int): TieredCost? =
        if (skillType == SkillType.CORE_PASSIVE) corePassiveCosts[targetLevel] else skillCosts[targetLevel]

    fun weaponModifyCost(rarity: ZzzRarity, targetLevel: Int): TieredCost? =
        weaponModifyCosts[rarity]?.get(targetLevel)

    /** Every level at which an ascension/weapon-modify breakpoint occurs. Used to know which
     * levels to sum over between current and target. */
    val ascensionBreakpoints: List<Int> = listOf(20, 30, 40, 50, 60)
}
