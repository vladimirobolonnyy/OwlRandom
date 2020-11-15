package com.obolonnyy.owlrandom.presentation.language

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class LanguageFragment : BaseFragment(R.layout.fragment_language), CoroutineScope by MainScope() {

    private val viewModel by viewModels { LanguageViewModel() }

    private val goodBadRating by lazy { requireView().findViewById<TextView>(R.id.good_bad_rating) }
    private val countWords by lazy { requireView().findViewById<TextView>(R.id.count_words) }
    private val topWord by lazy { requireView().findViewById<TextView>(R.id.top_word) }
    private val bottomWord by lazy { requireView().findViewById<TextView>(R.id.bottom_word) }
    private val showedAnswered by lazy { requireView().findViewById<TextView>(R.id.showed_answered) }

    private val switch by lazy { requireView().findViewById<View>(R.id.switch_language) }
    private val revert by lazy { requireView().findViewById<View>(R.id.revert) }
    private val translation by lazy { requireView().findViewById<View>(R.id.show_translation) }
    private val next by lazy { requireView().findViewById<View>(R.id.next) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switch.setOnClickListener { viewModel.onSwitchClicked() }
        revert.setOnClickListener { viewModel.onRevertClicked() }
        translation.setOnClickListener { viewModel.onTranslationClicked() }
        next.setOnClickListener { viewModel.onNextClicked() }

        observe(viewModel.viewState, ::render)
        observe(viewModel.viewEvents, ::process)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    private fun render(state: LanguageViewState) {
        goodBadRating.text = state.goodBadRating
        countWords.text = state.countWords
        topWord.text = state.topWord
        bottomWord.text = state.bottomWord
        showedAnswered.text = state.showedAnswered
        bottomWord.isInvisible = !state.showBottom
    }

    private fun process(event: Throwable) {
        showError(event)
    }
}