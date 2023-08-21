package com.orra.core_presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.orra.core_presentation.utils.setThemedContent

abstract class BaseComposeBottomSheet : BaseBottomSheet(0) {

    @Composable
    abstract fun Content()

    /**
     * Если нужен скролл внутри шторки, можно юзать так :

     * LazyColumn(
     *      modifier = Modifier
     *      .fillMaxSize()
     *      .nestedScroll(rememberNestedScrollInteropConnection()),
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setThemedContent {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                this@BaseComposeBottomSheet.Content()
            }
        }
    }

}