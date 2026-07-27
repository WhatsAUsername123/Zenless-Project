/*
 * Copyright 2025 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.forum.data.mapper

import feature.forum.model.AllForumResponse
import feature.forum.model.AllForumState
import feature.forum.model.BahamutForumListItemResponse
import feature.forum.model.BahamutForumListState
import feature.forum.model.NgaForumListItemResponse
import feature.forum.model.NgaForumListState
import feature.forum.model.PttForumListItemResponse
import feature.forum.model.PttForumListState
import feature.forum.model.RedditForumListItem
import feature.forum.model.RedditForumListState
import feature.forum.model.TwitterForumListItemResponse
import feature.forum.model.TwitterForumListState

fun AllForumResponse.toAllForumState(): AllForumState = AllForumState(
    reddit = reddit?.map { it.toRedditForumListState() } ?: emptyList(),
    bahamut = bahamut?.map { it.toBahamutForumListState() } ?: emptyList(),
    ptt = ptt?.map { it.toPttForumListState() } ?: emptyList(),
    twitter = twitter?.map { it.toTwitterForumListState() } ?: emptyList(),
    nga = nga?.map { it.toNgaForumListState() } ?: emptyList()
)

fun RedditForumListItem.toRedditForumListState(): RedditForumListState = RedditForumListState(
    title = title.orEmpty(),
    link = link.orEmpty(),
    imgUrl = image.orEmpty(),
    upVotes = upVotes.toString(),
    comments = comments.toString(),
    score = score.toString()
)

fun BahamutForumListItemResponse.toBahamutForumListState(): BahamutForumListState = BahamutForumListState(
    title = splitBahamutText(title.orEmpty()).second,
    category = splitBahamutText(title.orEmpty()).first,
    link = link.orEmpty(),
    imgUrl = imgSrc.orEmpty(),
    gp = gp.toString()
)

fun PttForumListItemResponse.toPttForumListState(): PttForumListState = PttForumListState(
    link = link.orEmpty(),
    title = title.orEmpty(),
    popularity = if ((popularity ?: 0) >= 100) "爆" else popularity.toString(),
    source = source.orEmpty()
)

fun TwitterForumListItemResponse.toTwitterForumListState(): TwitterForumListState = TwitterForumListState(
    title = title.orEmpty(),
    link = link.orEmpty(),
    imgUrl = image.orEmpty(),
    author = author.orEmpty(),
    authorUrl = authorProfile.orEmpty(),
    hashtag = hashtag.orEmpty()
)

fun NgaForumListItemResponse.toNgaForumListState(): NgaForumListState = NgaForumListState(
    title = title.orEmpty(),
    link = link.orEmpty(),
    replies = replies.toString()
)

fun splitBahamutText(input: String): Pair<String, String> {
    val regex = Regex("【(.*?)】(.*)")
    val matchResult = regex.find(input)
    return if (matchResult != null) {
        val (category, content) = matchResult.destructured
        category to content.trim()
    } else {
        "" to input
    }
}
