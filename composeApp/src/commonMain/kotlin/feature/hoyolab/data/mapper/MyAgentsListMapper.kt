/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.hoyolab.data.mapper

import feature.hoyolab.model.MyAgentListItem
import feature.hoyolab.model.MyAgentListItemResponse
import utils.findAgentAttributeFromHoYoLab
import utils.findAgentSpecialtyFromHoYoLab
import utils.findRarityFromHoYoLab

fun MyAgentListItemResponse.toMyAgentListItem(): MyAgentListItem = MyAgentListItem(
    id = id ?: 0,
    name = nameMi18n.orEmpty(),
    level = level ?: 0,
    rank = rank ?: 0,
    imageUrl = roleSquareUrl.orEmpty(),
    rarity = findRarityFromHoYoLab(rarity.orEmpty()),
    specialty = findAgentSpecialtyFromHoYoLab(avatarProfession ?: 0),
    attribute = findAgentAttributeFromHoYoLab(elementType ?: 0)
)
