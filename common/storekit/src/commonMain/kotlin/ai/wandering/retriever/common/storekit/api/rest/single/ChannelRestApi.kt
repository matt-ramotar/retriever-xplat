package ai.wandering.retriever.common.storekit.api.rest.single

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Channel

interface ChannelRestApi : RestApi<String, Channel.Output, Channel.Network>