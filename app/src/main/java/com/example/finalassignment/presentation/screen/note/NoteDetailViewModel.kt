package com.example.finalassignment.presentation.screen.note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.data.local.dao.NoteDao
import com.example.finalassignment.data.local.entity.LocalNoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _note = MutableStateFlow<LocalNoteEntity?>(null)
    val note: StateFlow<LocalNoteEntity?> = _note.asStateFlow()

    fun loadNoteById(noteId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _note.value = noteDao.getNoteById(noteId)
            _isLoading.value = false
        }
    }
}