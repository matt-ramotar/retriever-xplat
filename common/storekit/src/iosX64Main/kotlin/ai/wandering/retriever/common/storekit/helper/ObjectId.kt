package ai.wandering.retriever.common.storekit.helper

import org.mongodb.kbson.BsonObjectId

actual fun ObjectId(): String = BsonObjectId().toHexString()