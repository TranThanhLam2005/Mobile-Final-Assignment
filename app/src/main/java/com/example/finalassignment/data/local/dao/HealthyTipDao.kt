package com.example.finalassignment.data.local.dao

import androidx.room.*
import com.example.finalassignment.data.local.entity.LocalHealthyTipEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthyTipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvice(advice: LocalHealthyTipEntity)

    @Query("SELECT * FROM advice ORDER BY timestamp DESC LIMIT 1")
    fun getLatestAdvice(): Flow<LocalHealthyTipEntity?>
}