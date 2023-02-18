package ai.wandering.retriever.common.storekit.store.paging

import ai.wandering.retriever.common.storekit.LocalUserActionPage
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.converter.asUnpopulated
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.Page
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.result.RequestResult
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Updater

class UserActionPagingStoreProvider(
    private val api: UserActionPagingApi,
    private val db: RetrieverDatabase
) : MutableStoreProvider<String, Page<String, UserAction.Network>, Page<String, UserAction.Output.Unpopulated>, LocalUserActionPage, Boolean> {
    override fun provideConverter(): Converter<Page<String, UserAction.Network>, Page<String, UserAction.Output.Unpopulated>, LocalUserActionPage> =
        Converter.Builder<Page<String, UserAction.Network>, Page<String, UserAction.Output.Unpopulated>, LocalUserActionPage>()
            .fromNetworkToOutput { networkPage ->
                Page(
                    key = networkPage.key,
                    items = networkPage.items.map { it.asUnpopulated() },
                    prevKey = networkPage.prevKey,
                    nextKey = networkPage.nextKey
                )
            }
            .fromOutputToLocal { outputPage ->
                LocalUserActionPage(
                    key = outputPage.key,
                    nextKey = outputPage.nextKey,
                    prevKey = outputPage.prevKey,
                    itemIds = outputPage.items.map { it.id }
                )
            }
            .fromLocalToOutput { localPage ->
                val userActions = localPage.itemIds.map { userActionId -> db.localUserActionQueries.getById(userActionId).executeAsOne().asUnpopulated() }
                Page(
                    key = localPage.key,
                    nextKey = localPage.nextKey,
                    prevKey = localPage.prevKey,
                    items = userActions
                )
            }
            .build()

    override fun provideFetcher(): Fetcher<String, Page<String, UserAction.Network>> = Fetcher.of { key ->
        when (val result = api.get(key, PagingType.Append)) {
            is RequestResult.Exception -> Page(key)
            is RequestResult.Success -> result.data
        }
    }

    override fun provideBookkeeper(): Bookkeeper<String> {
        TODO("Not yet implemented")
    }

    override fun provideSot(): SourceOfTruth<String, LocalUserActionPage> {
        TODO("Not yet implemented")
    }

    override fun provideUpdater(): Updater<String, Page<String, UserAction.Output.Unpopulated>, Boolean> {
        TODO("Not yet implemented")
    }

    override fun provideMutableStore(): MutableStore<String, Page<String, UserAction.Output.Unpopulated>> {
        TODO("Not yet implemented")
    }
}