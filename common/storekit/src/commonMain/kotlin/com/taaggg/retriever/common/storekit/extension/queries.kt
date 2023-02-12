package com.taaggg.retriever.common.storekit.extension

import com.taaggg.retriever.common.storekit.LocalNoteQueries
import com.taaggg.retriever.common.storekit.LocalTagQueries
import com.taaggg.retriever.common.storekit.LocalUser
import com.taaggg.retriever.common.storekit.LocalUserQueries

fun LocalTagQueries.getAllAsList() = getAll().executeAsList()
fun LocalNoteQueries.findById(id: String) = getById(id).executeAsOne()
fun LocalUserQueries.getAllNotes(id: String) =getNotesByUserId(id).executeAsOne()