package com.example.finalassignment.presentation.screen.metricdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.data.local.dao.LocalHealthMetricDao
import com.example.finalassignment.data.local.entity.LocalHealthMetricEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetricDetailViewModel @Inject constructor(
    private val healthMetricDao: LocalHealthMetricDao,
) : ViewModel() {

    private val _metric = MutableStateFlow<LocalHealthMetricEntity?>(null)
    val metric: StateFlow<LocalHealthMetricEntity?> = _metric

    fun loadMetricById(id: Long) {
        viewModelScope.launch {
            _metric.value = healthMetricDao.getMetricById(id)
        }
    }

    fun updateMetric(updated: LocalHealthMetricEntity) {
        viewModelScope.launch {
            healthMetricDao.update(updated)
            _metric.value = updated // refresh local state
        }
    }
}
