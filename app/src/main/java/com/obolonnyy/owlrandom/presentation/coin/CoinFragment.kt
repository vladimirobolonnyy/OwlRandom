package com.obolonnyy.owlrandom.presentation.coin

import android.os.Bundle
import android.view.View
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import com.orra.core_ui.button.SecondaryButton
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.text.BodyText
import com.orra.core_ui.text.Title
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.clearClickable

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
            val scroll = rememberScrollState()
            val infiniteTransition = rememberInfiniteTransition(label = "")

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
                    when {
                        state.loading -> {
                            val animDur = 300
                            val rotationAnimation = infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 360f,
                                animationSpec = infiniteRepeatable(tween(animDur, 0, LinearEasing)),
                                label = ""
                            )
                            val floatAn = infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 4f,
                                animationSpec = infiniteRepeatable(tween(animDur, 0, LinearEasing)),
                                label = ""
                            )
                            val isHead = remember { mutableStateOf(state.value) }
                            isHead.value = floatAn.value in 1f..3f
                            val res = if (isHead.value) R.drawable.success else R.drawable.error
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .graphicsLayer { rotationY = rotationAnimation.value },
                                painter = painterResource(id = res),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                colorFilter = ColorFilter.tint(AppTheme.colors.elements.primary)
                            )
                        }

                        state.value -> RenderImage(R.drawable.success)
                        else -> RenderImage(R.drawable.error)
                    }

                    Title(state.value.toCoinStr(), textAlign = TextAlign.Center)

                    val statsText = state.stats.toStr()

                    if (state.stats.isNotEmpty()) {
                        BodyText(
                            text = stringResource(
                                id = R.string.coin_result_stats,
                                state.positive,
                                state.negative
                            )
                        )
                        Column(modifier = Modifier.verticalScroll(scroll)) {
                            BodyText(text = statsText)
                        }
                    }

                    LaunchedEffect(key1 = statsText, block = {
                        scroll.animateScrollTo(scroll.maxValue)
                    })
                }
            }
            val secText =
                if (state?.useAnimation == true) R.string.coin_disable_animation else R.string.coin_enable_animation
            SecondaryButton(
                text = stringResource(id = secText),
                onClick = viewModel::onAnimationClicked
            )
            BaseButton(
                text = stringResource(id = R.string.coin_roll_button),
                bgColor = AppTheme.colors.static.primary,
                onClick = viewModel::roll
            )
            Space(size = 10.dp)
        }
    }

    private fun List<Boolean>.toStr(): String {
        return this.mapIndexed { i, l -> "${i + 1}. ${l.toCoinStr()}" }.joinToString("\n")
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