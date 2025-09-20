package com.example.finalassignment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advice")
data class LocalHealthyTipEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val advice: String,
    val timestamp: Long = System.currentTimeMillis()
)