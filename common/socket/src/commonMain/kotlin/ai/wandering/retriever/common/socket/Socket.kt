package ai.wandering.retriever.common.socket

import kotlinx.coroutines.CoroutineScope

expect class Socket(endpoint: String, scope: CoroutineScope) {
    fun emit(event: String, data: String)
    fun connect()
    fun disconnect()
    fun isConnected(): Boolean

    fun on(event: String, handler: suspend (Array<out Any>) -> Unit)
}

