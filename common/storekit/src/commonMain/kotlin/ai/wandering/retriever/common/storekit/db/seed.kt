package ai.wandering.retriever.common.storekit.db

import ai.wandering.retriever.common.storekit.RetrieverDatabase

fun RetrieverDatabase.seed() {
    Seeds.users.forEach { localUserQueries.upsert(it) }
    Seeds.tags.forEach { localTagQueries.upsert(it) }
    Seeds.mentions.forEach { localMentionQueries.upsert(it) }
    Seeds.notes.forEach { localNoteQueries.upsert(it) }
    Seeds.graphs.forEach { localGraphQueries.upsert(it) }
    Seeds.noteChannels.forEach { noteChannelQueries.upsert(it) }
    Seeds.noteMentions.forEach { noteMentionQueries.upsert(it) }
    Seeds.userFollowingTags.forEach { userFollowingTagQueries.upsert(it) }
    Seeds.userFollowingGraphs.forEach { userFollowingGraphQueries.upsert(it) }
    Seeds.userFollowingUsers.forEach { userFollowingUserQueries.upsert(it) }
    Seeds.userPinnedGraphs.forEach { userPinnedGraphQueries.upsert(it) }
    Seeds.userPinnedChannels.forEach { userPinnedChannelQueries.upsert(it) }
    Seeds.userPinnedNotes.forEach { userPinnedNoteQueries.upsert(it) }
}