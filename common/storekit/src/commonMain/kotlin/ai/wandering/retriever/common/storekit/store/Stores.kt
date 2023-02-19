package ai.wandering.retriever.common.storekit.store

import ai.wandering.retriever.common.storekit.entity.Channel
import org.mobilenativefoundation.store.store5.MutableStore


object Stores {
    const val Channels = "ChannelsStore"
}

typealias ChannelsStore = MutableStore<String, List<Channel.Output.Unpopulated>>