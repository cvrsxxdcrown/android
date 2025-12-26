package com.example.cardgame500.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Game : Screen("game", "Игра")
    object Rules : Screen("rules", "Правила")
    object Stats : Screen("stats", "Статистика")
    object Settings : Screen("settings", "Настройки")
}
