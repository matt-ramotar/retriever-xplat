package ai.wandering.retriever.common.storekit.market

import ai.wandering.retriever.common.storekit.RetrieverDatabase
import ai.wandering.retriever.common.storekit.db.queries.channel.asNode
import ai.wandering.retriever.common.storekit.db.queries.channel.asPopulated
import ai.wandering.retriever.common.storekit.db.queries.channel.asUnpopulated
import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.store.ChannelsStore







class ChannelMarket(
    val channelStore: ChannelsStore, // TODO
    val channelsStore: ChannelsStore,
    val db: RetrieverDatabase
) {

    inline fun <reified T : Channel.Output> single(channelId: String): T =
        when (T::class) {
            Channel.Output.Unpopulated::class -> {
                db.localChannelQueries.asUnpopulated(channelId) as T
            }

            Channel.Output.Populated::class -> {
                db.localChannelQueries.asPopulated(channelId) as T
            }

            else -> {
                db.localChannelQueries.asNode(channelId) as T
            }
        }

    inline fun <reified T : Channel.Output> collection(userId: String): List<T> =
        db.localChannelsQueries.findById(userId).executeAsOneOrNull()?.channelIds?.map { single<T>(it) } as List<T>


}