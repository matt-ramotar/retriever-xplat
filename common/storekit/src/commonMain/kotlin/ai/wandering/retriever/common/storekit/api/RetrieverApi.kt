package ai.wandering.retriever.common.storekit.api

import ai.wandering.retriever.common.storekit.api.auth.AuthApi

interface RetrieverApi :
    AuthApi,
    ChannelApi,
    UserNotificationsApi