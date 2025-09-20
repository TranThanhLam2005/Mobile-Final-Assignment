package com.example.finalassignment.presentation.screen.healthytip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HealthyTipScreen(
    navController: NavController,
    viewModel: HealthyTipViewModel = hiltViewModel()
) {
    val tip by viewModel.tip.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDailyTip()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        if (tip == null) {
            Text("Loading tip...")
        } else {
            Text(
                text = "💡 Health Tip:",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
