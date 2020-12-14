package com.obolonnyy.owlrandom.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.getColorCompose
import com.obolonnyy.owlrandom.utils.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class SettingsFragment : BaseFragment(R.layout.fragment_empty) {

    private val viewModel by viewModels { SettingsViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    MakeContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun MakeContent() {
        val checked = viewModel.switchEnabled.observeAsState(false)
        val wordsDesiredCount = viewModel.wordsDesiredCount.observeAsState(0)
        val mainTabs = viewModel.viewState.value?.mainTabValues ?: emptyList()
        val mainTab = viewModel.viewState.observeAsState().value?.mainTab

        Column {
            Row(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    "Settings",
                    style = typography.h6,
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Load pictures",
                    style = typography.body2
                )
                Switch(
                    checked = checked.value,
                    onCheckedChange = { b -> viewModel.onSwitchClicked(b) },
                    color = getColorCompose(R.color.colorAccent)
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Random words count: = ${wordsDesiredCount.value}",
                    style = typography.body2
                )
                Slider(
                    modifier = Modifier.weight(1f),
                    value = wordsDesiredCount.value.toFloat() / 100,
                    activeTickColor = getColorCompose(R.color.colorAccent),
                    inactiveTickColor = getColorCompose(R.color.colorAccent),
                    thumbColor = getColorCompose(R.color.colorAccent),
                    activeTrackColor = getColorCompose(R.color.colorAccent),
                    onValueChange = { viewModel.onDesiredUpdated(it) }
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Start tab",
                    style = typography.body2
                )
            }
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                mainTabs.forEach {
                    val bgColor = if (it == mainTab) R.color.color3_red else R.color.colorPrimary
                    Button(
                        onClick = { viewModel.onMainTabSelected(it) },
                        modifier = Modifier.weight(1f).padding(4.dp),
                        backgroundColor = getColorCompose(bgColor),
                        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f),
                            text = it,
                            style = typography.body2,
                            color = getColorCompose(R.color.white),
                        )
                    }
                }
            }
        }
    }
}