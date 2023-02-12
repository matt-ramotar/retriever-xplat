package ai.wandering.retriever.common.storekit.repository

import ai.wandering.retriever.common.storekit.entities.user.network.NetworkUser
import ai.wandering.retriever.common.storekit.result.RequestResult

interface DemoRepository {
    suspend fun login(): RequestResult<NetworkUser>
}