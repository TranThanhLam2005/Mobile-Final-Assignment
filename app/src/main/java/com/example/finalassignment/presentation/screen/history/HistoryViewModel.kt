package com.example.finalassignment.presentation.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.data.local.dao.LocalHealthMetricDao
import com.example.finalassignment.data.local.entity.LocalHealthMetricEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val healthMetricDao: LocalHealthMetricDao
) : ViewModel() {
    private val _metrics = MutableStateFlow<List<LocalHealthMetricEntity>>(emptyList())
    val metrics: StateFlow<List<LocalHealthMetricEntity>> = _metrics

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getAllMetricToday()
    }

    fun getAllMetricToday(){
        // Collect Flow from DAO so updates are automatic
        viewModelScope.launch {
            healthMetricDao.getAllMetrics().collect { list ->
                _metrics.value = list
            }
        }
    }
}