package com.obolonnyy.owlrandom.app

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.presentation.create.EditDetailsFragment
import com.obolonnyy.owlrandom.presentation.details.DetailsFragment
import com.obolonnyy.owlrandom.presentation.main.MainFragment

interface Navigator {
    fun goToEditDetails(groupId: Long? = null)
    fun goToDetails(groupId: Long)
    fun goToMain()
}

class NavigatorImpl(
    activity: FragmentActivity,
    @IdRes private val container: Int = R.id.main_container
) : Navigator {

    private val fm: FragmentManager = activity.supportFragmentManager

    override fun goToEditDetails(groupId: Long?) {
        EditDetailsFragment.new(groupId).replace()
    }

    override fun goToDetails(groupId: Long) {
        DetailsFragment.new(groupId).replace()
    }

    override fun goToMain() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        MainFragment::class.java.showOrCreate()
    }

    private fun Fragment.replace() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.animator.slide_in_left, R.animator.slide_in_right,
                R.animator.slide_in_left_exit, R.animator.slide_in_right_exit
            )
            .replace(container, this, this::class.java.toString())
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

    private fun FragmentManager.find(fragmentClass: Class<out Fragment>): Fragment? {
        return fragments.find { fragmentClass.isInstance(it) }
    }

    private fun FragmentManager.currentVisibleFragment(): Fragment? {
        return fragments.find { it.isVisible && it.isAdded }
    }
}