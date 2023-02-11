package com.taaggg.retriever.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.retriever.android.common.scoping.AppScope
import com.taaggg.retriever.common.storekit.api.NotesApi
import com.taaggg.retriever.common.storekit.db.Collection
import com.taaggg.retriever.common.storekit.entities.note.Mention
import com.taaggg.retriever.common.storekit.entities.note.Note
import com.taaggg.retriever.common.storekit.entities.note.Tag
import com.taaggg.retriever.common.storekit.repository.AuthRepository
import javax.inject.Named

@ContributesTo(AppScope::class)
interface AppDependencies {
    val authRepository: AuthRepository
    val api: NotesApi

    @Named("NoteCollection")
    fun noteCollection(): Collection<Note>

    @Named("TagCollection")
    fun tagCollection(): Collection<Tag>

    @Named("MentionCollection")
    fun mentionCollection(): Collection<Mention>
}