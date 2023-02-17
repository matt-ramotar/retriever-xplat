package ai.wandering.retriever.common.storekit.api.impl

import ai.wandering.retriever.common.storekit.api.auth.AuthApi
import ai.wandering.retriever.common.storekit.api.ChannelApi
import ai.wandering.retriever.common.storekit.api.UserNotificationsApi
import ai.wandering.retriever.common.storekit.api.RetrieverApi

class RealRetrieverApi(
    authApi: AuthApi,
    channelApi: ChannelApi,
    userNotificationsApi: UserNotificationsApi
) :
    RetrieverApi,
    AuthApi by authApi,
    ChannelApi by channelApi,
    UserNotificationsApi by userNotificationsApi