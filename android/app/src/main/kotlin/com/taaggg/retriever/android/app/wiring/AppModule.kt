package com.taaggg.retriever.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.retriever.android.app.db.MentionCollection
import com.taaggg.retriever.android.app.db.NoteCollection
import com.taaggg.retriever.android.app.db.TagCollection
import com.taaggg.retriever.android.common.scoping.AppScope
import com.taaggg.retriever.android.common.scoping.SingleIn
import com.taaggg.retriever.common.storekit.api.AuthApi
import com.taaggg.retriever.common.storekit.api.HttpClientProvider
import com.taaggg.retriever.common.storekit.api.NotesApi
import com.taaggg.retriever.common.storekit.api.RealNotesApi
import com.taaggg.retriever.common.storekit.db.Collection
import com.taaggg.retriever.common.storekit.entities.note.Mention
import com.taaggg.retriever.common.storekit.entities.note.Note
import com.taaggg.retriever.common.storekit.entities.note.Tag
import com.taaggg.retriever.common.storekit.repository.AuthRepository
import com.taaggg.retriever.common.storekit.repository.RealAuthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
@ContributesTo(AppScope::class)
object AppModule {

    private val httpClient = HttpClientProvider().provide()

    @Provides
    fun provideNotesApi(): NotesApi = RealNotesApi(httpClient)

    @Provides
    fun provideAuthApi(): AuthApi = RealNotesApi(httpClient)

    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository = RealAuthRepository(api)

    @Provides
    @SingleIn(AppScope::class)
    @Named("MentionCollection")
    fun provideMentionCollection(): Collection<Mention> = MentionCollection()

    @Provides
    @SingleIn(AppScope::class)
    @Named("TagCollection")
    fun provideTagCollection(): Collection<Tag> = TagCollection()

    @Provides
    @SingleIn(AppScope::class)
    @Named("NoteCollection")
    fun provideNoteCollection(): Collection<Note> = NoteCollection()
}
