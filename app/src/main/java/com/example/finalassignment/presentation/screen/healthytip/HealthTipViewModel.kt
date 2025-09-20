package com.example.finalassignment.presentation.screen.healthytip

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.finalassignment.data.remote.AdviceApi
import com.example.finalassignment.domain.repository.AdviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthyTipViewModel @Inject constructor(
    private val adviceApi: AdviceApi,
) : ViewModel() {

    private val _tip = MutableStateFlow<String?>(null)
    val tip: StateFlow<String?> = _tip

    fun fetchDailyTip() {
        viewModelScope.launch {
            try {
                val response = adviceApi.getAdvice()
                _tip.value = response.slip.advice
            } catch (e: Exception) {
                _tip.value = "⚠️ Could not fetch tip, try again later."
            }
        }
    }
}
