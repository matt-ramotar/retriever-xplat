package ai.wandering.retriever.common.storekit.store.collection

import ai.wandering.retriever.common.storekit.LocalChannels
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.bookkeeper.ChannelsBookkeeper
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asUnpopulatedOutput
import ai.wandering.retriever.common.storekit.db.queries.channel.asUnpopulated
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Channels
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

class ChannelsStoreProvider(private val api: ChannelsRestApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, Channels, List<Channel.Output.Unpopulated>, List<Channel.Output.Unpopulated>, Boolean> {
    override fun provideConverter(): Converter<Channels, List<Channel.Output.Unpopulated>, List<Channel.Output.Unpopulated>> =
        Converter.Builder<Channels, List<Channel.Output.Unpopulated>, List<Channel.Output.Unpopulated>>()
            .fromNetworkToOutput { network -> network.channels.map { it.asUnpopulatedOutput() } }
            .fromOutputToLocal { it }
            .fromLocalToOutput { it }
            .build()

    override fun provideFetcher(): Fetcher<String, Channels> = Fetcher.of { userId ->
        when (val result = api.get(userId)) {
            is RequestResult.Exception -> {
                println("Fetcher exception: ${result.error}")
                TODO()
            }

            is RequestResult.Success -> {
                println("Fetcher response: ${result.data}")
                result.data
            }
        }
    }

    override fun provideBookkeeper(): Bookkeeper<String> = Bookkeeper.by(
        getLastFailedSync = { id -> db.channelsBookkeeperQueries.findById(id).executeAsOneOrNull()?.timestamp },
        setLastFailedSync = { id, timestamp ->
            try {
                db.channelsBookkeeperQueries.upsert(ChannelsBookkeeper(id, timestamp))
                true
            } catch (_: Throwable) {
                false
            }
        },
        clear = { id ->
            try {
                db.channelsBookkeeperQueries.clearById(id)
                true
            } catch (_: Throwable) {
                false
            }
        },
        clearAll = {
            try {
                db.channelsBookkeeperQueries.clearAll()
                true
            } catch (_: Throwable) {
                false
            }
        }
    )

    override fun provideSot(): SourceOfTruth<String, List<Channel.Output.Unpopulated>> = SourceOfTruth.of(
        reader = { userId ->
            println("Reading from SOT")




            channelFlow {

                try {
                    val queryResult = db.localChannelsQueries.findById(userId).executeAsOneOrNull()
                    println("SOT read result: $queryResult")
                    println("UserId: ${queryResult?.userId}")
                    println("Channel Ids: ${queryResult?.channelIds}")
                    val channels = queryResult?.channelIds?.map { channelId -> db.localChannelQueries.asUnpopulated(channelId) }
                    send(channels)
                } catch (error: Throwable) {
                    println("Error reading from SOT: $error")
                }
            }
        },
        writer = { _, listOfUnpopulated ->
            println("Writing to SOT: $listOfUnpopulated")
            listOfUnpopulated.forEach { unpopulated -> db.localChannelQueries.upsert(unpopulated.asLocal()) }
            val userId = listOfUnpopulated.firstOrNull()?.userId
            val channelIds = listOfUnpopulated.map { it.id }
            if (userId != null) {
                db.localChannelsQueries.upsert(LocalChannels(userId, channelIds))
            }
        },
        delete = { userId -> db.localChannelsQueries.clearById(userId) },
        deleteAll = { db.localChannelsQueries.clearAll() }
    )

    override fun provideUpdater(): Updater<String, List<Channel.Output.Unpopulated>, Boolean> = Updater.by(
        post = { _, _ -> UpdaterResult.Error.Message("Not implemented") }
    )

    override fun provideMutableStore(): MutableStore<String, List<Channel.Output.Unpopulated>> = StoreBuilder
        .from<String, Channels, List<Channel.Output.Unpopulated>, List<Channel.Output.Unpopulated>>(
            fetcher = provideFetcher(),
            sourceOfTruth = provideSot()
        )
        .converter(provideConverter())
        .build(updater = provideUpdater(), bookkeeper = provideBookkeeper())

}