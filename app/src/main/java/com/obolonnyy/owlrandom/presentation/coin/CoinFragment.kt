package com.obolonnyy.owlrandom.presentation.coin

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.obolonnyy.owlrandom.utils.observe
import com.orra.core_presentation.base.BaseFragment
import com.orra.core_presentation.utils.fragmentViewModel
import com.orra.core_ui.button.BaseButton
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.text.BodyText
import com.orra.core_ui.text.Title
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.clearClickable
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController
import com.wajahatkarim.flippable.FlippableState

class CoinFragment : BaseFragment() {

    private val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    private val viewModel by fragmentViewModel { CoinViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.viewEvents, ::process)
    }

    @Composable
    override fun FragmentContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background.primary)
                .systemBarsPadding()
        ) {
            val state = viewModel.viewState.observeAsState().value
            NavBar(
                title = stringResource(id = R.string.coin_title),
                onLeftIconClicked = ::onBack,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clearClickable(onClick = viewModel::roll),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state?.let {
                    val flipController1 = remember { FlippableController() }
                    flipController1.flip(state.value.toState())
                    Flippable(
                        frontSide = { RenderImage(R.drawable.success) },
                        backSide = { RenderImage(R.drawable.error) },
                        flipController = flipController1,
                        flipOnTouch = false
                    )
                    when (val b = state.value) {
                        null -> {}
                        else -> Title(b.toCoinStr(), textAlign = TextAlign.Center)
                    }
                    if (state.stats.isNotEmpty()) {
                        BodyText(text = "Result: positive:= ${state.positive}, negative:= ${state.negative}")
                        BodyText(text = state.stats.toCoinStr())
                    }
                }
            }

            BaseButton(
                text = stringResource(id = R.string.coin_roll_button),
                bgColor = AppTheme.colors.static.primary,
                onClick = viewModel::roll
            )
            Space(size = 10.dp)
        }
    }

    private fun Boolean?.toState(): FlippableState {
        return when (this) {
            false -> FlippableState.BACK
            true -> FlippableState.FRONT
            null -> FlippableState.INITIALIZED
        }
    }

    private fun List<Boolean>.toCoinStr(): String {
        return joinToString(", ") { it.toCoinStr() }
    }

    private fun Boolean.toCoinStr(): String {
        return if (this) getString(R.string.coin_head) else getString(R.string.coin_tail)
    }

    @Composable
    private fun RenderImage(res: Int) {
        Icon(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = res),
            contentDescription = "",
            tint = AppTheme.colors.elements.primary
        )
    }

    private fun process(event: CoinViewEvent) {

    }

}