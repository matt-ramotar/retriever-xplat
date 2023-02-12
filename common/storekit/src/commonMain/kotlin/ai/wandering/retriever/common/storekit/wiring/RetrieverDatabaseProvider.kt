package ai.wandering.retriever.common.storekit.wiring

import com.squareup.sqldelight.EnumColumnAdapter
import ai.wandering.retriever.common.storekit.NoteRelationship
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.db.DriverFactory


class RetrieverDatabaseProvider {
    suspend fun provide(driverFactory: DriverFactory): RetrieverDatabase = RetrieverDatabase(
        driverFactory.createDriver(), noteRelationshipAdapter = NoteRelationship.Adapter(
            typeAdapter = EnumColumnAdapter()
        )
    )
}