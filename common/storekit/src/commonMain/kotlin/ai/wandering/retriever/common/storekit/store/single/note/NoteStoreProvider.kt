package ai.wandering.retriever.common.storekit.store.single.note

import ai.wandering.retriever.common.storekit.LocalNote
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.rest.single.NoteRestApi
import ai.wandering.retriever.common.storekit.bookkeeper.NoteBookkeeper
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asPopulatedOutput
import ai.wandering.retriever.common.storekit.converter.asUnpopulatedOutput
import ai.wandering.retriever.common.storekit.db.queries.note.findAndPopulate
import ai.wandering.retriever.common.storekit.entity.Note
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

class NoteStoreProvider(private val api: NoteRestApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, Note.Network.Populated, Note.Output.Populated, LocalNote, Boolean> {
    override fun provideConverter(): Converter<Note.Network.Populated, Note.Output.Populated, LocalNote> =
        Converter.Builder<Note.Network.Populated, Note.Output.Populated, LocalNote>()
            .fromNetworkToOutput { network -> network.asPopulatedOutput() }
            .fromOutputToLocal { output -> output.asLocal() }
            .fromLocalToOutput { local ->
                try {
                    db.localNoteQueries.findAndPopulate(local.id)
                } catch (error: Throwable) {

                    TODO()
                }
            }
            .build()

    override fun provideFetcher(): Fetcher<String, Note.Network.Populated> = Fetcher.of { noteId ->
        when (val result = api.get(noteId)) {
            is RequestResult.Exception -> TODO()
            is RequestResult.Success -> {
                result.data
            }
        }
    }

    override fun provideBookkeeper(): Bookkeeper<String> = Bookkeeper.by(
        getLastFailedSync = { id -> db.noteBookkeeperQueries.findById(id).executeAsOneOrNull()?.timestamp },
        setLastFailedSync = { id, timestamp ->
            try {
                db.noteBookkeeperQueries.upsert(NoteBookkeeper(id, timestamp))
                true
            } catch (_: Throwable) {
                false
            }
        },
        clear = { id ->
            try {
                db.noteBookkeeperQueries.clearById(id)
                true
            } catch (_: Throwable) {
                false
            }
        },
        clearAll = {
            try {
                db.noteBookkeeperQueries.clearAll()
                true
            } catch (_: Throwable) {
                false
            }
        }
    )


    override fun provideSot(): SourceOfTruth<String, LocalNote> = SourceOfTruth.of(
        reader = { channelId ->
            channelFlow {
                try {
                    send(db.localNoteQueries.findById(channelId).executeAsOneOrNull())
                } catch (error: Throwable) {

                }
            }
        },
        writer = { _, input -> db.localNoteQueries.upsert(input) },
        delete = { channelId -> db.localNoteQueries.clearById(channelId) },
        deleteAll = { db.localNoteQueries.clearAll() }
    )

    override fun provideUpdater(): Updater<String, Note.Output.Populated, Boolean> = Updater.by(
        post = { _, input ->
            when (val result = api.upsert(input.asUnpopulatedOutput())) {
                is RequestResult.Exception -> UpdaterResult.Error.Exception(result.error)
                is RequestResult.Success -> UpdaterResult.Success.Typed(true)
            }
        }
    )

    override fun provideMutableStore(): MutableStore<String, Note.Output.Populated> = StoreBuilder
        .from<String, Note.Network.Populated, Note.Output.Populated, LocalNote>(
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