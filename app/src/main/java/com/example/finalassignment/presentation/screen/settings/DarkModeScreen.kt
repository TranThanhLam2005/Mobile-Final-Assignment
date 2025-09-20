package com.example.finalassignment.presentation.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.finalassignment.data.local.preferences.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun DarkModeScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val isDarkMode by PreferenceManager.getDarkMode(context)
        .collectAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ){
        Text("DarkMode is ${if (isDarkMode) "ON" else "OFF"}")
        Spacer(modifier = Modifier.height(16.dp))

        Switch(
            checked = isDarkMode,
            onCheckedChange = { isChecked ->
                coroutineScope.launch {
                    PreferenceManager.saveDarkMode(context, isChecked)
                }
            }
        )

    }
}

