package com.example.finalassignment.core.di

import android.content.Context
import androidx.room.Room
import com.example.finalassignment.data.local.dao.LocalHealthMetricDao
import com.example.finalassignment.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HealthMetricModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "health_metrics_db" // <-- name for your DB
        ).build()
    }

    @Provides
    fun provideHealthMetricDao(db: AppDatabase): LocalHealthMetricDao {
        return db.localHealthMetricDao()
    }
}