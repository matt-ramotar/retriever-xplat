package com.taaggg.notes.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import com.taaggg.notes.android.common.scoping.AppScope
import com.taaggg.notes.common.storekit.api.AuthApi
import com.taaggg.notes.common.storekit.api.HttpClientProvider
import com.taaggg.notes.common.storekit.api.NotesApi
import com.taaggg.notes.common.storekit.api.RealNotesApi
import com.taaggg.notes.common.storekit.repository.AuthRepository
import com.taaggg.notes.common.storekit.repository.RealAuthRepository
import dagger.Module
import dagger.Provides

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
}
