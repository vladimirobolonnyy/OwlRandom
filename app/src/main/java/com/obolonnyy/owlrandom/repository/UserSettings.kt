package com.obolonnyy.owlrandom.repository

import com.obolonnyy.owlrandom.core.*
import com.obolonnyy.owlrandom.model.MainTabs
import com.obolonnyy.owlrandom.model.toMainTab

interface UserSettings {

    fun getTodaySpendSeconds(): Long

    fun saveCurrentSeconds(long: Long)

    var wordsDesiredCount: Int
    var loadPictures: Boolean

    fun getMainTab(): MainTabs
    fun setMainTab(tab: MainTabs)
}

class UserSettingsImpl(
    prefs: PreferencesHelper = PreferencesHelper.user,
    private val clock: Clock = RealtimeClock()
) : UserSettings {

    private var timeInApp by prefs.pref.long("timeInApp")
    private var lastDate by prefs.pref.long("lastDate")

    override var wordsDesiredCount by prefs.pref.int("wordsDesiredCount", 40)
    override var loadPictures by prefs.pref.bool("loadPictures", false)
    private var mainTabPref by prefs.pref.string("mainTab", "")

    override fun getMainTab(): MainTabs {
        return mainTabPref.toMainTab()
    }

    override fun setMainTab(tab: MainTabs) {
        mainTabPref = tab.str
    }

    override fun getTodaySpendSeconds(): Long {
        if (lastDate != clock.today()) return 0
        return timeInApp
    }

    override fun saveCurrentSeconds(long: Long) {
        timeInApp = long
        lastDate = clock.today()
    }
}