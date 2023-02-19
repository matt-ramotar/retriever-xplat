package ai.wandering.retriever.common.storekit.wiring

import ai.wandering.retriever.common.storekit.LocalNoteRelationship
import ai.wandering.retriever.common.storekit.LocalUserAction
import ai.wandering.retriever.common.storekit.LocalUserActionPage
import ai.wandering.retriever.common.storekit.LocalUserNotification
import ai.wandering.retriever.common.storekit.LocalUserNotifications
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.db.DriverFactory
import ai.wandering.retriever.common.storekit.db.ListOfStringsColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter


class RetrieverDatabaseProvider {
    suspend fun provide(driverFactory: DriverFactory): RetrieverDatabase =
        RetrieverDatabase(
            driver = driverFactory.createDriver(),
            localNoteRelationshipAdapter = LocalNoteRelationship.Adapter(EnumColumnAdapter()),
            localUserActionAdapter = LocalUserAction.Adapter(typeAdapter = EnumColumnAdapter()),
            localUserNotificationAdapter = LocalUserNotification.Adapter(typeAdapter = EnumColumnAdapter()),
            localUserNotificationsAdapter = LocalUserNotifications.Adapter(notificationIdsAdapter = ListOfStringsColumnAdapter()),
            localUserActionPageAdapter = LocalUserActionPage.Adapter(objIdsAdapter = ListOfStringsColumnAdapter())
        )
}