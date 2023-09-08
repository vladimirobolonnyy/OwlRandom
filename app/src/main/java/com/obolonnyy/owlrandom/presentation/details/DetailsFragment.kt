package com.obolonnyy.owlrandom.presentation.details

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.obolonnyy.owlrandom.presentation.details.random.RandomTypes
import com.obolonnyy.owlrandom.utils.observe
import com.orra.core_presentation.base.BaseFragment
import com.orra.core_presentation.utils.fragmentViewModel
import com.orra.core_ui.button.BaseButton
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.text.TextElement
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space

class DetailsFragment : BaseFragment() {

    companion object {
        private const val GROUP_ID = "GROUP_ID"

        fun new(groupId: Long): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(GROUP_ID, groupId)
                }
            }
        }
    }

    private val groupId by lazy { requireArguments().getLong(GROUP_ID) }
    private val viewModel by fragmentViewModel { DetailsViewModel(groupId) }
    private val navigator: Navigator by lazy { NavigatorImpl(this.requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.viewEvents, ::process)
    }

    @Composable
    override fun FragmentContent() {
        val state = viewModel.viewState.observeAsState().value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background.primary)
                .systemBarsPadding()
        ) {
            NavBar(
                title = state?.title.orEmpty(),
                onLeftIconClicked = ::onBack,
                iconRight = R.drawable.ic_baseline_edit_24,
                onRightIconClicked = viewModel::onEditClicked
            )
            Column(modifier = Modifier.weight(1f)) {
                LazyColumn(content = {
                    state?.items?.forEach { item ->
                        item(key = item.position) { RenderItem(item) }
                    }
                })
            }
            BaseButton(
                text = stringResource(id = R.string.roll),
                bgColor = AppTheme.colors.static.primary,
                onClick = viewModel::onRollClicked
            )
            Space(size = 10.dp)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun LazyItemScope.RenderItem(item: DetailsAdapterItem) {
        Box(
            modifier = Modifier
                .animateItemPlacement()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, AppTheme.colors.elements.divider)
                .background(item.bgColor)
                .padding(8.dp)
        ) {
            TextElement(item.text)
        }
    }

    private fun process(event: DetailsViewEvent) = when (event) {
        is DetailsViewEvent.ShowPickDialog -> showPickDialog(event.items)
        is DetailsViewEvent.NavigateToEdit -> navigator.goToEditDetails(event.groupId)
        is DetailsViewEvent.NavigateBack -> onBack()
    }

    private fun showPickDialog(items: List<RandomTypes>) {
        PickItemsBottomSheet.newInstance(
            items = items,
            onItemClicked = { viewModel.onRandomTypePicked(it) }
        ).showBottomSheet()
    }
}