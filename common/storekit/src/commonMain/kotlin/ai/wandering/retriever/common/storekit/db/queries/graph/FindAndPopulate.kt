package ai.wandering.retriever.common.storekit.db.queries.graph


import ai.wandering.retriever.common.storekit.LocalGraphQueries
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.entity.User
import kotlinx.datetime.Instant

fun LocalGraphQueries.findAndPopulate(id: String): Graph.Output.Populated {

    val queryResult = findByIdAndPopulate(id).executeAsList()
    val node = queryResult.first()

    val user = User.Output.Node(
        id = node.userId!!,
        name = node.userName!!,
        username = node.userUsername!!,
        avatarUrl = node.userAvatarUrl,
        coverImageUrl = node.userCoverImageUrl,
        bio = node.userBio,
        email = node.userEmail!!
    )

    val followers = queryResult.filter { it.followerId != null }.map {
        User.Output.Node(
            id = it.followerId!!,
            name = it.followerName!!,
            username = it.followerUsername!!,
            avatarUrl = it.followerAvatarUrl,
            coverImageUrl = it.followerCoverImageUrl,
            bio = it.followerBio,
            email = it.followerEmail!!
        )
    }

    val pinners = queryResult.filter { it.pinnerId != null }.map {
        User.Output.Node(
            id = it.pinnerId!!,
            name = it.pinnerName!!,
            username = it.pinnerUsername!!,
            avatarUrl = it.pinnerAvatarUrl,
            coverImageUrl = it.pinnerCoverImageUrl,
            bio = it.pinnerBio,
            email = it.pinnerEmail!!
        )
    }

    return Graph.Output.Populated(
        id = node.graphId,
        user = user,
        name = node.graphName,
        createdAt = Instant.parse(node.graphCreatedAt),
        followers = followers,
        pinners = pinners,
    )
}
