package com.obolonnyy.owlrandom.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.orra.core_presentation.base.BaseFragment
import com.orra.core_presentation.utils.fragmentViewModel
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.elementClickable

class MainFragment : BaseFragment() {

    private val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    private val viewModel by fragmentViewModel { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
//                Box(modifier = Modifier.weight(1f))
                RenderOtherTabs()
            }
            Space(size = 10.dp)
        }
    }

    @Composable
    private fun RenderOtherTabs() {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                RenderType(
                    name = stringResource(id = R.string.main_groups_text),
                    icon = R.drawable.ic_user_group,
                    onClick = { navigator.goToGroups() }
                )
            }
            Row {
                RenderType(
                    name = stringResource(id = R.string.main_numbers_text),
                    icon = R.drawable.baseline_looks_5_24,
                    onClick = { navigator.goToNumbers() }
                )
            }
            Row {
                RenderType(
                    name = stringResource(id = R.string.main_coil_text),
                    icon = R.drawable.ic_coin,
                    onClick = { navigator.goToCoin() }
                )
            }
            Row {
                RenderType(
                    name = stringResource(id = R.string.main_dice_text),
                    icon = R.drawable.baseline_casino_24,
                    onClick = { navigator.goToDice() }
                )
            }
        }
    }

    @Composable
    private fun RowScope.RenderType(name: String, icon: Int, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, AppTheme.colors.elements.secondary, RoundedCornerShape(6.dp))
                .elementClickable(onClick = onClick)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .background(AppTheme.colors.background.primary),
                text = name,
                style = AppTheme.styles.BodyPrimary,
                color = AppTheme.colors.text.primary,
            )
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = AppTheme.colors.elements.primary
            )
        }
    }
}