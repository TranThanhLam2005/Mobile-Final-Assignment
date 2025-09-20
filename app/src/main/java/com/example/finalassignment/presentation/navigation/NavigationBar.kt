package com.example.finalassignment.presentation.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalassignment.presentation.screen.healthytip.HealthyTipScreen
//import com.example.finalassignment.presentation.screen.healthytip.HealthyTipScreen
import com.example.finalassignment.presentation.screen.history.HistoryScreen
import com.example.finalassignment.presentation.screen.home.HomeScreen
import com.example.finalassignment.presentation.screen.metricdetail.MetricDetailScreen


enum class Destination(
    val route: String,
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val contentDescription: String
) {
    HOMES(Screen.HomeScreen.route, "Dashboard", Icons.Outlined.Home, Icons.Filled.Home,"Home"),
    HEALTHYTIPS(Screen.HealthyTipScreen.route, "Healthy Tips", Icons.Outlined.TipsAndUpdates, Icons.Filled.TipsAndUpdates,"Healthy Tips"),
    HISTORY(Screen.HistoryScreen.route, "History", Icons.Outlined.History, Icons.Filled.History,"History")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOMES -> HomeScreen(navController = navController)
                    Destination.HEALTHYTIPS -> HealthyTipScreen(navController = navController)
                    Destination.HISTORY -> HistoryScreen(navController = navController)
                }
            }
        }
        composable(Screen.MetricDetailScreen.route+"/{metricId}"){
                backStackEntry ->
            val metricId = backStackEntry.arguments?.getString("metricId") ?: return@composable
            MetricDetailScreen(metricId = metricId, navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.HOMES
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            val displayIcon = if(index == selectedDestination) destination.selectedIcon else destination.unselectedIcon
                            Icon(
                                displayIcon,
                                contentDescription = destination.contentDescription,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
    }
}