package com.example.finalassignment.data.local.dao

import androidx.room.*
import com.example.finalassignment.data.local.entity.LocalHealthMetricEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalHealthMetricDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(metric: LocalHealthMetricEntity): Long

    @Update
    suspend fun update(metric: LocalHealthMetricEntity)

    @Delete
    suspend fun delete(metric: LocalHealthMetricEntity)

    @Query("SELECT * FROM health_metrics ORDER BY date DESC")
    fun getAllMetrics(): Flow<List<LocalHealthMetricEntity>>

    @Query("SELECT * FROM health_metrics WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): LocalHealthMetricEntity?


    // ✅ NEW: get all metrics created today
    @Query("SELECT * FROM health_metrics WHERE date = :today ORDER BY id DESC")
    fun getTodayMetrics(today: String): Flow<List<LocalHealthMetricEntity>>

    // ✅ NEW: get single metric by ID
    @Query("SELECT * FROM health_metrics WHERE id = :id LIMIT 1")
    suspend fun getMetricById(id: Long): LocalHealthMetricEntity?

    @Query("SELECT * FROM health_metrics WHERE date = :today ORDER BY id DESC LIMIT 1")
    suspend fun getLatestTodayMetric(today: String): LocalHealthMetricEntity?
}