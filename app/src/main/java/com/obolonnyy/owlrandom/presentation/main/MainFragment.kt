package com.obolonnyy.owlrandom.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.obolonnyy.owlrandom.utils.observe
import com.orra.core_presentation.base.BaseFragment
import com.orra.core_presentation.dialog.BaseDialogFragment
import com.orra.core_presentation.utils.fragmentViewModel
import com.orra.core_ui.button.BaseButton
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.text.BodyText
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.elementClickable

class MainFragment : BaseFragment() {

    private val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    private val viewModel by fragmentViewModel { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.viewEvents, ::process)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            activity?.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onStop() {
        super.onStop()
        onBackPressedCallback.remove()
    }


    @Composable
    override fun FragmentContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background.primary)
                .systemBarsPadding()
        ) {
            NavBar(title = stringResource(id = R.string.main_title), iconLeft = null)
            Column(modifier = Modifier.weight(1f)) {
                RenderOtherTabs()
            }
            //todo delete
            BaseButton(
                text = stringResource(id = R.string.main_add_button),
                bgColor = AppTheme.colors.static.primary,
                onClick = {  navigator.goToEditDetails() }
            )
            Space(size = 10.dp)
        }
    }

    @Composable
    private fun RenderOtherTabs() {
        RenderType(
            name = stringResource(id = R.string.main_groups_text),
            icon = R.drawable.error,
            onClick = { navigator.goToGroups() }
        )
        RenderType(
            name = stringResource(id = R.string.main_coil_text),
            icon = R.drawable.error,
            onClick = { navigator.goToCoin() }
        )
        RenderType(
            name = stringResource(id = R.string.main_dice_text),
            icon = R.drawable.error,
            onClick = { navigator.goToDice() }
        )
        RenderType(
            name = stringResource(id = R.string.main_numbers_text),
            icon = R.drawable.error,
            onClick = { navigator.goToNumbers() }
        )
    }

    @Composable
    private fun RenderType(name: String, icon: Int, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .border(1.dp, AppTheme.colors.elements.divider, RoundedCornerShape(4.dp))
                .elementClickable(onClick = onClick)
                .padding(8.dp)
        ) {
            BodyText(text = name)
        }
    }

    private fun process(event: MainViewEvent) {
        when (event) {
            is MainViewEvent.GoToCreateItem -> {
                navigator.goToEditDetails()
            }
        }
    }
}