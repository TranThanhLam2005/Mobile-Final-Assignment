package com.example.finalassignment.domain.repository

import com.example.finalassignment.data.local.dao.HealthyTipDao
import com.example.finalassignment.data.local.entity.LocalHealthyTipEntity
import com.example.finalassignment.data.remote.AdviceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdviceRepository @Inject constructor(
    private val api: AdviceApi,
    private val dao: HealthyTipDao
) {
    fun getAdvice(): Flow<LocalHealthyTipEntity?> = flow {
        // Emit cached advice first
        emit(dao.getLatestAdvice().first())

        try {
            // Fetch new advice from API
            val response = api.getAdvice()
            val entity = LocalHealthyTipEntity(response.slip.id, response.slip.advice)
            dao.insertAdvice(entity)
            emit(entity)
        } catch (e: Exception) {
            // On failure, just emit cached again
            emit(dao.getLatestAdvice().first())
        }
    }
}