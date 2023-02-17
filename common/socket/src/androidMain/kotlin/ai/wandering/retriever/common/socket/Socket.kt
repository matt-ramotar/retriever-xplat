package ai.wandering.retriever.common.socket

import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

actual class Socket actual constructor(endpoint: String, private val scope: CoroutineScope) {


    private val socket: Socket = IO.socket(endpoint)

    actual fun emit(event: String, data: String) {
        socket.emit(event, data)
    }

    actual fun connect() {
        socket.connect()
    }

    actual fun disconnect() {
        socket.disconnect()
    }

    actual fun isConnected(): Boolean = socket.connected()
    actual fun on(event: String, handler: suspend (Array<out Any>) -> Unit) {
        socket.on(event) {
            scope.launch {
                handler(it)
            }
        }
    }
}
