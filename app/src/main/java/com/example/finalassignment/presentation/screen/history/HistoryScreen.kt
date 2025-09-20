package com.example.finalassignment.presentation.screen.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.finalassignment.presentation.navigation.Screen

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val metrics by viewModel.metrics.collectAsState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 50.dp)
    ){
        Text("History", style = MaterialTheme.typography.headlineMedium)
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(metrics) { metric ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Steps: ${metric.steps}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Heart Rate: ${metric.heartRate} bpm",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Sleep: ${metric.sleepHours} hrs",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.padding(top = 4.dp))
                        Text(
                            text =  metric.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}