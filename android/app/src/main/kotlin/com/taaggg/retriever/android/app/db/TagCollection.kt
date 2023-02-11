package com.taaggg.retriever.android.app.db

import com.taaggg.retriever.common.storekit.db.Collection
import com.taaggg.retriever.common.storekit.entities.note.Tag
import javax.inject.Inject


class TagCollection @Inject constructor() : Collection<Tag> {
    private val map = mutableMapOf<String, Tag>()

    init {
        map["1"] = RetrieverDatabase.Skiing
        map["2"] = RetrieverDatabase.BlackCrows
        map["3"] = RetrieverDatabase.ServerDrivenUi
        map["4"] = RetrieverDatabase.KotlinMultiPlatform
        map["5"] = RetrieverDatabase.ComponentBox
        map["6"] = RetrieverDatabase.Store
    }

    override fun find() = map.values.toList()
    override fun findById(id: String) = map[id]
    override fun find(tag: String) = find().filter { it.name == tag }
}

