package ai.wandering.retriever.android.app

import ai.wandering.retriever.android.app.datastore.authenticatedUser
import ai.wandering.retriever.android.app.datastore.userDataStore
import ai.wandering.retriever.android.app.ui.RetrieverScaffold
import ai.wandering.retriever.android.app.wiring.AppComponent
import ai.wandering.retriever.android.app.wiring.AppDependencies
import ai.wandering.retriever.android.app.wiring.UserComponent
import ai.wandering.retriever.android.common.scoping.ComponentHolder
import ai.wandering.retriever.android.common.scoping.UserDependencies
import ai.wandering.retriever.android.common.sig.SigTheme
import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import ai.wandering.retriever.common.storekit.repository.UserNotificationsRepository
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), ComponentHolder {
    companion object {
        private const val USER_ID = "USER_ID"
        fun getLaunchIntent(context: Context, userId: String) =
            Intent(context, MainActivity::class.java).putExtra(USER_ID, userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDataStore = applicationContext.userDataStore
        val userId = requireNotNull(intent.extras?.getString(USER_ID))

        lifecycleScope.launch {
            user = userDataStore.authenticatedUser(appDependencies.serializer, userId)
        }

        setContent {
            val notifications = userNotificationsRepository.notifications.collectAsState()

            SigTheme {
                RetrieverScaffold(notifications.value.size)
            }
        }
    }

    private lateinit var user: AuthenticatedUser
    override val component: UserComponent by lazy { userComponentFactory.create(user) }
    private val appComponent: AppComponent by lazy { (application as RetrieverApp).component }
    private val appDependencies: AppDependencies by lazy { appComponent as AppDependencies }
    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }
    private val userDependencies: UserDependencies by lazy { component as UserDependencies }
    private val userNotificationsRepository: UserNotificationsRepository by lazy { userDependencies.userNotificationsRepository }
}