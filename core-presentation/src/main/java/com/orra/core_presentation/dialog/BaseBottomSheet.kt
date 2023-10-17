package com.orra.core_presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment
import bottomSheetNavigationPadding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orra.core_presentation.R
import com.orra.core_presentation.utils.className
import com.orra.core_presentation.utils.setLightNavigationBar
import com.orra.core_presentation.utils.setThemedContent
import com.orra.core_ui.theme.AppTheme

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    protected fun DialogFragment.showDialog() {
        show(this@BaseBottomSheet.requireActivity().supportFragmentManager, className())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlfaBottomSheetDialog(
            requireContext(),
            R.style.BottomSheetTheme,
        )
        dialog.setLightNavigationBar()
        return dialog
    }

    @Composable
    abstract fun SheetContent()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(inflater.context).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setThemedContent {
            ModalBottomSheetContent()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    protected open fun ModalBottomSheetContent() {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(true),
            containerColor = AppTheme.colors.background.primary,
            onDismissRequest = { dismiss() },
            content = {
                Box(
                    modifier = Modifier
                        .background(AppTheme.colors.background.primary)
                        .bottomSheetNavigationPadding()
                ) {
                    SheetContent()
                }
            }
        )
    }
}