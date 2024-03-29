package com.obolonnyy.owlrandom.presentation.create

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.obolonnyy.owlrandom.utils.isKeyboardVisible
import com.obolonnyy.owlrandom.utils.observe
import com.orra.core_presentation.base.BaseFragment
import com.orra.core_presentation.dialog.BaseDialogFragment
import com.orra.core_presentation.utils.fragmentViewModel
import com.orra.core_ui.button.BaseButton
import com.orra.core_ui.navbar.NavBar
import com.orra.core_ui.text.EditText
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space

class CreateGroupFragment : BaseFragment() {

    companion object {
        private const val GROUP_ID = "GROUP_ID"

        fun new(groupId: Long?): CreateGroupFragment {
            groupId ?: return CreateGroupFragment()
            return CreateGroupFragment().apply {
                arguments = Bundle().apply {
                    putLong(GROUP_ID, groupId)
                }
            }
        }
    }

    private val groupId: Long? by lazy { arguments?.getLong(GROUP_ID) }
    private val viewModel by fragmentViewModel { CreateGroupViewModel(groupId.takeIf { it != -1L }) }
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

            val isKeyboardVisible = WindowInsets.Companion.isKeyboardVisible
            if (isKeyboardVisible) {
                Box(modifier = Modifier.imePadding()) {
                    BaseButton(
                        text = stringResource(id = R.string.save),
                        bgColor = AppTheme.colors.static.primary,
                        onClick = viewModel::onSaveClicked
                    )
                }
            } else {
                BaseButton(
                    text = stringResource(id = R.string.save),
                    bgColor = AppTheme.colors.static.primary,
                    onClick = viewModel::onSaveClicked
                )
                if (state?.deleteBtnIsVisible == true) {
                    BaseButton(
                        text = stringResource(id = R.string.delete),
                        bgColor = AppTheme.colors.static.primary,
                        onClick = viewModel::onDeleteClicked
                    )
                }
                Space(size = 10.dp)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun process(event: CreateGroupViewEvent): Unit = when (event) {
        is CreateGroupViewEvent.NavigateBack -> onBack()
        is CreateGroupViewEvent.NavigateToGroups -> navigator.backToGroups()
        is CreateGroupViewEvent.ShowDeleteDialog -> {
            BaseDialogFragment.show(
                fragmentManager = this.activity?.supportFragmentManager,
                title = getString(R.string.create_details_delete_title),
                content = getString(R.string.create_details_delete_content),
                positiveActionTitle = getString(R.string.create_details_positive_text),
                negativeActionTitle = getString(R.string.create_details_negative_text),
                onPositiveAction = viewModel::onDeleteConfirmed
            )
        }
    }

}
