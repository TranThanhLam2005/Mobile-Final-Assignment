package com.example.finalassignment.presentation.screen.metricdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MetricDetailScreen(
    metricId: String,
    navController: NavController,
    viewModel: MetricDetailViewModel = hiltViewModel()
) {
    val metric by viewModel.metric.collectAsState()

    // Load when screen opens
    LaunchedEffect(metricId) {
        viewModel.loadMetricById(metricId.toLong())
    }

    metric?.let { currentMetric ->
        var steps by remember { mutableStateOf(currentMetric.steps.toString()) }
        var heartRate by remember { mutableStateOf(currentMetric.heartRate.toString()) }
        var sleepHours by remember { mutableStateOf(currentMetric.sleepHours.toString()) }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 50.dp)
        ) {
            Text("Edit Metric", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = steps,
                onValueChange = { steps = it },
                label = { Text("Steps") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = heartRate,
                onValueChange = { heartRate = it },
                label = { Text("Heart Rate (bpm)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = sleepHours,
                onValueChange = { sleepHours = it },
                label = { Text("Sleep Hours") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Row {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(onClick = {
                    val updatedMetric = currentMetric.copy(
                        steps = steps.toIntOrNull() ?: currentMetric.steps,
                        heartRate = heartRate.toIntOrNull() ?: currentMetric.heartRate,
                        sleepHours = sleepHours.toFloatOrNull() ?: currentMetric.sleepHours
                    )
                    viewModel.updateMetric(updatedMetric)
                    navController.popBackStack()
                }) {
                    Text("Save")
                }
            }
        }
    } ?: run {
        Text("Loading metric details…")
    }
}
