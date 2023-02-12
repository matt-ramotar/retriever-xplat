package ai.wandering.retriever.android.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ai.wandering.retriever.android.app.auth.PassableUser
import ai.wandering.retriever.android.app.auth.deparcelize
import ai.wandering.retriever.android.app.ui.RetrieverScaffold
import ai.wandering.retriever.android.app.wiring.AppComponent
import ai.wandering.retriever.android.app.wiring.UserComponent
import ai.wandering.retriever.android.common.scoping.ComponentHolder
import ai.wandering.retriever.android.common.sig.SigTheme
import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import ai.wandering.retriever.common.storekit.entities.user.output.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity(), ComponentHolder {
    companion object {
        fun getLaunchIntent(context: Context, user: PassableUser): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("USER", user)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = requireNotNull(intent.extras?.getParcelable("USER", PassableUser::class.java)).deparcelize()

        setContent {

            val colors = systemThemeColors()

            SigTheme {

                RetrieverScaffold()

            }
        }
    }

    private lateinit var user: User
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override val component: UserComponent by lazy { userComponentFactory.create(user) }

    private val appComponent: AppComponent by lazy { (application as RetrieverApp).component }
    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }
    private val initialized = MutableStateFlow(false)
}