package ai.wandering.retriever.common.storekit.store.single

import ai.wandering.retriever.common.storekit.LocalChannel
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.rest.single.ChannelRestApi
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asOutput
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Updater

class ChannelStoreProvider(private val api: ChannelRestApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, Channel.Network, Channel.Output, LocalChannel, Boolean> {
    override fun provideConverter(): Converter<Channel.Network, Channel.Output, LocalChannel> = Converter.Builder<Channel.Network, Channel.Output, LocalChannel>()
        .fromNetworkToOutput { network -> network.asOutput() }
        .fromOutputToLocal { output -> output.asLocal() }
        .fromLocalToOutput { local -> local.asOutput() }
        .build()

    override fun provideFetcher(): Fetcher<String, Channel.Network> = Fetcher.of {
        TODO()
    }

    override fun provideBookkeeper(): Bookkeeper<String> {
        TODO("Not yet implemented")
    }

    override fun provideSot(): SourceOfTruth<String, LocalChannel> {
        TODO("Not yet implemented")
    }

    override fun provideUpdater(): Updater<String, Channel.Output, Boolean> {
        TODO("Not yet implemented")
    }

    override fun provideMutableStore(): MutableStore<String, Channel.Output> {
        TODO("Not yet implemented")
    }

}