package ai.wandering.retriever.common.storekit.api.paging.collection

import ai.wandering.retriever.common.storekit.api.paging.PagingApi
import ai.wandering.retriever.common.storekit.entity.Note

interface NotePagingApi : PagingApi<Int, Note.Network.Populated>