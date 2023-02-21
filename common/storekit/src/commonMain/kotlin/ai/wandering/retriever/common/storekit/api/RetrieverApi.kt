package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelsRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.ChannelRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.GraphRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.MentionRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.NoteRestApi
import ai.wandering.retriever.common.storekit.api.rest.single.TagRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi

interface RetrieverApi {
    val auth: AuthApi

    val channel: ChannelRestApi
    val channels: ChannelsRestApi

    val graph: GraphRestApi

    val mention: MentionRestApi

    val note: NoteRestApi
    val notePaging: NotePagingApi

    val tag: TagRestApi

    val userNotificationsSocket: UserNotificationsSocketApi
}