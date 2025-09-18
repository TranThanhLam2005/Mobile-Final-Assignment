package com.example.finalassignment.presentation.screen.home

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.data.local.dao.NoteDao
import com.example.finalassignment.data.local.entity.LocalNoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val noteDao: NoteDao
//) : ViewModel() {
//
//    val notes = mutableStateListOf<LocalNoteEntity>()
//
//    fun refreshNotes() {
//        viewModelScope.launch {
//            val allNotes = noteDao.getAllNotes()
//            notes.clear()
//            notes.addAll(allNotes)
//        }
//    }
//}