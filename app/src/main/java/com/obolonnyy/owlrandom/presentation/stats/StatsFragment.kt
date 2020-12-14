package com.obolonnyy.owlrandom.presentation.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope.align
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.model.TimeStats
import com.obolonnyy.owlrandom.utils.viewModels
import kotlinx.datetime.LocalDate

class StatsFragment : BaseFragment(R.layout.fragment_empty) {

    private val viewModel by viewModels { StatsViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    UserScreen(viewModel)
                }
            }
        }
    }

    @Composable
    fun UserScreen(userViewModel: StatsViewModel) {
        val items = userViewModel.list.observeAsState(listOf())
        UserList(userList = items)
    }

    @Composable
    private fun UserList(userList: State<List<TimeStats>>) {
        LazyColumnFor(items = userList.value) { user ->
            StatsRow(stats = user, onUserClick = {
                showMessage("You clicked ${user.date}")
            })
            Divider()
        }
    }

    @Composable
    private fun StatsRow(stats: TimeStats, onUserClick: (TimeStats) -> Unit) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onUserClick(stats) })
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text(
                    text = stats.date.printDate(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                )
            }
            Text(
                text = stats.workedSeconds.printTime(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
        }
    }

    private fun LocalDate.printDate(): String {
        return "$dayOfMonth-$monthNumber-$year"
    }

    private fun Long.printTime(): String {
        val m = this / 60
        val ms = if (m < 10) "0$m" else "$m"
        val s = this % 60
        val ss = if (s < 10) "0$s" else "$s"
        return "$ms:$ss"
    }
}
