package com.obolonnyy.owlrandom.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
        if (BuildInfo.isDedug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}