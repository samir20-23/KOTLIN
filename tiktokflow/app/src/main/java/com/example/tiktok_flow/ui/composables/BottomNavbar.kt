package com.example.tiktok_flow.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tiktok_flow.ui.navigation.Screen

@Composable
fun BottomNavbar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val navItems = listOf(
        Screen.Home,
        Screen.Location,
        Screen.Messages,
        Screen.Profile
    )

    BottomAppBar {
        navItems.forEach { screen ->
            IconButton(
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.label,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        FloatingActionButton(
            onClick = { /* Handle upload click */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Upload"
            )
        }
    }
}
