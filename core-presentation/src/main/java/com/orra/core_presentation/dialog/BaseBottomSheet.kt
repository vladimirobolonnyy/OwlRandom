package com.orra.core_presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orra.core_presentation.utils.className
import com.orra.core_ui.R

open class BaseBottomSheet(
    @LayoutRes private val layoutResId: Int = 0
) : BottomSheetDialogFragment() {

    class Params(
        val disableFade: Boolean = false,
        val isHideable: Boolean = true,
    )

    protected open val params: Params = Params()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = AlfaBottomSheetDialog(
//            requireContext(),
//            R.style.BottomSheetTheme,
//            params.disableFade,
//            params.isHideable,
//        )
//        return dialog
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layoutResId != 0) {
            return inflater.inflate(layoutResId, container, false)
        }
        return null
    }

    protected fun DialogFragment.showDialog() {
        show(this@BaseBottomSheet.requireActivity().supportFragmentManager, className())
    }
}