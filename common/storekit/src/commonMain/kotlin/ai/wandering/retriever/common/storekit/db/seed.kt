package ai.wandering.retriever.common.storekit.db

import ai.wandering.retriever.common.storekit.RetrieverDatabase

fun RetrieverDatabase.seed() {
    Seeds.users.forEach { localUserQueries.upsert(it) }
    Seeds.tags.forEach { localTagQueries.upsert(it) }
    Seeds.mentions.forEach { localMentionQueries.upsert(it) }
    Seeds.notes.forEach { localNoteQueries.upsert(it) }
    Seeds.noteTags.forEach { noteTagQueries.upsert(it) }
    Seeds.noteMentions.forEach { noteMentionQueries.upsert(it) }
}