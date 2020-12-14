package com.obolonnyy.owlrandom.model

enum class MainTabs(val str: String) {
    RANDOM("random"),
    LEARN("learn"),
    STATS("stats"),
    SETTINGS("settings")
}

fun String?.toMainTab(): MainTabs {
    return MainTabs.values().firstOrNull { it.str == this } ?: MainTabs.RANDOM
}