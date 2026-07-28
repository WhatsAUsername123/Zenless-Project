/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.materialcalculator.model

/**
 * Maps the raw HoYoLab `skillType` int (see MyAgentDetailSkillResponse) to a named skill.
 *
 * Codes confirmed directly from MyAgentSkillCard.kt's existing `skills.find { it.skillType == X }`
 * calls - this is not a guess, it mirrors what the app already renders.
 *
 * Note: Core Passive does NOT use the same 2-12 level range as the other five skills - it uses a
 * separate letter-tier progression (raw values 2-7 map to A-F, see MyAgentSkillCard's
 * convertNumberToDisplayChar). Its cost table must be looked up separately - see CostTables.kt.
 */
enum class SkillType(val code: Int) {
    BASIC(0),
    SPECIAL(1),
    DODGE(2),
    ULTIMATE(3),
    CORE_PASSIVE(5),
    QUICK_ASSIST(6);

    companion object {
        fun fromCode(code: Int?): SkillType? = entries.find { it.code == code }
    }
}
