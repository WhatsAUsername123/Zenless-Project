/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.materialcalculator.domain

import feature.hoyolab.model.agent.MyAgentDetailSkill
import feature.materialcalculator.model.SkillType

/** Converts the already-synced skills list (raw Int skillType) into the calculator's typed map.
 * Skills with an unrecognized code are dropped rather than crashing - if a new skill type ever
 * appears, this silently ignores it, so it's worth logging/asserting on in debug builds if you
 * want to catch that early. */
fun List<MyAgentDetailSkill>.toSkillLevelMap(): Map<SkillType, Int> =
    mapNotNull { skill -> SkillType.fromCode(skill.skillType)?.let { type -> type to skill.level } }
        .toMap()
