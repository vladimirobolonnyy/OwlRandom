package com.obolonnyy.owlrandom.presentation.numbers

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.orra.core_ui.text.EditText
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.clearClickable

class NumbersFragment : BaseFragment() {

    private val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    private val viewModel by fragmentViewModel { NumbersViewModel() }

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
                title = stringResource(id = R.string.numbers_title),
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
                    EditText(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.minValue.toString(),
                        label = stringResource(id = R.string.numbers_min_valie_hint),
                        onValueChange = viewModel::onMinValueChanged
                    )
                    EditText(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.maxValue.toString(),
                        label = stringResource(id = R.string.numbers_max_valie_hint),
                        onValueChange = viewModel::onMaxValueChanged
                    )

                    RenderImage(state.values)

                    val scroll =rememberScrollState()
                    val statsText = state.stats.toStr()

                    if (state.stats.isNotEmpty()) {
                        BodyText(text = stringResource(id = R.string.dice_stats))
                        Column(modifier = Modifier.verticalScroll(scroll)) {
                            BodyText(text = statsText)
                        }
                    }

                    LaunchedEffect(key1 = statsText, block = {
                        scroll.animateScrollTo(scroll.maxValue)
                    })
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

    @Composable
    private fun RenderImage(number: Long) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                color = AppTheme.colors.text.primary,
                style = AppTheme.styles.TitlePrimary,
            )
        }
    }

    private fun process(event: DiceViewEvent) {

    }

    private fun List<Long>.toStr(): String {
        return this.mapIndexed { i, l -> "${i + 1}. $l" }.joinToString("\n")
    }

}

