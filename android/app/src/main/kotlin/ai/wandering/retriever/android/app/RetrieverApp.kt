package ai.wandering.retriever.android.app

import ai.wandering.retriever.android.app.wiring.AppComponent
import ai.wandering.retriever.android.app.wiring.AppDependencies
import ai.wandering.retriever.android.app.wiring.DaggerAppComponent
import ai.wandering.retriever.android.app.wiring.UserComponent
import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.scoping.ComponentHolder
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.common.storekit.db.DriverFactory
import ai.wandering.retriever.common.storekit.db.seed
import ai.wandering.retriever.common.storekit.wiring.RetrieverDatabaseProvider
import android.app.Activity
import android.app.Application
import com.squareup.anvil.annotations.ContributesBinding
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
