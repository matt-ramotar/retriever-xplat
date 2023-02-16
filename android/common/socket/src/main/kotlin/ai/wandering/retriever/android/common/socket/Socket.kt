package ai.wandering.retriever.android.common.socket

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object RetrieverSocket {
    private lateinit var _socket: Socket

    @Synchronized
    fun setSocket() {
        try {
            _socket = IO.socket("https://www.api.retriever.wandering.ai")
        } catch (e: URISyntaxException) {
            println("ERROR = $e")
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return _socket
    }

    @Synchronized
    fun establishConnection() {
        _socket.connect()
    }

    @Synchronized
    fun closeConnection() {
        _socket.disconnect()
    }
}
