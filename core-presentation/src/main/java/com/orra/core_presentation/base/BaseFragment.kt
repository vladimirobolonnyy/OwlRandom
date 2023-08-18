package com.orra.core_presentation.base

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.orra.core_presentation.base.views.ErrorView
import com.orra.core_presentation.base.views.InfoView
import com.orra.core_presentation.base.views.LoadingView
import com.orra.core_presentation.utils.className
import com.orra.core_presentation.utils.observeLD
import com.orra.core_presentation.utils.setThemedContent
import com.orra.core_ui.R
import kotlinx.coroutines.delay

abstract class BaseFragment : Fragment(R.layout.fragment_base) {

    protected val rootComposeView get() = contentView!!
    private var contentView: ComposeView? = null
    private var onRetryConsumers = mutableListOf<(ErrorAction) -> Unit>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.contentView).setThemedContent { FragmentContent() }
    }

    protected open fun <E : Any> observeBase(viewModel: BaseViewModel<E>) {
        observeLD(viewModel.modalLoadingState) {
            view?.findViewById<ComposeView>(R.id.loadingView)?.setThemedContent { LoadingView(it) }
        }
        observeLD(viewModel.errorState) {
            view?.findViewById<ComposeView>(R.id.errorView)?.setThemedContent {
                ErrorView(errorActions = it, onRetryClicked = viewModel::onRetryActions)
            }
        }
        observeLD(viewModel.notificationsEvents, ::renderNotifications)
        onRetryConsumers.add(viewModel::onRetryAction)
    }

    private fun renderNotifications(notification: Notification) {
        view?.findViewById<ComposeView>(R.id.infoView)?.setThemedContent {
            val isVisible = remember(notification) { mutableStateOf(true) }
            InfoView(notification, isVisible.value)
            LaunchedEffect(key1 = notification, block = {
                delay(2000)
                isVisible.value = false
            })
        }
    }


    @Composable
    open fun FragmentContent() {

    }

    protected fun DialogFragment.showDialog() {
        val manager = this@BaseFragment.requireActivity().supportFragmentManager
        manager.fragments.lastOrNull()?.let {
            if (it::class.java == this::class.java) {
                return
            }
        }
        show(manager, className())
    }

    override fun onDestroyView() {
        contentView = null
        super.onDestroyView()
    }

    protected fun onBack() {
        activity?.onBackPressed()
    }

}