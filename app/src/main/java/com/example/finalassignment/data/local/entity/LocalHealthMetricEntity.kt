package com.example.finalassignment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_metrics")
data class LocalHealthMetricEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val date: String,          // e.g. "2025-09-20"
    val steps: Int,            // steps walked
    val heartRate: Int,        // avg bpm
    val sleepHours: Float      // hours slept
)