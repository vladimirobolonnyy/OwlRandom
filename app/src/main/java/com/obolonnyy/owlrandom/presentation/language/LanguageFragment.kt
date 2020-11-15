package com.obolonnyy.owlrandom.presentation.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.ui.tooling.preview.Preview
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class LanguageFragment : BaseFragment(R.layout.fragment_language), CoroutineScope by MainScope() {

    private val viewModel by viewModels { LanguageViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    NewsStory()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.viewState, ::render)
        observe(viewModel.viewEvents, ::process)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    private fun render(state: LanguageViewState) {
        showMessage(state.toString())
    }

    private fun process(event: Throwable) {
        showError(event)
    }

    @Preview
    @Composable
    fun NewsStory() {
        Column {
            Text("A day in Shark Fin Cove")
            Text("Davenport, California")
            Text("December 2018")
        }
    }
}