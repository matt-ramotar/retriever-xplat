package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Channel

interface ChannelRestApi : RestApi<String, Channel.Output.Unpopulated, Channel.Network>