package com.taaggg.notes.android.app

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.taaggg.notes.android.app.auth.PassableUser
import com.taaggg.notes.android.app.auth.deparcelize
import com.taaggg.notes.android.app.wiring.AppComponent
import com.taaggg.notes.android.app.wiring.UserComponent
import com.taaggg.notes.android.common.scoping.ComponentHolder
import com.taaggg.notes.common.storekit.entities.user.output.User
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = requireNotNull(intent.extras?.getParcelable("USER", PassableUser::class.java)).deparcelize()

        setContent {
            Column {
                Text(text = "Main", color = Color.White)
                Text(text = user.email, color = Color.White)
            }
        }
    }

    private lateinit var user: User
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override lateinit var component: UserComponent

    private val appComponent: AppComponent by lazy { (application as NotesApp).component }
    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }
    private val initialized = MutableStateFlow(false)
}