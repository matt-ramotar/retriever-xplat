package ai.wandering.retriever.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    suspend fun createDriver(): SqlDriver
}