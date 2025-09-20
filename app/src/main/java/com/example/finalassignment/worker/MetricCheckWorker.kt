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
import com.example.finalassignment.data.local.dao.LocalHealthMetricDao
import com.example.finalassignment.data.local.entity.LocalHealthMetricEntity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Hilt entry point to access MetricDao inside WorkManager
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface MetricCheckWorkerEntryPoint {
    fun metricDao(): LocalHealthMetricDao
}

class MetricCheckWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val notificationId = 2001
    private val channelId = "metric_alert_channel"

    override suspend fun doWork(): Result {
        return try {
            Log.d("MetricCheckWorker", "Checking today's health metrics...")

            val hiltEntryPoint = EntryPointAccessors.fromApplication(
                context,
                MetricCheckWorkerEntryPoint::class.java
            )
            val metricDao = hiltEntryPoint.metricDao()

            // Get today's date
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // Grab today's metrics from DB
            val todayMetric = metricDao.getLatestTodayMetric(todayDate)

            todayMetric?.let { metric ->
                val heartRateThreshold = 100
                if (metric.heartRate > heartRateThreshold) {
                    sendNotification(
                        title = "⚠️ High Heart Rate!",
                        value = metric.heartRate,
                        threshold = heartRateThreshold
                    )
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("MetricCheckWorker", "Error checking metrics", e)
            Result.retry()
        }
    }

    private fun sendNotification(title: String, value: Int, threshold: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Health Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Health metric alerts"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("Measured: $value (Threshold: $threshold)")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
