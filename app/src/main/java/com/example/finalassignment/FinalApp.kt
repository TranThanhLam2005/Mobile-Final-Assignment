package com.example.finalassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.work.*
import com.example.finalassignment.worker.MetricCheckWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class FinalApp : Application() {

    override fun onCreate() {
        super.onCreate()
        scheduleMetricCheckWork()
    }
    private fun scheduleMetricCheckWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // No network needed
            .setRequiresBatteryNotLow(true)                   // Avoid low-battery runs
            .build()

        // PeriodicWorkRequestBuilder only takes (interval, TimeUnit)
        val workRequest = PeriodicWorkRequestBuilder<MetricCheckWorker>(
            3, TimeUnit.HOURS // Run every 3 Hour
        )
            .setConstraints(constraints)
            .addTag("metric_check_work")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "metric_check_periodic",
            ExistingPeriodicWorkPolicy.KEEP, // Keep if already scheduled
            workRequest
        )
    }
}

