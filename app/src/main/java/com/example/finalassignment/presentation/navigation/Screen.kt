package com.example.finalassignment.presentation.navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
    object NoteDetailScreen: Screen("note_detail")
}