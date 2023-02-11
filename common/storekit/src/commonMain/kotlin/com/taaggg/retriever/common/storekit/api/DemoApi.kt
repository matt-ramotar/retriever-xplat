package com.taaggg.retriever.common.storekit.api

import com.taaggg.retriever.common.storekit.entities.user.network.NetworkUser
import com.taaggg.retriever.common.storekit.result.RequestResult

interface DemoApi {
    suspend fun login(): RequestResult<NetworkUser>
}