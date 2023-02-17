package ai.wandering.retriever.common.storekit.store

import org.mobilenativefoundation.store.store5.Bookkeeper
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Updater

interface MutableStoreProvider<Key : Any, Network : Any, Output : Any, Local : Any, Response : Any> {
    fun provideConverter(): Converter<Network, Output, Local>
    fun provideFetcher(): Fetcher<Key, Network>
    fun provideBookkeeper(): Bookkeeper<Key>
    fun provideSot(): SourceOfTruth<Key, Local>
    fun provideUpdater(): Updater<Key, Output, Response>
    fun provideMutableStore(): MutableStore<Key, Output>
}