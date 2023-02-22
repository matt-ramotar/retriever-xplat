package ai.wandering.retriever.common.storekit.db.queries.user_action

import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.db.queries.graph.findAndPopulate
import ai.wandering.retriever.common.storekit.db.queries.note.findAndPopulate
import ai.wandering.retriever.common.storekit.db.queries.user.findAndPopulate
import ai.wandering.retriever.common.storekit.entity.Identifiable
import ai.wandering.retriever.common.storekit.entity.User
import ai.wandering.retriever.common.storekit.entity.UserAction

fun RetrieverDatabase.findAndPopulateUserAction(userActionId: String): UserAction.Output.Populated<*> {


    println("Id = $userActionId")
    val userAction = localUserActionQueries.findByIdAndPopulateUser(userActionId).executeAsOne()

    println("User action = $userAction")
    val obj: Identifiable.Output = when (userAction.userActionModel) {
        UserAction.Model.Note -> localNoteQueries.findAndPopulate(userAction.userActionObjectId)
        UserAction.Model.Thread -> TODO() // db.localThreadQueries.findAndPopulate(userAction.userActionObjectId)
        UserAction.Model.Channel -> TODO() // db.localChannelQueries.findAndPopulate(userAction.userActionObjectId)
        UserAction.Model.Graph -> localGraphQueries.findAndPopulate(userAction.userActionObjectId)
        UserAction.Model.User -> localUserQueries.findAndPopulate(userAction.userActionObjectId)
    }

    val user = User.Output.Node(
        id = userAction.userId!!,
        name = userAction.userName!!,
        username = userAction.userUsername!!,
        email = userAction.userEmail!!,
        bio = userAction.userBio,
        avatarUrl = userAction.userAvatarUrl,
        coverImageUrl = userAction.userCoverImageUrl
    )

    return UserAction.Output.Populated(
        id = userAction.userActionId,
        user = user,
        obj = obj,
        model = userAction.userActionModel,
        type = userAction.userActionType,
    )
}