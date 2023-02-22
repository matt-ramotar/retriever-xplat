package ai.wandering.retriever.common.storekit.store.collection

import ai.wandering.retriever.common.storekit.LocalChannels
import ai.wandering.retriever.common.storekit.NoteChannel
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.UserPinnedChannel
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.bookkeeper.ChannelsBookkeeper
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asPopulatedOutput
import ai.wandering.retriever.common.storekit.db.queries.channel.asPopulated
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
    MutableStoreProvider<String, Channels, List<Channel.Output.Populated>, List<Channel.Output.Populated>, Boolean> {
    override fun provideConverter(): Converter<Channels, List<Channel.Output.Populated>, List<Channel.Output.Populated>> =
        Converter.Builder<Channels, List<Channel.Output.Populated>, List<Channel.Output.Populated>>()
            .fromNetworkToOutput { network -> network.channels.map { it.asPopulatedOutput() } }
            .fromOutputToLocal { it }
            .fromLocalToOutput { it }
            .build()

    override fun provideFetcher(): Fetcher<String, Channels> = Fetcher.of { userId ->
        when (val result = api.get(userId)) {
            is RequestResult.Exception -> {
                TODO()
            }

            is RequestResult.Success -> {
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

    override fun provideSot(): SourceOfTruth<String, List<Channel.Output.Populated>> = SourceOfTruth.of(
        reader = { userId ->


            channelFlow {

                try {
                    val channels = db.localChannelsQueries.asPopulated(userId, db.localChannelQueries)
                    send(channels)
                } catch (error: Throwable) {
                }
            }
        },
        writer = { _, listOfPopulated ->

            try {
                val userId = listOfPopulated.firstOrNull()?.user?.id
                val channelIds = listOfPopulated.map { it.id }
                if (userId != null) {
                    val channels = LocalChannels(userId, channelIds)
                    db.localChannelsQueries.upsert(channels)

                    listOfPopulated.forEachIndexed { index, populated ->


                        // User
                        val user = populated.user.asLocal()
                        db.localUserQueries.upsert(user)

                        // Graph
                        val graph = populated.graph.asLocal()
                        db.localGraphQueries.upsert(graph)

                        // Tag
                        val tag = populated.tag.asLocal()
                        db.localTagQueries.upsert(tag)

                        // Notes
                        populated.notes.forEach { note ->
                            db.localNoteQueries.upsert(note.asLocal())
                            val noteChannel = NoteChannel(note.id, populated.id)
                            db.noteChannelQueries.upsert(noteChannel)
                        }

                        // Pinners
                        populated.pinners.forEach { pinner ->
                            db.localUserQueries.upsert(pinner.asLocal())
                            val pinnedChannel = UserPinnedChannel(pinner.id, populated.id)
                            db.userPinnedChannelQueries.upsert(pinnedChannel)
                        }

                        // Channel
                        db.localChannelQueries.upsert(populated.asLocal())
                    }
                }
            } catch (error: Throwable) {
            }

        },
        delete = { userId -> db.localChannelsQueries.clearById(userId) },
        deleteAll = { db.localChannelsQueries.clearAll() }
    )

    override fun provideUpdater(): Updater<String, List<Channel.Output.Populated>, Boolean> = Updater.by(
        post = { _, _ -> UpdaterResult.Error.Message("Not implemented") }
    )

    override fun provideMutableStore(): MutableStore<String, List<Channel.Output.Populated>> = StoreBuilder
        .from<String, Channels, List<Channel.Output.Populated>, List<Channel.Output.Populated>>(
            fetcher = provideFetcher(),
            sourceOfTruth = provideSot()
        )
        .converter(provideConverter())
        .build(updater = provideUpdater(), bookkeeper = provideBookkeeper())

}