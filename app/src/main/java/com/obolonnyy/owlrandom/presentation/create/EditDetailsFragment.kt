package com.obolonnyy.owlrandom.presentation.create

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.orra.core_ui.text.EditText
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space

class EditDetailsFragment : BaseFragment() {

    companion object {
        private const val GROUP_ID = "GROUP_ID"

        fun new(groupId: Long?): EditDetailsFragment {
            groupId ?: return EditDetailsFragment()
            return EditDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(GROUP_ID, groupId)
                }
            }
        }
    }

    private val groupId: Long? by lazy { arguments?.getLong(GROUP_ID) }
    private val viewModel by fragmentViewModel { EditDetailsViewModel(groupId.takeIf { it != -1L }) }
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
                title = stringResource(id = R.string.main_title),
                onLeftIconClicked = ::onBack,
            )
            Column(modifier = Modifier.weight(1f)) {
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    value = state?.title,
                    label = stringResource(id = R.string.create_details_item_title_hint),
                    onValueChange = viewModel::onTitleChanged
                )
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    value = state?.items,
                    label = stringResource(id = R.string.create_details_item_hint),
                    onValueChange = viewModel::onItemsChanged
                )
            }
            if (state?.deleteBtnIsVisible == true) {
                BaseButton(
                    text = stringResource(id = R.string.delete),
                    bgColor = AppTheme.colors.static.primary,
                    onClick = viewModel::delete
                )
            }
            Space(size = 10.dp)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun process(event: CreateDetailsViewEvent): Unit = when (event) {
        is CreateDetailsViewEvent.NavigateToMain -> navigator.backToMain()
    }

}
