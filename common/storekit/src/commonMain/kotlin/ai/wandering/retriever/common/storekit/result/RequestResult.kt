package ai.wandering.retriever.common.storekit.result

sealed class RequestResult<out Data : Any> {
    data class Success<Data : Any>(val data: Data) : RequestResult<Data>()
    data class Exception(val error: Throwable) : RequestResult<Nothing>()
}