package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Channel

interface ChannelRestApi : RestApi<String, Channel.Network, Channel.Request.Create, Channel.Network, Channel.Request.Update, Channel.Network, Boolean>