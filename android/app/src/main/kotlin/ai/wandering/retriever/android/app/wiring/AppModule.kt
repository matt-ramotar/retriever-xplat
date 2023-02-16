package ai.wandering.retriever.android.app.wiring

import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.common.storekit.api.AuthApi
import ai.wandering.retriever.common.storekit.api.HttpClientProvider
import ai.wandering.retriever.common.storekit.api.RealRetrieverApi
import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.api.RetrieverSocket
import ai.wandering.retriever.common.storekit.api.Socket
import ai.wandering.retriever.common.storekit.repository.AuthRepository
import ai.wandering.retriever.common.storekit.repository.RealAuthRepository
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dev.icerock.moko.socket.SocketEvent
import dev.icerock.moko.socket.Socket as Io
@Module
@ContributesTo(AppScope::class)
object AppModule {

    private val httpClient = HttpClientProvider().provide()

    @Provides
    fun provideRetrieverApi(): RetrieverApi = RealRetrieverApi(httpClient)

    @Provides
    fun provideAuthApi(): AuthApi = RealRetrieverApi(httpClient)

    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository = RealAuthRepository(api)

    @Provides
    fun provideSocket(): Socket = RetrieverSocket()
}
