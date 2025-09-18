package com.example.finalassignment.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.finalassignment.presentation.screen.settings.DarkModeScreen

@Composable
fun HomeScreen(
    navController: NavController
) {
    //val viewModel: HomeViewModel = hiltViewModel()
    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                DarkModeScreen()
                Spacer(modifier = Modifier.width(16.dp))
                FloatingActionButton(
                    onClick = {
                        //navController.navigate("add_edit_note")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note"
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
//            items(viewModel.notes.size) { index ->
//                val note = viewModel.notes[index]
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                        .clickable {
//                            navController.navigate("add_edit_note/${note.id}")
//                        }
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .padding(16.dp)
//                    ) {
//                        Text(
//                            text = note.title,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Text(
//                            text = note.content,
//                            style = MaterialTheme.typography.bodyMedium,
//                            maxLines = 2,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
//            }
            items(1){
                Text("Hello World")
            }
        }
    }
}
