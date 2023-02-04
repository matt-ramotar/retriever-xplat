@file:OptIn(ExperimentalMaterial3Api::class)

package com.taaggg.notes.android.app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.taaggg.notes.android.app.navigation.Routing

@Composable
fun HowlScaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { HowlBottomBar(navController = navController) }
    ) { innerPadding ->
        Routing(navController = navController, innerPadding = innerPadding)
    }
}