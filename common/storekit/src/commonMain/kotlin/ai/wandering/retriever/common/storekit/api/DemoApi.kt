package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.result.RequestResult

interface DemoApi {
    suspend fun login(): RequestResult<NetworkUser>
}