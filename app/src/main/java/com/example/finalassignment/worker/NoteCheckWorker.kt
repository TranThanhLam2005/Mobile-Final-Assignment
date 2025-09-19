package com.example.finalassignment.worker

import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import com.example.finalassignment.MainActivity
import com.example.finalassignment.R
import com.example.finalassignment.data.local.dao.NoteDao
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NoteCheckWorkerEntryPoint {
    fun noteDao(): NoteDao
}

class NoteCheckWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val notificationId = 1001
    private val channelId = "note_reminder_channel"

    override suspend fun doWork(): Result {
        return try {
            Log.d("NoteCheckWorker", "Checking for notes...")
            // Get NoteDao using Hilt EntryPoint
            val hiltEntryPoint = EntryPointAccessors.fromApplication(
                context,
                NoteCheckWorkerEntryPoint::class.java
            )
            val noteDao = hiltEntryPoint.noteDao()

            // Check for notes in the last 24 hours
            val twentyFourHoursAgo = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
            val recentNotes = noteDao.getNotesAfter(twentyFourHoursAgo)

            if (recentNotes.isEmpty()) {
                sendNotification()
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private fun sendNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Note Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminders to write notes"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Create intent to open the app (replace with your main activity)
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build and show notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Make sure this icon exists
            .setContentTitle("Note Reminder")
            .setContentText("Haven't written a note today? Add one now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
            .build()

        try {
            notificationManager.notify(notificationId, notification)
            Log.d("NoteCheckWorker", "Notification sent successfully")
        } catch (e: SecurityException) {
            Log.e("NoteCheckWorker", "Failed to send notification - permission denied", e)
        }
    }
}
