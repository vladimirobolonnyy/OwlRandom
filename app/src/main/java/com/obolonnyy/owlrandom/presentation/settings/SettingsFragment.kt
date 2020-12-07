package com.obolonnyy.owlrandom.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class SettingsFragment : BaseFragment(R.layout.fragment_empty), CoroutineScope by MainScope() {

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

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


    @Preview
    @Composable
    fun MakeContent() {
        val checked = viewModel.switchEnabled.observeAsState(false)
        val wordsDesiredCount = viewModel.wordsDesiredCount.observeAsState(0)

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
                    color = Color.Red
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

//                val sliderPosition = remember { mutableStateOf(wordsDesiredCount.value.toFloat()) }

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Random words count: = ${wordsDesiredCount.value}",
                    style = typography.body2
                )

                Slider(
                    modifier = Modifier.weight(1f),
                    value = wordsDesiredCount.value.toFloat() / 100,
                    activeTickColor = Color.Red,
                    inactiveTickColor = Color.Red,
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Red,
                    inactiveTrackColor = Color.Red,
                    onValueChange = {
//                        sliderPosition.value = it
                        viewModel.onDesiredUpdated(it)
                    })
            }
        }
    }
}