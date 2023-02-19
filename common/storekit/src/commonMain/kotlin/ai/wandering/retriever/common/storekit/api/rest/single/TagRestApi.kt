package ai.wandering.retriever.common.storekit.api.rest.single

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Tag

interface TagRestApi : RestApi<String, Tag.Output.Unpopulated, Tag.Network>