package com.example.finalassignment.presentation.screen.home

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.data.local.dao.NoteDao
import com.example.finalassignment.data.local.entity.LocalNoteEntity
import com.example.finalassignment.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<LocalNoteEntity>>(emptyList())
    val notes: StateFlow<List<LocalNoteEntity>> = _notes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            _isLoading.value = true
            _notes.value = noteDao.getAllNotes() // if DAO returns List
            _isLoading.value = false
        }
    }

//    fun addNote(title: String, content: String, city: String?, apiKey: String) {
//        viewModelScope.launch {
//            var weatherTag: String? = null
//            if (!city.isNullOrEmpty()) {
//                weatherRepository.getWeather(city, apiKey).collect { weather ->
//                    weatherTag = weather
//                }
//            }
//
//            val newNote = LocalNoteEntity(
//                title = title,
//                content = content,
//                timestamp = System.currentTimeMillis(),
//                weatherTag = weatherTag
//            )
//            noteDao.insertNote(newNote)
//            _notes.value = noteDao.getAllNotes()
//        }
//    }
        fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = LocalNoteEntity(
                title = title,
                content = content,
                timestamp = System.currentTimeMillis(),
            )
            noteDao.insertNote(newNote)
            _notes.value = noteDao.getAllNotes()
        }
    }

}
