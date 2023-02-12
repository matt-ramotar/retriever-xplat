package com.taaggg.retriever.android.app

import android.app.Activity
import android.app.Application
import com.squareup.anvil.annotations.ContributesBinding
import com.taaggg.retriever.android.app.wiring.AppComponent
import com.taaggg.retriever.android.app.wiring.AppDependencies
import com.taaggg.retriever.android.app.wiring.DaggerAppComponent
import com.taaggg.retriever.android.app.wiring.UserComponent
import com.taaggg.retriever.android.common.scoping.AppScope
import com.taaggg.retriever.android.common.scoping.ComponentHolder
import com.taaggg.retriever.android.common.scoping.SingleIn
import com.taaggg.retriever.common.storekit.db.DriverFactory
import com.taaggg.retriever.common.storekit.db.seed
import com.taaggg.retriever.common.storekit.wiring.RetrieverDatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class, boundType = Application::class)
class RetrieverApp : Application(), ComponentHolder {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        val application = this
        coroutineScope.launch {
            val database = RetrieverDatabaseProvider().provide(DriverFactory(applicationContext))
            database.seed()
            component = DaggerAppComponent.factory().create(
                application = application,
                database = database,
                applicationContext = applicationContext
            )
        }
    }
}

internal fun AppComponent.userComponentFactory() = (this as UserComponent.ParentBindings).userComponentFactory()
internal fun AppComponent.appDependencies() = this as AppDependencies
fun Activity.notesApp(): RetrieverApp = application as RetrieverApp
