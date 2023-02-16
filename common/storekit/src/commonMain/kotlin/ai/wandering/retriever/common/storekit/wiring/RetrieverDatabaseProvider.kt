package ai.wandering.retriever.common.storekit.wiring

import ai.wandering.retriever.common.storekit.LocalUserAction
import ai.wandering.retriever.common.storekit.LocalUserNotification
import ai.wandering.retriever.common.storekit.NoteRelationship
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.db.DriverFactory
import com.squareup.sqldelight.EnumColumnAdapter


class RetrieverDatabaseProvider {
    suspend fun provide(driverFactory: DriverFactory): RetrieverDatabase =
        RetrieverDatabase(
            driver = driverFactory.createDriver(),
            noteRelationshipAdapter = NoteRelationship.Adapter(typeAdapter = EnumColumnAdapter()),
            localUserActionAdapter = LocalUserAction.Adapter(typeAdapter = EnumColumnAdapter()),
            localUserNotificationAdapter = LocalUserNotification.Adapter(typeAdapter = EnumColumnAdapter())
        )
}