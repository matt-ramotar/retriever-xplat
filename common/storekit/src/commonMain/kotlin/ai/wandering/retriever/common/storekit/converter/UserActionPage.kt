package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalUserActionPage
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse

fun PagingResponse.Data<Int, UserAction.Output.Populated<*>>.asLocal(userId: String) = LocalUserActionPage(
    userId = userId,
    pageId = pageId,
    nextPageId = nextPageId,
    prevPageId = prevPageId,
    totalPages = totalPages,
    totalUserActions = totalObjects,
    offset = offset,
    userActionIds = objects.map { it.id }
)