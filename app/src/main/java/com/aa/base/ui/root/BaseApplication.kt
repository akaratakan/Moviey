package com.aa.base.ui.root

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by atakanakar on 16.08.2023
 */
@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(BetterTimberDebugTree("GTAG"))
    }
}