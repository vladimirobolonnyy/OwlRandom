package com.obolonnyy.owlrandom.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class MainApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        AndroidThreeTen.init(this);
        if (BuildInfo.isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}