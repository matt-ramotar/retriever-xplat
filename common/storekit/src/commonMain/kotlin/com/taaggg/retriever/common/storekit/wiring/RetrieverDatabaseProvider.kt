package com.taaggg.retriever.common.storekit.wiring

import com.squareup.sqldelight.EnumColumnAdapter
import com.taaggg.retriever.common.storekit.NoteRelationship
import com.taaggg.retriever.common.storekit.RetrieverDatabase
import com.taaggg.retriever.common.storekit.db.DriverFactory


class RetrieverDatabaseProvider {
    suspend fun provide(driverFactory: DriverFactory): RetrieverDatabase = RetrieverDatabase(
        driverFactory.createDriver(), noteRelationshipAdapter = NoteRelationship.Adapter(
            typeAdapter = EnumColumnAdapter()
        )
    )
}