package com.obolonnyy.owlrandom.app

import com.obolonnyy.owlrandom.BuildConfig

object BuildInfo {
    val isDebug : Boolean = BuildConfig.BUILD_TYPE.toLowerCase() == "debug"
}