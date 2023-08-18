package com.orra.core_presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.orra.core_presentation.utils.setThemedContent
import com.orra.core_ui.theme.AppTheme

abstract class BaseComposeBottomSheet : BaseBottomSheet(0) {

    @Composable
    abstract fun Content()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setThemedContent {
            ModalBottomSheet(
                sheetState = rememberModalBottomSheetState(true),
                containerColor = AppTheme.colors.background.primary,
                onDismissRequest = { dismiss() },
            ) {
                this@BaseComposeBottomSheet.Content()
            }
        }
    }

}