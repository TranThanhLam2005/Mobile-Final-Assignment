package com.example.finalassignment.presentation.screen.note

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.finalassignment.presentation.navigation.Screen
import com.example.finalassignment.presentation.screen.home.formatTimestamp
import com.example.finalassignment.presentation.screen.settings.DarkModeScreen
import java.text.DateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteDetailScreen(
    noteId: String,
    navController: NavController,
) {
    val viewModel : NoteDetailViewModel = hiltViewModel()
    // collect the note and loading state
    val note by viewModel.note.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Load note when entering the screen
    LaunchedEffect(noteId) {
        viewModel.loadNoteById(noteId.toInt()) // assuming noteId is Int
    }

    Scaffold(
        topBar = {
            FloatingActionButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }, modifier = Modifier.padding(top = 40.dp)
    ) { paddingValues ->
        if(isLoading){
            CircularProgressIndicator(color = Color.Yellow)
        } else{
            Column(
                modifier = Modifier.padding(paddingValues).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                note?.let {
                    Text(
                        text = "Id: "  + it.id.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                note?.let {
                    Text(
                        text ="Title: "  +  it.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                note?.let {
                    Text(
                        text = "Content: "  + it.content,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    text = formatTimestamp(note?.timestamp ?: 0L),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
