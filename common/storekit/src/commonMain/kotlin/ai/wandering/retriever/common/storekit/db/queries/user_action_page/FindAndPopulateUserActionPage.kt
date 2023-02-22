package ai.wandering.retriever.common.storekit.db.queries.user_action_page

import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.db.queries.user_action.findAndPopulateUserAction
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse

fun RetrieverDatabase.findAndPopulateUserActionPage(pageId: Int, userId: String): PagingResponse<Int, UserAction.Output.Populated<*>> {
    val queryResult = localUserActionPageQueries.find(pageId, userId).executeAsOne()

    val objects = queryResult.userActionIds?.map { userActionId ->
        findAndPopulateUserAction(userActionId)
    } ?: listOf()

    return PagingResponse.Data(
        pageId = pageId,
        objects = objects,
        prevPageId = queryResult.prevPageId,
        nextPageId = queryResult.nextPageId,
        totalObjects = queryResult.totalUserActions,
        totalPages = queryResult.totalPages,
        offset = queryResult.offset
    )


}