package com.example.trackershmecker.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

object NotificationChannelHelper {
    const val DEFAULT_CHANNEL_ID = "tracker_default"

    fun createChannels(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            DEFAULT_CHANNEL_ID,
            "General",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "General notifications"
        }
        manager.createNotificationChannel(channel)
    }
}
