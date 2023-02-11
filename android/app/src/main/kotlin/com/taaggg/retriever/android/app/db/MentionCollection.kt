package com.taaggg.retriever.android.app.db

import com.taaggg.retriever.common.storekit.db.Collection
import com.taaggg.retriever.common.storekit.entities.note.Mention
import javax.inject.Inject


class MentionCollection @Inject constructor() : Collection<Mention> {
    private val map = mutableMapOf<String, Mention>()

    init {
        map["1"] = RetrieverDatabase.Tag
        map["2"] = RetrieverDatabase.Trot
        map["3"] = RetrieverDatabase.Tugg
    }

    override fun find() = map.values.toList()
    override fun findById(id: String) = map[id]
    override fun find(tag: String) = find().filter { it.name == tag }
}