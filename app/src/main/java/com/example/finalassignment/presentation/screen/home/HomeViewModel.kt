package com.example.finalassignment.presentation.screen.home


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
class HomeViewModel @Inject constructor(
    private val healthMetricDao: LocalHealthMetricDao,
) : ViewModel() {

    private val _metricsToday = MutableStateFlow<List<LocalHealthMetricEntity>>(emptyList())
    val metricsToday: StateFlow<List<LocalHealthMetricEntity>> = _metricsToday

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getAllMetricToday()
    }

    fun getAllMetricToday(){
        // Collect Flow from DAO so updates are automatic
        viewModelScope.launch {
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date())
            healthMetricDao.getTodayMetrics(todayDate).collect { list ->
                _metricsToday.value = list
            }
        }
    }

    fun addHealthMetric(steps: Int, heartRate: Int, sleepHours: Float) {
        viewModelScope.launch {
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date())

            val newMetric = LocalHealthMetricEntity(
                date = todayDate,
                steps = steps,
                heartRate = heartRate,
                sleepHours = sleepHours
            )
            healthMetricDao.insert(newMetric) // Flow will auto-update
        }
    }
}