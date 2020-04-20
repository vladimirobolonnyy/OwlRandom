package com.obolonnyy.owlrandom.utils

import android.content.Intent
import androidx.fragment.app.Fragment
import java.io.Serializable

interface FragmentFactory : Serializable {
    companion object {
        const val FRAGMENT_FACTORY = "fragment_factory"
    }

    fun createFragment(): Fragment
}

inline fun Intent.putFragmentFactory(crossinline createFragment: () -> Fragment) {
    val factory = object : FragmentFactory {
        override fun createFragment(): Fragment = createFragment.invoke()
    }
    putExtra(FragmentFactory.FRAGMENT_FACTORY, factory)
}

fun Intent.getFragmentFactory(): FragmentFactory {
    return getSerializableExtra(FragmentFactory.FRAGMENT_FACTORY) as FragmentFactory
}
