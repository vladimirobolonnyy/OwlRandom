package com.orra.core_presentation.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.reflect.KProperty

class FragmentArgumentsDelegate<T> where T : Any? {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(fragment: Fragment, property: KProperty<*>): T =
        fragment.requireArguments().get(property.name) as T

    operator fun setValue(fragment: Fragment, property: KProperty<*>, value: T) {
        if (value == null) return
        val args = fragment.arguments ?: Bundle()
        fragment.arguments = args

        val key = property.name
        when (value) {
            is Int -> args.putInt(key, value)
            is String -> args.putString(key, value)
            is Boolean -> args.putBoolean(key, value)
            is Parcelable -> args.putParcelable(key, value)
            is Serializable -> args.putSerializable(key, value)
            else -> error("Not supported type. Please add it to FragmentArgumentsDelegate class")
        }
    }

}