package ai.wandering.retriever.common.storekit.api.paging.collection

import ai.wandering.retriever.common.storekit.api.paging.PagingApi
import ai.wandering.retriever.common.storekit.entity.UserAction

interface UserActionPagingApi : PagingApi<String, UserAction.Network>