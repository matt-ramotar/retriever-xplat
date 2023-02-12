package com.taaggg.retriever.common.storekit.entities.user.output

import com.taaggg.retriever.common.storekit.entities.note.Channel
import com.taaggg.retriever.common.storekit.entities.note.Note
import com.taaggg.retriever.common.storekit.entities.note.Tag
import com.taaggg.retriever.common.storekit.entities.user.UserId
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
    val following: List<User> = listOf(),
    val followers: List<User> = listOf(),
    val graphs: List<Graph> = listOf(),
    val tagsFollowing: List<Tag> = listOf(),
    val graphsFollowing: List<Graph> = listOf(),
    val graphsDependingOn: List<Graph> = listOf(),
    val pinnedGraphs: List<Graph> = listOf(),
    val pinnedChannels: List<Channel> = listOf(),
    val pinnedNotes: List<Note> = listOf(),
    val bio: String? = null,
)

@Serializable
data class Graph(
    val id: String,
    val name: String,
    val userId: String
)