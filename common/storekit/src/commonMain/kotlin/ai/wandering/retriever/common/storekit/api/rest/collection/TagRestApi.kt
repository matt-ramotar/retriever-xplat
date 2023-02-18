package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Graph
import ai.wandering.retriever.common.storekit.entity.Tag

interface TagRestApi : RestApi<String, Tag.Output.Unpopulated, Tag.Network>