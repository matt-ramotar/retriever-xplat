package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.api.auth.AuthApi
import ai.wandering.retriever.common.storekit.networking.HttpClientProvider
import ai.wandering.retriever.common.storekit.api.RealRetrieverApi
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.repository.AuthRepository
import ai.wandering.retriever.common.storekit.repository.RealAuthRepository
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
object AppModule {

    private val httpClient = HttpClientProvider().provide()

    @Provides
    fun provideRetrieverApi(socket: Socket): RetrieverApi = RealRetrieverApi(httpClient, socket)

    @Provides
    fun provideAuthApi(socket: Socket): AuthApi = RealRetrieverApi(httpClient, socket)

    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository = RealAuthRepository(api)

}
