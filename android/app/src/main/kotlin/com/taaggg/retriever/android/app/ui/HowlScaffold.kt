@file:OptIn(ExperimentalMaterial3Api::class)

package com.taaggg.retriever.android.app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.taaggg.retriever.android.app.navigation.Routing

@Composable
fun HowlScaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { HowlBottomBar(navController = navController) }
    ) { innerPadding ->
        Routing(navController = navController, innerPadding = innerPadding)
    }
}