package com.example.trackershmecker

import android.app.Application
import com.example.trackershmecker.fcm.NotificationChannelHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrackerShmeckerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannelHelper.createChannels(this)
    }
}
