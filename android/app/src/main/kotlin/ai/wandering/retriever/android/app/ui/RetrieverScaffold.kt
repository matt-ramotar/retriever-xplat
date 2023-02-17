@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.app.ui

import ai.wandering.retriever.android.app.navigation.Routing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RetrieverScaffold(notifications: Int) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { RetrieverBottomBar(navController = navController, notifications = notifications) }
    ) { innerPadding ->
        Routing(navController = navController, innerPadding = innerPadding)
    }
}