package ai.wandering.retriever.common.storekit.store.paging

import ai.wandering.retriever.common.storekit.LocalUserActionPage
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.bookkeeper.UserActionPageBookkeeper
import ai.wandering.retriever.common.storekit.converter.asUnpopulated
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.result.RequestResult
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult

class UserActionPagingStoreProvider(
    private val user: User.Output.Node,
    private val api: UserActionPagingApi,
    private val db: RetrieverDatabase
) : MutableStoreProvider<Int, PagingResponse<Int, UserAction.Network>, PagingResponse<Int, UserAction.Output.Unpopulated>, LocalUserActionPage, Boolean> {
    override fun provideConverter(): Converter<PagingResponse<Int, UserAction.Network>, PagingResponse<Int, UserAction.Output.Unpopulated>, LocalUserActionPage> =
        Converter.Builder<PagingResponse<Int, UserAction.Network>, PagingResponse<Int, UserAction.Output.Unpopulated>, LocalUserActionPage>()
            .fromNetworkToOutput { networkPage ->

                require(networkPage is PagingResponse.Data)

                PagingResponse.Data(
                    page = PagingResponse.Data.Page(id = networkPage.page.id, objects = networkPage.page.objects?.map { it.asUnpopulated() }),
                    metadata = if (networkPage.metadata != null) PagingResponse.Data.Metadata(
                        totalObjects = networkPage.metadata.totalObjects,
                        totalPages = networkPage.metadata.totalPages,
                        offset = networkPage.metadata.offset
                    ) else null
                )
            }
            .fromOutputToLocal { outputPage ->
                require(outputPage is PagingResponse.Data)

                LocalUserActionPage(
                    id = outputPage.page.id,
                    nextId = outputPage.page.nextId,
                    prevId = outputPage.page.prevId,
                    objIds = outputPage.page.objects?.map { it.id }
                )
            }
            .fromLocalToOutput { localPage ->
                val userActions = localPage.objIds?.map { userActionId -> db.localUserActionQueries.findById(userActionId).executeAsOne().asUnpopulated() }
                PagingResponse.Data(
                    page = PagingResponse.Data.Page(
                        id = localPage.id,
                        objects = userActions,
                        prevId = localPage.prevId,
                        nextId = localPage.nextId
                    )
                )
            }
            .build()

    override fun provideFetcher(): Fetcher<Int, PagingResponse<Int, UserAction.Network>> = Fetcher.of { pageId ->
        val userId = user.id
        val query = Json { "userId" to userId }

        when (val result = api.get(pageId, PagingType.Append, query)) {
            is RequestResult.Exception -> PagingResponse.Error
            is RequestResult.Success -> result.data
        }
    }

    override fun provideBookkeeper(): Bookkeeper<Int> = Bookkeeper.by(
        getLastFailedSync = { id -> db.userActionPageBookkeeperQueries.findById(id).executeAsOne().timestamp },
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

    override fun provideSot(): SourceOfTruth<Int, LocalUserActionPage> = SourceOfTruth.of(
        reader = { key ->
            flow {
                emit(db.localUserActionPageQueries.findByKey(key).executeAsOne())
            }
        },
        writer = { _, localPage ->
            db.localUserActionPageQueries.upsert(localPage)
        },
        delete = { key -> db.localUserActionPageQueries.clearByKey(key) },
        deleteAll = { db.localUserActionPageQueries.clearAll() }
    )

    override fun provideUpdater(): Updater<Int, PagingResponse<Int, UserAction.Output.Unpopulated>, Boolean> = Updater.by(
        post = { _, _ -> UpdaterResult.Error.Message("Not implemented") }
    )

    override fun provideMutableStore(): MutableStore<Int, PagingResponse<Int, UserAction.Output.Unpopulated>> = StoreBuilder
        .from<Int, PagingResponse<Int, UserAction.Network>, PagingResponse<Int, UserAction.Output.Unpopulated>, LocalUserActionPage>(
            fetcher = provideFetcher(),
            sourceOfTruth = provideSot()
        )
        .converter(provideConverter())
        .build(
            bookkeeper = provideBookkeeper(),
            updater = provideUpdater()
        )
}