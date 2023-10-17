package com.orra.core_presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.core.view.WindowCompat
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.orra.core_presentation.R


class AlfaBottomSheetDialog constructor(
    context: Context,
    @StyleRes theme: Int,
) : BottomSheetDialog(context, theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.design_bottom_sheet)?.let { bottomSheet ->
            BottomSheetBehavior.from(bottomSheet).also { behavior ->
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
            setCancelable(true)
            bottomSheet.setBackgroundResource(android.R.color.transparent)
        }
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        view.fitsSystemWindows = false
//        removeFitSystemWindow()
    }

    private fun removeFitSystemWindow() {
        window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        findViewById<ViewGroup>(R.id.container)?.let { container ->
            container.fitsSystemWindows = false
            container.children.forEach {
                it.fitsSystemWindows = false
            }
        }
    }

}