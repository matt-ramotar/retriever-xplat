package com.taaggg.notes.android.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.taaggg.notes.android.app.auth.PassableUser
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text(text = "Main")
        }
    }

    private lateinit var user: User
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override lateinit var component: UserComponent

    private val appComponent: AppComponent by lazy { (application as NotesApp).component }
    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }
    private val initialized = MutableStateFlow(false)
}