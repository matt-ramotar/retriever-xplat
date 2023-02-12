package ai.wandering.retriever.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import ai.wandering.retriever.common.storekit.RetrieverDatabase
import kotlinx.coroutines.await

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return initSqlDriver(RetrieverDatabase.Schema).await()
    }
}