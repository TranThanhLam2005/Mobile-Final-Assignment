package com.example.finalassignment.presentation.screen.home


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.finalassignment.presentation.navigation.Screen
import com.example.finalassignment.presentation.screen.settings.DarkModeScreen
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    var showDialog by remember { mutableStateOf(false) }

    var steps by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var sleepHours by remember { mutableStateOf("") }

    val metrics by viewModel.metricsToday.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f, false)) {
                    DarkModeScreen()
                }
                FloatingActionButton(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Health Metric"
                    )
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            CircularProgressIndicator(color = Color.Green)
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp)
            ) {
                items(metrics) { metric ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.MetricDetailScreen.route + "/${metric.id}"
                                )
                            }
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

    // 🔹 Add Metric Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row {
                    TextButton(
                        onClick = {
                            showDialog = false
                            steps = ""
                            heartRate = ""
                            sleepHours = ""
                        }
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    TextButton(
                        onClick = {
                            viewModel.addHealthMetric(
                                steps = steps.toIntOrNull() ?: 0,
                                heartRate = heartRate.toIntOrNull() ?: 0,
                                sleepHours = sleepHours.toFloatOrNull() ?: 0f
                            )
                            showDialog = false
                            steps = ""
                            heartRate = ""
                            sleepHours = ""
                        }
                    ) {
                        Text("Save")
                    }
                }
            },
            dismissButton = {},
            title = { Text("New Health Metric") },
            text = {
                Column {
                    OutlinedTextField(
                        value = steps,
                        onValueChange = { steps = it },
                        label = { Text("Steps") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    OutlinedTextField(
                        value = heartRate,
                        onValueChange = { heartRate = it },
                        label = { Text("Heart Rate (bpm)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    OutlinedTextField(
                        value = sleepHours,
                        onValueChange = { sleepHours = it },
                        label = { Text("Sleep (hours)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}