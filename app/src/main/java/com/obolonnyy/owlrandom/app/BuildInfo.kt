package com.obolonnyy.owlrandom.app

import com.obolonnyy.owlrandom.BuildConfig

object BuildInfo {
    val isDedug : Boolean = BuildConfig.BUILD_TYPE.toLowerCase() == "debug"
}