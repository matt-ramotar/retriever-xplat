package com.taaggg.retriever.android.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

private const val HOME_ROUTE = "/home"
private const val ACCOUNT_ROUTE = "/account"
private const val SEARCH_ROUTE = "/search"
private const val NOTIFICATION_ROUTE = "/notification"

private const val HOME = "HOME"
private const val ACCOUNT = "ACCOUNT"
private const val SEARCH = "SEARCH"
private const val NOTIFICATION = "NOTIFICATION"


sealed class Screen(
    val route: String,
    val title: String,
    val iconSelected: ImageVector,
    val iconNotSelected: ImageVector
) {
    object Home : Screen(HOME_ROUTE, HOME, Icons.Filled.Home, Icons.Outlined.Home)
    object Account : Screen(ACCOUNT_ROUTE, ACCOUNT, Icons.Filled.Person, Icons.Outlined.Person)
    object Notification : Screen(NOTIFICATION_ROUTE, NOTIFICATION, Icons.Filled.Notifications, Icons.Outlined.Notifications)
    object Search : Screen(SEARCH_ROUTE, SEARCH, Icons.Filled.Search, Icons.Outlined.Search)
}