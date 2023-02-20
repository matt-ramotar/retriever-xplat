package ai.wandering.retriever.common.storekit.market

import ai.wandering.retriever.common.storekit.entity.Channel
import ai.wandering.retriever.common.storekit.entity.paging.PagingResponse
import ai.wandering.retriever.common.storekit.entity.paging.PagingType
import ai.wandering.retriever.common.storekit.store.ChannelStore
import ai.wandering.retriever.common.storekit.store.ChannelsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import kotlin.reflect.KClass

//
//class ChannelMarket(
//    val channelStore: ChannelsStore, // TODO
//    val channelsStore: ChannelsStore,
//    val db: RetrieverDatabase
//) {
//
//    inline fun <reified T : Channel.Output> single(channelId: String): T =
//        when (T::class) {
//            Channel.Output.Unpopulated::class -> {
//                db.localChannelQueries.asUnpopulated(channelId) as T
//            }
//
//            Channel.Output.Populated::class -> {
//                db.localChannelQueries.asPopulated(channelId) as T
//            }
//
//            else -> {
//                db.localChannelQueries.asNode(channelId) as T
//            }
//        }
//
//    inline fun <reified T : Channel.Output> collection(userId: String): List<T> =
//        db.localChannelsQueries.findById(userId).executeAsOneOrNull()?.channelIds?.map { single<T>(it) } as List<T>
//
//
//}


// USAGE = multiple representations of a model (e.g., single, collection, paging, socket)
// Reasons why
// READ
// Performance of separate stores without sharing data
// Optimization

// WRITE
// Out of sync item and list or manage multiple writes


// RULE = fetch single populated persist unpopulated populate on read rather than persist all references
// WHY - (1) space cost (2) manage updating all references on object change - IDs won't change


//interface Market<Model : Any> {
//
//    fun <Output : Model> stream(id: String, classType: KClass<Output>): Flow<Output?>
//    fun <Output : Model> streamAll(userId: String, classType: KClass<Output>): Flow<List<Output>?>
//    fun <Output : Model> get(id: String, classType: KClass<Output>): Output?
//    fun <Output : Model> getAll(userId: String, classType: KClass<Output>): Flow<List<Output>?>
//    fun <Output : Model> upsertAndUpdateAll(id: String, userId: String, input: Output, classType: KClass<Output>): Boolean
//    fun <Output : Model> upsertAllAndUpdateAll(id: String, userId: String, input: List<Output>, classType: KClass<Output>): Boolean
//
//}

// For populated Need to remember to write all children!

interface Stream<Base : Any> {
    fun <Output : Base> stream(objectId: String, classType: KClass<Output>): Flow<StoreReadResponse<Output>>
}

interface StreamAll<Base : Any> {
    fun <Output : Base> streamAll(userId: String, classType: KClass<Output>): Flow<StoreReadResponse<List<Output>>>
}

interface Get<Base : Any> {
    fun <Output : Base> get(objectId: String, classType: KClass<Output>): StoreReadResponse<Output>
}

interface GetAll<Base : Any> {
    fun <Output : Base> getAll(userId: String, classType: KClass<Output>): StoreReadResponse<List<Output>>
}

interface Upsert<Base : Any> {
    fun <Input : Base> upsert(objectId: String, userId: String, input: Input, classType: KClass<Input>): StoreWriteResponse
}

interface UpsertMany<Base : Any> {
    fun <Input : Base> upsertMany(objectId: String, userId: String, input: List<Input>, classType: KClass<Input>): StoreWriteResponse
}

interface Paginate<Base : Any> {
    fun <Output : Base> paginate(pageId: Int, pagingType: PagingType, query: Json? = null, classType: KClass<Output>): PagingResponse<Int, Output>
}

interface SubscribeMany<Base : Any> {
    fun <Output : Base> subscribeMany(userId: String, classType: KClass<Output>): Flow<StoreReadResponse<List<Output>>>
}

interface Subscribe<Base : Any> {
    fun <Output : Base> subscribe(objectId: String, classType: KClass<Output>): Flow<StoreReadResponse<Output>>
}


interface Market<Base : Any> :
    Stream<Base>,
    StreamAll<Base>,
    Get<Base>,
    GetAll<Base>,
    Upsert<Base>,
    UpsertMany<Base>,
    Paginate<Base>,
    SubscribeMany<Base>,
    Subscribe<Base>


data class Stores<Key : Any, Output : Any>(
    val one: MutableStore<Key, Output>,
    val many: MutableStore<Key, List<Output>>,
    val socket: MutableStore<Key, List<Output>>,
    val paging: MutableStore<Key, List<Output>>
)

class ChannelMarket(
    private val singleStore: ChannelStore,
    private val collectionStore: ChannelsStore,
    private val socketStore: String,
    private val pagingStore: String,
) : Market<Channel> {
    override fun <Output : Channel> stream(objectId: String, classType: KClass<Output>): Flow<StoreReadResponse<Output>> {
        TODO("Not yet implemented")
    }

    override fun <Output : Channel> streamAll(userId: String, classType: KClass<Output>): Flow<StoreReadResponse<List<Output>>> {
        TODO("Not yet implemented")
    }

    override fun <Output : Channel> get(objectId: String, classType: KClass<Output>): StoreReadResponse<Output> {
        TODO("Not yet implemented")
    }

    override fun <Output : Channel> getAll(userId: String, classType: KClass<Output>): StoreReadResponse<List<Output>> {
        TODO("Not yet implemented")
    }

    override fun <Input : Channel> upsert(objectId: String, userId: String, input: Input, classType: KClass<Input>): StoreWriteResponse {
        TODO("Not yet implemented")
    }

    override fun <Input : Channel> upsertMany(objectId: String, userId: String, input: List<Input>, classType: KClass<Input>): StoreWriteResponse {
        TODO("Not yet implemented")
    }

    override fun <Output : Channel> paginate(pageId: Int, pagingType: PagingType, query: Json?, classType: KClass<Output>): PagingResponse<Int, Output> {
        TODO("Not yet implemented")
    }

    override fun <Output : Channel> subscribeMany(userId: String, classType: KClass<Output>): Flow<StoreReadResponse<List<Output>>> {
        TODO("Not yet implemented")
    }

    override fun <Output : Channel> subscribe(objectId: String, classType: KClass<Output>): Flow<StoreReadResponse<Output>> {
        TODO("Not yet implemented")
    }

}