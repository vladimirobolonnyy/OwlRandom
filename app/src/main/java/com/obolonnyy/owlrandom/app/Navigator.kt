package com.obolonnyy.owlrandom.app

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.presentation.coin.CoinFragment
import com.obolonnyy.owlrandom.presentation.create.CreateGroupFragment
import com.obolonnyy.owlrandom.presentation.details.DetailsFragment
import com.obolonnyy.owlrandom.presentation.dice.DiceFragment
import com.obolonnyy.owlrandom.presentation.main.MainFragment
import com.obolonnyy.owlrandom.presentation.numbers.NumbersFragment
import com.obolonnyy.owlrandom.presentation.teams.GroupsFragment
import com.orra.core_presentation.utils.className

interface Navigator {
    fun goToEditDetails(groupId: Long? = null)
    fun goToDetails(groupId: Long)
    fun showMain()
    fun backToMain()
    fun backToGroups()
    fun goToCoin()
    fun goToDice()
    fun goToNumbers()
    fun goToGroups()
}

class NavigatorImpl(
    activity: FragmentActivity,
    @IdRes private val container: Int = R.id.main_container
) : Navigator {

    private val fm: FragmentManager = activity.supportFragmentManager

    override fun showMain() {
        MainFragment().replace()
    }

    override fun goToEditDetails(groupId: Long?) {
        CreateGroupFragment.new(groupId).replace()
    }

    override fun goToDetails(groupId: Long) {
        DetailsFragment.new(groupId).replace()
    }

    override fun backToMain() {
        MainFragment().replace()
    }

    override fun backToGroups() {
        fm.popBackStack(GroupsFragment().className(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
        goToGroups()
    }

    override fun goToCoin() {
        CoinFragment().replace()
    }

    override fun goToDice() {
        DiceFragment().replace()
    }

    override fun goToNumbers() {
        NumbersFragment().replace()
    }

    override fun goToGroups() {
        GroupsFragment().replace()
    }

    private fun Fragment.replace() {
        fm.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.animator.slide_in_left, R.animator.slide_in_right,
                R.animator.slide_in_left_exit, R.animator.slide_in_right_exit
            )
            .replace(container, this, this.className())
            .addToBackStack(this.className())
            .commit()
    }

    private fun Fragment.add() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.animator.slide_in_left, R.animator.slide_in_right,
                R.animator.slide_in_left_exit, R.animator.slide_in_right_exit
            )
            .add(container, this, this.className())
            .addToBackStack(this.className())
            .commit()
    }

    private fun Class<out Fragment>.showOrCreate() {
        fm.find(this)?.show(add = false) ?: newInstance().show(add = true)
    }

    private fun Fragment.show(add: Boolean) {
        val fragment = this
        fm.beginTransaction()
            .apply { fm.currentVisibleFragment()?.let(this::hide) }
            .apply {
                if (add) {
                    add(container, fragment, fragment::class.java.simpleName)
                } else {
                    show(fragment)
                }
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