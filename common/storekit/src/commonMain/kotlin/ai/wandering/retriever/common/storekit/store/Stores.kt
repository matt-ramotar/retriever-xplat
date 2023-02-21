package ai.wandering.retriever.common.storekit.store

import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.Note
import ai.wandering.retriever.common.storekit.entity.UserAction
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import org.mobilenativefoundation.store.store5.MutableStore


object Stores {
    object Single {
        const val Channel = "ChannelStore"
        const val Note = "NoteStore"
    }

    object Collection {
        const val Channel = "ChannelsStore"
    }

    object Socket {}

    object Paging {
        const val UserAction = "UserActionPagingStore"
    }

}

typealias ChannelsStore = MutableStore<String, List<Channel.Output.Populated>>
typealias ChannelStore = MutableStore<String, Channel.Output.Populated>
typealias NoteStore = MutableStore<String, Note.Output.Populated>
typealias UserActionPagingStore = MutableStore<Int, PagingResponse<Int, UserAction.Output.Populated<*>>>