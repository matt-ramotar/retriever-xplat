package ai.wandering.retriever.common.storekit.store.single.channel

import ai.wandering.retriever.common.storekit.LocalChannel
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.rest.single.ChannelRestApi
import ai.wandering.retriever.common.storekit.bookkeeper.ChannelBookkeeper
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asNodeOutput
import ai.wandering.retriever.common.storekit.converter.asPopulatedOutput
import ai.wandering.retriever.common.storekit.db.queries.channel.asPopulated
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.result.RequestResult
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import kotlinx.coroutines.flow.channelFlow
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult

class NodeChannelStoreProvider(private val api: ChannelRestApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, Channel.Network.Populated, Channel.Output.Populated, LocalChannel, Boolean> {
    override fun provideConverter(): Converter<Channel.Network.Populated, Channel.Output.Populated, LocalChannel> =
        Converter.Builder<Channel.Network.Populated, Channel.Output.Populated, LocalChannel>()
            .fromNetworkToOutput { network ->
                val output = network.asPopulatedOutput()

                output
            }
            .fromOutputToLocal { output ->
                val local = output.asLocal()

                local
            }
            .fromLocalToOutput { local ->
                try {
                    val output = db.localChannelQueries.asPopulated(local.id)


                    output
                } catch (error: Throwable) {

                    TODO()
                }
            }
            .build()

    override fun provideFetcher(): Fetcher<String, Channel.Network.Populated> = Fetcher.of { channelId ->
        when (val result = api.get(channelId)) {
            is RequestResult.Exception -> TODO()
            is RequestResult.Success -> {

                result.data
            }
        }
    }

    override fun provideBookkeeper(): Bookkeeper<String> = Bookkeeper.by(
        getLastFailedSync = { id -> db.channelBookkeeperQueries.findById(id).executeAsOneOrNull()?.timestamp },
        setLastFailedSync = { id, timestamp ->
            try {
                db.channelBookkeeperQueries.upsert(ChannelBookkeeper(id, timestamp))
                true
            } catch (_: Throwable) {
                false
            }
        },
        clear = { id ->
            try {
                db.channelBookkeeperQueries.clearById(id)
                true
            } catch (_: Throwable) {
                false
            }
        },
        clearAll = {
            try {
                db.channelBookkeeperQueries.clearAll()
                true
            } catch (_: Throwable) {
                false
            }
        }
    )


    override fun provideSot(): SourceOfTruth<String, LocalChannel> = SourceOfTruth.of(
        reader = { channelId ->
            channelFlow {
                try {
                    send(db.localChannelQueries.findById(channelId).executeAsOneOrNull())
                } catch (error: Throwable) {

                }
            }
        },
        writer = { _, input -> db.localChannelQueries.upsert(input) },
        delete = { channelId -> db.localChannelQueries.clearById(channelId) },
        deleteAll = { db.localChannelQueries.clearAll() }
    )

    override fun provideUpdater(): Updater<String, Channel.Output.Populated, Boolean> = Updater.by(
        post = { _, input ->
            when (val result = api.upsert(input.asNodeOutput())) {
                is RequestResult.Exception -> UpdaterResult.Error.Exception(result.error)
                is RequestResult.Success -> UpdaterResult.Success.Typed(true)
            }
        }
    )

    override fun provideMutableStore(): MutableStore<String, Channel.Output.Populated> = StoreBuilder
        .from<String, Channel.Network.Populated, Channel.Output.Populated, LocalChannel>(
            fetcher = provideFetcher(),
            sourceOfTruth = provideSot()
        )
        .converter(
            converter = provideConverter()
        )
        .build(
            updater = provideUpdater(),
            bookkeeper = provideBookkeeper()
        )

}