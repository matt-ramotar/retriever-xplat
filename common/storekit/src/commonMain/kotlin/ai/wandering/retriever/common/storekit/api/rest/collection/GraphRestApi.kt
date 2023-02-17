package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Graph

interface GraphRestApi : RestApi<String, Graph.Network, Graph.Request.Create, Graph.Network, Graph.Request.Update, Graph.Network, Boolean>