/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.materialcalculator.domain

import utils.AgentAttribute
import utils.AgentSpecialty

/**
 * Material ID lookups. These IDs are pulled directly from the existing mapping in
 * feature/agent/data/mapper/AgentsListMapper.kt - not re-derived, just reused so both places stay
 * in sync. If you ever change one, change the other (or better, refactor both to call this object).
 */
object MaterialLookup {

    /**
     * "Higher Dimensional Data" - the universal Core Passive material, same for every agent
     * regardless of attribute/specialty. PLACEHOLDER - fill in the real asset ID from your
     * material catalog (ZZZ-Archive-Asset). Left null on purpose so the calculator flags
     * incomplete data instead of guessing a wrong ID.
     */
    val CORE_ENHANCEMENT_MATERIAL_ID: Int? = null // TODO: source the real material ID

    /** Skill-upgrade materials are keyed by ATTRIBUTE (element), not specialty. */
    fun skillMaterialIds(attribute: AgentAttribute): List<Int> = when (attribute) {
        AgentAttribute.Physical -> listOf(53, 54, 55)
        AgentAttribute.Fire -> listOf(56, 57, 58)
        AgentAttribute.Ice -> listOf(59, 60, 61)
        AgentAttribute.Electric -> listOf(62, 63, 64)
        AgentAttribute.Ether -> listOf(65, 66, 67)
        AgentAttribute.None -> emptyList()
    }

    /** Ascension materials are keyed by SPECIALTY (combat style). */
    fun ascensionMaterialIds(specialty: AgentSpecialty): List<Int> = when (specialty) {
        AgentSpecialty.Attack -> listOf(17, 18, 19)
        AgentSpecialty.Stun -> listOf(20, 21, 22)
        AgentSpecialty.Anomaly -> listOf(23, 24, 25)
        AgentSpecialty.Support -> listOf(26, 27, 28)
        AgentSpecialty.Defense -> listOf(29, 30, 31)
        AgentSpecialty.Rupture -> listOf(69, 70, 71)
        AgentSpecialty.None -> emptyList()
    }

    /** W-Engine Modify materials are also keyed by SPECIALTY. */
    fun wEngineMaterialIds(specialty: AgentSpecialty): List<Int> = when (specialty) {
        AgentSpecialty.Attack -> listOf(32, 33, 34)
        AgentSpecialty.Stun -> listOf(35, 36, 37)
        AgentSpecialty.Anomaly -> listOf(38, 39, 40)
        AgentSpecialty.Support -> listOf(41, 42, 43)
        AgentSpecialty.Defense -> listOf(44, 45, 46)
        AgentSpecialty.Rupture -> listOf(72, 73, 74)
        AgentSpecialty.None -> emptyList()
    }

    /**
     * True when a synced agent's attribute or specialty didn't resolve to a known value.
     * This should never happen for a real agent - every agent has both - so treat it as a signal
     * that HoYoLab returned something your enums don't cover yet (e.g. a new special attribute),
     * not as "this agent has no materials". Surface a warning in the UI rather than showing 0.
     */
    fun isMaterialDataIncomplete(attribute: AgentAttribute, specialty: AgentSpecialty): Boolean =
        attribute == AgentAttribute.None || specialty == AgentSpecialty.None
}
