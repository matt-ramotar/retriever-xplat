package ai.wandering.retriever.common.socket

import kotlinx.coroutines.CoroutineScope

actual class Socket actual constructor(endpoint: String, private val scope: CoroutineScope) {
    actual fun emit(event: String, data: String) {
    }

    actual fun connect() {
    }

    actual fun disconnect() {
    }

    actual fun isConnected(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun on(event: String, handler: suspend (Array<out Any>) -> Unit) {
    }

}