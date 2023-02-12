package ai.wandering.retriever.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.storekit.api.AuthApi
import ai.wandering.retriever.common.storekit.api.HttpClientProvider
import ai.wandering.retriever.common.storekit.api.NotesApi
import ai.wandering.retriever.common.storekit.api.RealNotesApi
import ai.wandering.retriever.common.storekit.repository.AuthRepository
import ai.wandering.retriever.common.storekit.repository.RealAuthRepository
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
