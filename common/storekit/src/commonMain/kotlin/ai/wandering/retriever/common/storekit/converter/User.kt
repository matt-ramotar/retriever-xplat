package ai.wandering.retriever.common.storekit.converter

import ai.wandering.retriever.common.storekit.LocalUser
import ai.wandering.retriever.common.storekit.entity.User

fun User.Network.asLocal(): LocalUser = LocalUser(
    id = _id,
    username = username,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    coverImageUrl = coverImageUrl,
    bio = bio
)

fun User.Network.asUnpopulatedOutput(): User.Output.Unpopulated = User.Output.Unpopulated(
    id = _id,
    name = name,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    coverImageUrl = coverImageUrl,
    bio = bio,
    noteIds = noteIds,
    graphIds = graphIds,
    followedGraphIds = followedGraphIds,
    followedTagIds = followedTagIds,
    followedUserIds = followedUserIds,
    followerIds = followerIds,
    pinnedChannelIds = pinnedChannelIds,
    pinnedGraphIds = pinnedGraphIds,
    pinnedNoteIds = pinnedNoteIds,
    actionIds = actionIds,
    followedActionIds = followedActionIds
)

fun User.Network.asNodeOutput(): User.Output.Node = User.Output.Node(
    id = _id,
    name = name,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    coverImageUrl = coverImageUrl,
    bio = bio,
)

fun User.Output.Node.asLocal(): LocalUser = LocalUser(
    id = id,
    name = name,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    coverImageUrl = coverImageUrl,
    bio = bio,
)


fun User.Output.Unpopulated.asNodeOutput(): User.Output.Node = User.Output.Node(
    id = id,
    name = name,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    coverImageUrl = coverImageUrl,
    bio = bio,
)