package com.example.finalassignment.presentation.navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
    object MetricDetailScreen: Screen("metric_detail")
    object HealthyTipScreen: Screen("healthy_tip")
    object HistoryScreen: Screen("history")
}