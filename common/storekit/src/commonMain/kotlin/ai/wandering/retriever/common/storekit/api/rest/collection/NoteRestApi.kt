package ai.wandering.retriever.common.storekit.api.rest.collection

import ai.wandering.retriever.common.storekit.api.rest.RestApi
import ai.wandering.retriever.common.storekit.entity.Mention
import ai.wandering.retriever.common.storekit.entity.Note

interface NoteRestApi : RestApi<String, Note.Output.Unpopulated, Note.Network>