package com.example.cardgame500

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.cardgame500.ui.navigation.Screen
import com.example.cardgame500.ui.screens.*
import com.example.cardgame500.ui.theme.CardGame500Theme
import com.example.cardgame500.ui.notifications.GameNotification

class MainActivity : ComponentActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        GameNotification.createChannel(this)

        setContent {
            CardGame500Theme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val currentRoute =
                                navController.currentBackStackEntryAsState().value
                                    ?.destination?.route

                            listOf(
                                Screen.Game,
                                Screen.Rules,
                                Screen.Stats,
                                Screen.Settings
                            ).forEach { screen ->
                                NavigationBarItem(
                                    selected = currentRoute == screen.route,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = when (screen) {
                                                Screen.Game -> Icons.Default.PlayArrow
                                                Screen.Rules -> Icons.Default.Info
                                                Screen.Stats -> Icons.Default.List
                                                Screen.Settings -> Icons.Default.Settings
                                            },
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = { Text(screen.title) }
                                )
                            }
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Game.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Screen.Game.route) { GameScreen() }
                        composable(Screen.Rules.route) { RulesScreen() }
                        composable(Screen.Stats.route) { StatsScreen() }
                        composable(Screen.Settings.route) { SettingsScreen() }
                    }
                }
            }
        }
    }
}
