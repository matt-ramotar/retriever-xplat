package ai.wandering.retriever.common.storekit.store.collection

import ai.wandering.retriever.common.storekit.LocalUserNotification
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.entity.UserNotification
import ai.wandering.retriever.common.storekit.result.RequestResult
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Updater

class UserNotificationStoreProvider(private val api: UserNotificationsSocketApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, List<UserNotification.Network>, List<UserNotification.Output.Unpopulated>, LocalUserNotification, Any> {
    override fun provideConverter(): Converter<List<UserNotification.Network>, List<UserNotification.Output.Unpopulated>, LocalUserNotification> {
        TODO("Not yet implemented")
    }

    override fun provideFetcher(): Fetcher<String, List<UserNotification.Network>> = Fetcher.ofFlow { userId ->
        api.subscribe(userId).map { result ->
            when (result) {
                is RequestResult.Exception -> {
                    listOf()
                }

                is RequestResult.Success -> {
                    result.data.notifications
                }
            }
        }
    }

    override fun provideBookkeeper(): Bookkeeper<String> {
        TODO("Not yet implemented")
    }

    override fun provideSot(): SourceOfTruth<String, LocalUserNotification> {
        TODO("Not yet implemented")
    }

    override fun provideUpdater(): Updater<String, List<UserNotification.Output.Unpopulated>, Any> {
        TODO("Not yet implemented")
    }

    override fun provideMutableStore(): MutableStore<String, List<UserNotification.Output.Unpopulated>> {
        TODO("Not yet implemented")
    }


}