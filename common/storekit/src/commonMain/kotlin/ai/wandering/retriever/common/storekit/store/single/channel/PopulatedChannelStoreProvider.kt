package ai.wandering.retriever.common.storekit.store.single.channel

import ai.wandering.retriever.common.storekit.NoteChannel
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.UserPinnedChannel
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

class PopulatedChannelStoreProvider(private val api: ChannelRestApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, Channel.Network.Populated, Channel.Output.Populated, Channel.Output.Populated, Boolean> {


    fun populateDatabase(output: Channel.Output.Populated) {
        db.localChannelQueries.upsert(output.asLocal())

        // User
        db.localUserQueries.upsert(output.user.asLocal())
        // Graph
        db.localGraphQueries.upsert(output.graph.asLocal())
        // Tag
        db.localTagQueries.upsert(output.tag.asLocal())
        // Notes
        output.notes.forEach { note ->
            db.localNoteQueries.upsert(note.asLocal())
            db.noteChannelQueries.upsert(NoteChannel(note.id, output.id))
        }


        // Pinners
        output.pinners.forEach { pinner ->
            db.localUserQueries.upsert(pinner.asLocal())
            db.userPinnedChannelQueries.upsert(UserPinnedChannel(pinner.id, output.id))
        }

    }

    override fun provideConverter(): Converter<Channel.Network.Populated, Channel.Output.Populated, Channel.Output.Populated> =
        Converter.Builder<Channel.Network.Populated, Channel.Output.Populated, Channel.Output.Populated>()
            .fromNetworkToOutput { network ->
                val output = network.asPopulatedOutput()
                also {
                    populateDatabase(output)
                }
                output
            }
            .fromOutputToLocal {

                it
            }
            .fromLocalToOutput { it }
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


    override fun provideSot(): SourceOfTruth<String, Channel.Output.Populated> = SourceOfTruth.of(
        reader = { channelId ->
            channelFlow {
                try {
                    send(db.localChannelQueries.asPopulated(channelId))
                } catch (error: Throwable) {
                }
            }
        },
        writer = { _, input ->
            db.localChannelQueries.upsert(input.asLocal())

            // User
            db.localUserQueries.upsert(input.user.asLocal())
            // Graph
            db.localGraphQueries.upsert(input.graph.asLocal())
            // Tag
            db.localTagQueries.upsert(input.tag.asLocal())
            // Notes
            input.notes.forEach { note -> db.localNoteQueries.upsert(note.asLocal()) }

            // Pinners
            input.pinners.forEach { pinner -> db.localUserQueries.upsert(pinner.asLocal()) }
        },
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
        .from<String, Channel.Network.Populated, Channel.Output.Populated, Channel.Output.Populated>(
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