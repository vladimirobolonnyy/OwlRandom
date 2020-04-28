package com.obolonnyy.owlrandom.app

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.create.CreateDetailsFragment
import com.obolonnyy.owlrandom.details.DetailsFragment
import com.obolonnyy.owlrandom.main.MainFragment

class Navigator(
    private val activity: FragmentActivity, //Todo bad
    @IdRes private val container: Int = R.id.main_container
) {

    private val fm: FragmentManager = activity.supportFragmentManager

    fun goToCreateDetails(fragmentToHide: Fragment, groupId: Long) {
        CreateDetailsFragment.new(groupId).replace()
    }

    fun goToDetails() {
        DetailsFragment().replace()
    }

    fun goToMain() {
        MainFragment::class.java.showOrCreate()
    }

    fun popStackBack() {
        fm.popBackStack()
    }

    private fun Fragment.replace() {
        fm.beginTransaction()
            .replace(container, this, this::class.java.toString())
            .addToBackStack(this::class.java.toString())
            .commit()
    }

    private fun Fragment.add(fragmentToHide: Fragment) {
        fm.beginTransaction()
            .hide(fragmentToHide)
            .add(container, this, this::class.java.toString())
            .addToBackStack(this::class.java.toString())
            .commit()
    }

    private fun Class<out Fragment>.showOrCreate() {
        fm.find(this)?.show(add = false) ?: newInstance().show(add = true)
    }

    private fun Fragment.show(add: Boolean) {
        fm.beginTransaction()
            .apply { fm.currentVisibleFragment()?.let(this::hide) }
            .apply {
                if (add) add(
                    container,
                    this@show,
                    this::class.java.simpleName
                ) else show(this@show)
            }
            .commit()
    }

    fun FragmentManager.find(fragmentClass: Class<out Fragment>): Fragment? {
        return fragments.find { fragmentClass.isInstance(it) }
    }

    fun FragmentManager.currentVisibleFragment(): Fragment? {
        return fragments.find { it.isVisible && it.isAdded }
    }
}