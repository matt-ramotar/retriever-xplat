package ai.wandering.retriever.android.app

import ai.wandering.retriever.android.app.wiring.AppComponent
import ai.wandering.retriever.android.app.wiring.AppDependencies
import ai.wandering.retriever.android.app.wiring.DaggerAppComponent
import ai.wandering.retriever.android.app.wiring.UserComponent
import ai.wandering.retriever.android.common.coroutines.SuspendLazy
import ai.wandering.retriever.android.common.coroutines.suspendLazy
import ai.wandering.retriever.android.common.scoping.AppScope
import ai.wandering.retriever.android.common.scoping.ComponentHolder
import ai.wandering.retriever.android.common.scoping.SingleIn
import ai.wandering.retriever.common.socket.Socket
import ai.wandering.retriever.common.storekit.RetrieverDatabase
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

    private val database: SuspendLazy<RetrieverDatabase> = coroutineScope.suspendLazy {
        RetrieverDatabaseProvider().provide(DriverFactory(applicationContext))
    }
    override lateinit var component: AppComponent
    override fun onCreate() {
        val application = this
        coroutineScope.launch {
            val database = database.invoke()
            val socket = Socket(SOCKET_URI, coroutineScope)
            socket.connect()
            component = DaggerAppComponent.factory().create(application, database, applicationContext, socket)
            database.seed()
            super.onCreate()
        }
    }

    companion object {
        private const val SOCKET_URI = "https://www.api.retriever.wandering.ai"
    }
}

internal fun AppComponent.userComponentFactory() = (this as UserComponent.ParentBindings).userComponentFactory()
internal fun AppComponent.appDependencies() = this as AppDependencies
fun Activity.notesApp(): RetrieverApp = application as RetrieverApp
