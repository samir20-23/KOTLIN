package com.example.tiktok_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiktok_flow.ui.composables.BottomNavbar
import com.example.tiktok_flow.ui.navigation.Screen
import com.example.tiktok_flow.ui.screens.HomeScreen
import com.example.tiktok_flow.ui.screens.LocationScreen
import com.example.tiktok_flow.ui.screens.MessagesScreen
import com.example.tiktok_flow.ui.screens.ProfileScreen
import com.example.tiktok_flow.ui.theme.TiktokflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TiktokflowTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavbar(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen()
                        }
                        composable(Screen.Location.route) {
                            LocationScreen()
                        }
                        composable(Screen.Messages.route) {
                            MessagesScreen()
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}
