@file:OptIn(ExperimentalMaterial3Api::class)

package ai.wandering.retriever.android.app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ai.wandering.retriever.android.app.navigation.Routing

@Composable
fun RetrieverScaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { RetrieverBottomBar(navController = navController) }
    ) { innerPadding ->
        Routing(navController = navController, innerPadding = innerPadding)
    }
}