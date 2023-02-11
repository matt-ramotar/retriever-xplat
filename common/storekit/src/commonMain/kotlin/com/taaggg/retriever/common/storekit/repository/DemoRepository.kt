package com.taaggg.retriever.common.storekit.repository

import com.taaggg.retriever.common.storekit.entities.user.network.NetworkUser
import com.taaggg.retriever.common.storekit.result.RequestResult

interface DemoRepository {
    suspend fun login(): RequestResult<NetworkUser>
}