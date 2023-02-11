package com.taaggg.retriever.android.app.db

import com.taaggg.retriever.common.storekit.db.Collection
import com.taaggg.retriever.common.storekit.entities.note.Note

class NoteCollection : Collection<Note> {
    private val map = mutableMapOf<String, Note>()

    init {
        map["1"] = RetrieverDatabase.SkiedKillingtonWithTag
        map["2"] = RetrieverDatabase.WorkingOnComponentBox
        map["3"] = RetrieverDatabase.WorkingOnStore
    }

    override fun find() = map.values.toList()
    override fun findById(id: String) = map[id]
    override fun find(tag: String) = find().filter { note -> note.tags.any { it.name == tag } }
}

