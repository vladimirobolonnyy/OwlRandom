package com.obolonnyy.owlrandom.app

import com.obolonnyy.owlrandom.BuildConfig

object BuildInfo {
    val isDebug: Boolean = BuildConfig.BUILD_TYPE.toLowerCase() == "debug"
    val alfaRelease: Boolean = BuildConfig.BUILD_TYPE.toLowerCase() == "alphaRelease"
    const val appPackage: String = BuildConfig.APPLICATION_ID


    const val spreadsheetId = "1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8"
    const val SheetId = "1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8"
    const val ApiKey = "AIzaSyDkYHGDk1IyyJu1RnmaMLqDuP6SVU84htg"
    const val CX = "d7a80698f10795d96"
}