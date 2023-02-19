package ai.wandering.retriever.common.storekit.store

import ai.wandering.retriever.common.storekit.entity.Channel
import org.mobilenativefoundation.store.store5.MutableStore


object Stores {
    object Single {
        const val Channel = "ChannelStore"
    }

    object Collection {
        const val Channel = "ChannelsStore"
    }

    object Socket {}

    object Paging {}

}

typealias ChannelsStore = MutableStore<String, List<Channel.Output.Unpopulated>>
typealias ChannelStore = MutableStore<String, Channel.Output>