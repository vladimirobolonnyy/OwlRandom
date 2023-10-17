package com.obolonnyy.owlrandom.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class MainApplication : Application(), AppScope {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        context = this
        AndroidThreeTen.init(this);
        if (BuildInfo.isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}


interface AppScope {
    val applicationScope: CoroutineScope
}

val appScope = (MainApplication.context as AppScope).applicationScope