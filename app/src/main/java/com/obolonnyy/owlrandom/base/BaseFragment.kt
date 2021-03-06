package com.obolonnyy.owlrandom.base

import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl

abstract class BaseFragment(@LayoutRes val res: Int) : Fragment(res) {

    protected val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    protected open val onBackPressed: (() -> Unit)? = null

    /**
     * Callback, который будет вызван вместо activity.onBackPressed().
     *
     * Когда использовать?
     * Когда надо совершить какое-либо действие из фрагмента, если пользователь нажал
     * на системную кнопку "назад"
     *
     * Примечание.
     * Не стоит внутрь onBackPressedCallback'a писать activity.onBackPressed(),
     * ибо это приведет к зацикливанию
     */
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed?.invoke()
        }
    }

    override fun onStart() {
        super.onStart()
        if (onBackPressed != null) {
            requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        }
    }

    override fun onStop() {
        super.onStop()
        if (onBackPressed != null) {
            onBackPressedCallback.remove()
        }
    }
}