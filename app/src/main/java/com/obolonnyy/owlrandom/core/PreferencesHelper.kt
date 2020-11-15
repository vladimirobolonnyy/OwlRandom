package com.obolonnyy.owlrandom.core

import android.content.Context
import android.content.SharedPreferences
import com.obolonnyy.owlrandom.app.MainApplication
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesHelper private constructor(val pref: SharedPreferences) {

    companion object {
        val user = PreferencesHelper(getPref("user"))

        private fun getPref(name: String) =
            MainApplication.context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getBoolean(key: String, defValue: Boolean) = pref.getBoolean(key, defValue)

    fun getInt(key: String, defValue: Int) = pref.getInt(key, defValue)

    fun getLong(key: String, defValue: Long) = pref.getLong(key, defValue)

    fun getString(key: String, defValue: String? = null): String? = pref.getString(key, defValue)

    fun setBoolean(key: String, value: Boolean): Unit = pref.edit().putBoolean(key, value).apply()

    fun setInt(key: String, value: Int): Unit = pref.edit().putInt(key, value).apply()

    fun setLong(key: String, value: Long): Unit = pref.edit().putLong(key, value).apply()

    fun setString(key: String, value: String?): Unit = pref.edit().putString(key, value).apply()

    fun contain(key: String) = pref.contains(key)

    fun removeKey(key: String): Unit = pref.edit().remove(key).apply()

    fun clear() = pref.edit().clear().apply()
}


fun SharedPreferences.string(key: String? = null, default: String? = null): ReadWriteProperty<Any, String?> {
    return internalDelegate(key, default, SharedPreferences::getString, SharedPreferences.Editor::putString)
}

fun SharedPreferences.long(key: String? = null, default: Long = -1): ReadWriteProperty<Any, Long> {
    return internalDelegate(key, default, SharedPreferences::getLong, SharedPreferences.Editor::putLong)
}

/**
 * Используется для создания делегатов для работы с [SharedPreferences].
 * Пример использования:
 * `val someCounter: Int by sharedPreferences.delegate()`
 * Для поддержки типа нужно добавить реализацию функции-расширения [delegate] для этого типа.
 */
private inline fun <T> SharedPreferences.internalDelegate(
    key: String?,
    defaultValue: T,
    crossinline getValue: SharedPreferences.(String, T) -> T,
    crossinline setValue: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return getValue(key ?: property.name, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            val internalKey = key ?: property.name
            edit().apply {
                value?.let { setValue(internalKey, value) } ?: remove(internalKey)
            }.apply()
        }
    }
}