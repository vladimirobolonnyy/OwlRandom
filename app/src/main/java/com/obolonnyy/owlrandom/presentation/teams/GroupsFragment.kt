package com.obolonnyy.owlrandom.presentation.teams

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.obolonnyy.owlrandom.model.MyGroup
import com.obolonnyy.owlrandom.utils.observe
import com.orra.core_presentation.base.BaseFragment
import com.orra.core_presentation.utils.fragmentViewModel
import com.orra.core_ui.button.BaseButton
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.text.BodyText
import com.orra.core_ui.text.TextElement
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.clearClickable
import com.orra.core_ui.utils.elementClickable

class GroupsFragment : BaseFragment() {

    private val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    private val viewModel by fragmentViewModel { GroupsViewModel() }

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
                title = stringResource(id = R.string.groups_title),
                onLeftIconClicked = ::onBack,
            )

            val groups = state?.groups
            Column(modifier = Modifier.weight(1f)) {
                if (groups.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clearClickable(onClick = viewModel::onAddItemClicked)
                    ) {
                        BodyText(text = stringResource(id = R.string.main_empty_text))
                    }
                } else {
                    groups.forEach {
                        RenderGroup(it)
                    }
                }
            }
            BaseButton(
                text = stringResource(id = R.string.main_add_button),
                bgColor = AppTheme.colors.static.primary,
                onClick = viewModel::onAddItemClicked
            )
            Space(size = 10.dp)
        }
    }

    @Composable
    private fun RenderGroup(myGroup: MyGroup) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .border(1.dp, AppTheme.colors.elements.divider, RoundedCornerShape(4.dp))
                .elementClickable { onItemClicked(myGroup.id) }
                .padding(8.dp)
        ) {
            TextElement(myGroup.title)
        }
    }

    private fun process(event: GroupsViewEvent) {
        when (event) {
            is GroupsViewEvent.GoToCreateItem -> {
                navigator.goToEditDetails()
            }
        }
    }

    private fun onItemClicked(groupId: Long) {
        navigator.goToDetails(groupId)
    }
}