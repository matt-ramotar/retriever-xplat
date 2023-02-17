package ai.wandering.retriever.common.storekit.store.channel

import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.result.RequestResult
import org.mobilenativefoundation.store.store5.Fetcher

class ChannelFetcherProvider(private val api: RetrieverApi) {
    fun provide(): Fetcher<String, RequestResult<Channel.Network>> = Fetcher.of { channelId ->
        api.getById(channelId)
    }
}