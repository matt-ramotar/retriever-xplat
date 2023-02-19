package ai.wandering.retriever.common.storekit.api.impl

import ai.wandering.retriever.common.storekit.api.RetrieverApi
import ai.wandering.retriever.common.storekit.api.paging.collection.NotePagingApi
import ai.wandering.retriever.common.storekit.api.paging.collection.UserActionPagingApi
import ai.wandering.retriever.common.storekit.api.rest.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.rest.collection.ChannelRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.GraphRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.MentionRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.NoteRestApi
import ai.wandering.retriever.common.storekit.api.rest.collection.TagRestApi
import ai.wandering.retriever.common.storekit.api.socket.collection.UserNotificationsSocketApi

class RealRetrieverApi(
    override val auth: AuthApi,
    override val channel: ChannelRestApi,
    override val graph: GraphRestApi,
    override val mention: MentionRestApi,
    override val note: NoteRestApi,
    override val notePaging: NotePagingApi,
    override val tag: TagRestApi,
    override val userActionPaging: UserActionPagingApi,
    override val userNotificationsSocket: UserNotificationsSocketApi
) : RetrieverApi