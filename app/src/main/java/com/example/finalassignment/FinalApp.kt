package com.example.finalassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.work.*
import com.example.finalassignment.worker.NoteCheckWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class FinalApp : Application() {

    override fun onCreate() {
        super.onCreate()
        scheduleNoteCheckWork()
    }

    private fun scheduleNoteCheckWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NoteCheckWorker>(
            repeatInterval = 3, // Check every 3 hours
            repeatIntervalTimeUnit = TimeUnit.HOURS,
            flexTimeInterval = 1, // Allow 1 hour flexibility
            flexTimeIntervalUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .addTag("note_check_work")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "note_check_periodic",
            ExistingPeriodicWorkPolicy.KEEP, // Keep existing work if already scheduled
            workRequest
        )
    }
}

