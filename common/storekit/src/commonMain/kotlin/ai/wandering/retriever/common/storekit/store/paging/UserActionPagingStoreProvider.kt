package ai.wandering.retriever.common.storekit.store.paging


import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.bookkeeper.UserActionPageBookkeeper
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asNodeOutput
import ai.wandering.retriever.common.storekit.db.queries.user_action_page.findAndPopulateUserActionPage
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.entity.Identifiable
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.Thread
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.result.RequestResult
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult


// Refactor output to populated output
// Update SOT write to also write each useraction

inline fun <reified T : Identifiable> UserAction.Network.Populated.asPopulatedOutput(serializer: Json): UserAction.Output.Populated<T> {
    println("Hitting $this")

    return UserAction.Output.Populated<T>(
        id = _id,
        user = user.asNodeOutput(),
        obj = serializer.decodeFromString(obj),
        model = UserAction.Model.lookup(model),
        type = UserAction.Type.lookup(type)
    )
}


class UserActionPagingStoreProvider(
    private val user: AuthenticatedUser,
    private val api: UserActionPagingApi,
    private val db: RetrieverDatabase,
    private val serializer: Json,
) : MutableStoreProvider<Int, PagingResponse<Int, UserAction.Network.Populated>, PagingResponse<Int, UserAction.Output.Populated<*>>, PagingResponse<Int, UserAction.Output.Populated<*>>, Boolean> {
    override fun provideConverter(): Converter<PagingResponse<Int, UserAction.Network.Populated>, PagingResponse<Int, UserAction.Output.Populated<*>>, PagingResponse<Int, UserAction.Output.Populated<*>>> =
        Converter.Builder<PagingResponse<Int, UserAction.Network.Populated>, PagingResponse<Int, UserAction.Output.Populated<*>>, PagingResponse<Int, UserAction.Output.Populated<*>>>()
            .fromNetworkToOutput { pagingResponse ->

                try {
                    require(pagingResponse is PagingResponse.Data)

                    val userActions = pagingResponse.objects.map { userAction ->
                        when (val model = UserAction.Model.lookup(userAction.model)) {
                            UserAction.Model.Note -> userAction.asPopulatedOutput<Note.Network.Populated>(serializer)
                            UserAction.Model.Thread -> userAction.asPopulatedOutput<Thread.Network>(serializer)
                            UserAction.Model.Channel -> userAction.asPopulatedOutput<Channel.Network.Populated>(serializer)
                            UserAction.Model.Graph -> userAction.asPopulatedOutput<Graph.Network.Populated>(serializer)
                            UserAction.Model.User -> userAction.asPopulatedOutput<User.Network>(serializer)
                        }

                    }

                    PagingResponse.Data(
                        pageId = pagingResponse.pageId,
                        nextPageId = pagingResponse.nextPageId,
                        prevPageId = pagingResponse.prevPageId,
                        totalPages = pagingResponse.totalPages,
                        totalObjects = pagingResponse.totalObjects,
                        offset = pagingResponse.offset,
                        objects = userActions
                    )
                } catch (error: Throwable) {
                    TODO()
                }
            }
            .fromOutputToLocal { pagingResponse ->
                require(pagingResponse is PagingResponse.Data)

                PagingResponse.Data(
                    pageId = pagingResponse.pageId,
                    nextPageId = pagingResponse.nextPageId,
                    prevPageId = pagingResponse.prevPageId,
                    objects = pagingResponse.objects,
                    totalPages = pagingResponse.totalPages,
                    offset = pagingResponse.offset,
                    totalObjects = pagingResponse.totalObjects,
                )
            }
            .fromLocalToOutput { localPage ->
                require(localPage is PagingResponse.Data)
                PagingResponse.Data(
                    pageId = localPage.pageId,
                    nextPageId = localPage.nextPageId,
                    prevPageId = localPage.prevPageId,
                    totalPages = localPage.totalPages,
                    totalObjects = localPage.totalObjects,
                    offset = localPage.offset,
                    objects = localPage.objects
                )
            }
            .build()


    override fun provideFetcher(): Fetcher<Int, PagingResponse<Int, UserAction.Network.Populated>> = Fetcher.of { pageId ->
        val userId = user.id
        val query = Json { "userId" to userId }
        println("Fetcher")

        when (val result = api.get(pageId, PagingType.Append, query)) {
            is RequestResult.Exception -> PagingResponse.Error
            is RequestResult.Success -> result.data
        }
    }

    override fun provideBookkeeper(): Bookkeeper<Int> = Bookkeeper.by(
        getLastFailedSync = { id -> db.userActionPageBookkeeperQueries.findById(id).executeAsOneOrNull()?.timestamp },
        setLastFailedSync = { id, timestamp ->
            try {
                db.userActionPageBookkeeperQueries.upsert(UserActionPageBookkeeper(id, timestamp))
                true
            } catch (_: Throwable) {
                false
            }
        },
        clear = { id ->
            try {
                db.userActionPageBookkeeperQueries.clearById(id)
                true
            } catch (_: Throwable) {
                false
            }
        },
        clearAll = {
            try {
                db.userActionPageBookkeeperQueries.clearAll()
                true
            } catch (_: Throwable) {
                false
            }
        }
    )

    override fun provideSot(): SourceOfTruth<Int, PagingResponse<Int, UserAction.Output.Populated<*>>> = SourceOfTruth.of(
        reader = { pageId ->

            flow {
                emit(db.findAndPopulateUserActionPage(pageId, user.id))
            }
        },
        writer = { _, pagingResponse ->
            println("Trying to save")
            try {
                require(pagingResponse is PagingResponse.Data)
                db.localUserActionPageQueries.upsert(pagingResponse.asLocal(user.id))

                pagingResponse.objects.forEach { userAction ->
                    db.localUserQueries.upsert(userAction.user.asLocal())
                    db.localUserActionQueries.upsert(userAction.asLocal())
                }

            } catch (error: Throwable) {
                println(error.message)
                println(error.cause)
            }
        },
        delete = { pageId -> db.localUserActionPageQueries.clear(pageId, user.id) },
        deleteAll = { db.localUserActionPageQueries.clearAll() }
    )

    override fun provideUpdater(): Updater<Int, PagingResponse<Int, UserAction.Output.Populated<*>>, Boolean> = Updater.by(
        post = { _, _ -> UpdaterResult.Error.Message("Not implemented") }
    )

    override fun provideMutableStore(): MutableStore<Int, PagingResponse<Int, UserAction.Output.Populated<*>>> = StoreBuilder
        .from<Int, PagingResponse<Int, UserAction.Network.Populated>, PagingResponse<Int, UserAction.Output.Populated<*>>, PagingResponse<Int, UserAction.Output.Populated<*>>>(
            fetcher = provideFetcher(),
            sourceOfTruth = provideSot()
        )
        .converter(provideConverter())
        .build(
            bookkeeper = provideBookkeeper(),
            updater = provideUpdater()
        )
}