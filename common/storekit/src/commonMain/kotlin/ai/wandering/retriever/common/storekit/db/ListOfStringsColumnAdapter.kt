@file:Suppress("FunctionName")

package ai.wandering.retriever.common.storekit.db

import com.squareup.sqldelight.ColumnAdapter

fun ListOfStringsColumnAdapter() = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",")
        }

    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}