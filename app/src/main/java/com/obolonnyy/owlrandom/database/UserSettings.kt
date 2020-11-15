package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.core.Clock
import com.obolonnyy.owlrandom.core.PreferencesHelper
import com.obolonnyy.owlrandom.core.RealtimeClock
import com.obolonnyy.owlrandom.core.long

interface UserSettings {

    fun getTodaySpendSeconds(): Long

    fun saveCurrentSeconds(long: Long)
}

class UserSettingsImpl(
    prefs: PreferencesHelper = PreferencesHelper.user,
    private val clock: Clock = RealtimeClock()
) : UserSettings {

    private var timeInApp by prefs.pref.long("timeInApp")
    private var lastDate by prefs.pref.long("lastDate")

    override fun getTodaySpendSeconds(): Long {
        if (lastDate != clock.today()) return 0
        return timeInApp
    }

    override fun saveCurrentSeconds(long: Long) {
        timeInApp = long
        lastDate = clock.today()
    }
}