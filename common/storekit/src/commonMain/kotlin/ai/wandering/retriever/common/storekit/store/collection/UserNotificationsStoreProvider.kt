package ai.wandering.retriever.common.storekit.store.collection

import ai.wandering.retriever.common.storekit.LocalUserNotification
import ai.wandering.retriever.common.storekit.LocalUserNotifications
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi
import ai.wandering.retriever.common.storekit.converter.asLocal
import ai.wandering.retriever.common.storekit.converter.asUnpopulated
import ai.wandering.retriever.common.storekit.entity.UserNotification
import ai.wandering.retriever.common.storekit.entity.UserNotifications
import ai.wandering.retriever.common.storekit.result.RequestResult
import ai.wandering.retriever.common.storekit.store.MutableStoreProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult


class UserNotificationsStoreProvider(private val api: UserNotificationsSocketApi, private val db: RetrieverDatabase) :
    MutableStoreProvider<String, UserNotifications, List<UserNotification.Output.Unpopulated>, List<LocalUserNotification>, Boolean> {
    override fun provideConverter(): Converter<UserNotifications, List<UserNotification.Output.Unpopulated>, List<LocalUserNotification>> =
        Converter.Builder<UserNotifications, List<UserNotification.Output.Unpopulated>, List<LocalUserNotification>>()
            .fromNetworkToOutput { network ->
                network.notifications.map { it.asUnpopulated() }
            }
            .fromOutputToLocal { output ->
                output.map { unpopulated -> unpopulated.asLocal() }
            }
            .fromLocalToOutput { local ->
                local.map {
                    UserNotification.Output.Unpopulated(
                        id = it.id,
                        userId = it.userId,
                        actionId = it.actionId,
                        type = it.type
                    )
                }
            }
            .build()


    override fun provideFetcher(): Fetcher<String, UserNotifications> = Fetcher.ofFlow { userId ->
        api.subscribe(userId).map { result ->
            when (result) {
                is RequestResult.Exception -> UserNotifications(listOf())
                is RequestResult.Success -> result.data
            }
        }
    }

    override fun provideBookkeeper(): Bookkeeper<String> = Bookkeeper.by(
        getLastFailedSync = { null },
        setLastFailedSync = { _, _ -> false },
        clear = { false },
        clearAll = { false }
    )

    override fun provideSot(): SourceOfTruth<String, List<LocalUserNotification>> = SourceOfTruth.of(
        reader = { flow { } },
        writer = { userId, local ->
            local.forEach {
                db.localUserNotificationQueries.upsert(it)
            }

            val notificationIds = local.map { it.id }
            val localUserNotifications = LocalUserNotifications(userId, notificationIds)
            db.localUserNotificationsQueries.upsert(localUserNotifications)
        },
        delete = { userId ->
            db.localUserNotificationsQueries.clearById(userId)
        },
        deleteAll = {
            db.localUserNotificationsQueries.clearAll()
        }
    )

    override fun provideUpdater(): Updater<String, List<UserNotification.Output.Unpopulated>, Boolean> = Updater.by(
        post = { _, _ -> UpdaterResult.Error.Message("Not implemented") },
    )

    override fun provideMutableStore(): MutableStore<String, List<UserNotification.Output.Unpopulated>> = StoreBuilder
        .from<String, UserNotifications, List<UserNotification.Output.Unpopulated>, List<LocalUserNotification>>(
            fetcher = provideFetcher(),
            sourceOfTruth = provideSot()
        )
        .converter(provideConverter())
        .build(
            updater = provideUpdater(),
            bookkeeper = provideBookkeeper()
        )
}