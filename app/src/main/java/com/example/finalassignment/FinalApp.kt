package com.example.finalassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class FinalApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}