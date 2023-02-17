package ai.wandering.retriever.common.storekit.store

import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store

interface StoreProvider<Key : Any, Network : Any, Output : Any, Local : Any> {
    fun provideFetcher(): Fetcher<Key, Network>
    fun provideSot(): SourceOfTruth<Key, Local>
    fun provideStore(): Store<Key, Output>
}