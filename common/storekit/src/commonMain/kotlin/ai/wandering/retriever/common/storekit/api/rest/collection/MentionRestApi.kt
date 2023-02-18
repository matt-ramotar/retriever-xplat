package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Mention

interface MentionRestApi : RestApi<String, Mention.Output.Unpopulated, Mention.Network>