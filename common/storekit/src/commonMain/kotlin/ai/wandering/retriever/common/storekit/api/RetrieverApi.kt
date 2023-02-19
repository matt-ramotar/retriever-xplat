package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.GraphRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.MentionRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.NoteRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.TagRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi

interface RetrieverApi {
    val auth: AuthApi

    val channel: ChannelRestApi

    val graph: GraphRestApi

    val mention: MentionRestApi

    val note: NoteRestApi
    val notePaging: NotePagingApi

    val tag: TagRestApi

    val userActionPaging: UserActionPagingApi

    val userNotificationsSocket: UserNotificationsSocketApi
}