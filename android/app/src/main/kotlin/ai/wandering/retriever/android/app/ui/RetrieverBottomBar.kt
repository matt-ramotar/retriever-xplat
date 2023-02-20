package ai.wandering.retriever.android.app.ui

import ai.wandering.retriever.android.app.R
import ai.wandering.retriever.android.app.navigation.BottomTabs
import ai.wandering.retriever.android.common.navigation.Screen
import ai.wandering.retriever.android.common.sig.color.Sig
import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun RetrieverBottomBar(navController: NavHostController, notifications: Int) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val colors = systemThemeColors()

    fun isSelected(tab: Screen) = currentDestination?.hierarchy?.any { it.route == tab.route } == true
    BottomAppBar(
        actions = {
            BottomTabs.forEach { tab ->

                Box {

                    val icon = if (isSelected(tab)) tab.iconSelected else tab.iconNotSelected

                    when (tab) {
                        Screen.Notification -> {

                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clip(RoundedCornerShape(50))
                                    .background(colors.alert.text)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = notifications.toString(), style = MaterialTheme.typography.labelMedium, color = colors.standard.background)
                            }
                        }

                        else -> {}
                    }
                    IconButton(onClick = {
                        navController.navigate(tab.route)
                    }) {
                        Icon(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(32.dp))
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("create/note")
            }, shape = CircleShape, containerColor = Sig.ColorScheme.onBackground, contentColor = BottomAppBarDefaults.containerColor) {
                Icon(painterResource(id = R.drawable.edit), null, modifier = Modifier.size(28.dp))
            }
        }
    )
}
