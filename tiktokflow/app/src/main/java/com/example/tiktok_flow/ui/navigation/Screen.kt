package com.example.tiktok_flow.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Home : Screen("home", Icons.Default.Home, "Home")
    object Location : Screen("location", Icons.Default.LocationOn, "Location")
    object Messages : Screen("messages", Icons.Default.Message, "Messages")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
}
