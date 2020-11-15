package com.obolonnyy.owlrandom.presentation.language

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.activityViewModels
import com.obolonnyy.owlrandom.utils.materialDialog
import com.obolonnyy.owlrandom.utils.observe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class LanguageFragment : BaseFragment(R.layout.fragment_language), CoroutineScope by MainScope() {

    private val viewModel by activityViewModels { LanguageViewModel() }

    private val goodBadRating by lazy { requireView().findViewById<TextView>(R.id.good_bad_rating) }
    private val countWords by lazy { requireView().findViewById<TextView>(R.id.count_words) }
    private val topWord by lazy { requireView().findViewById<TextView>(R.id.top_word) }
    private val bottomWord by lazy { requireView().findViewById<TextView>(R.id.bottom_word) }
    private val showedAnswered by lazy { requireView().findViewById<TextView>(R.id.showed_answered) }
    private val timer by lazy { requireView().findViewById<TextView>(R.id.time) }

    private val switch by lazy { requireView().findViewById<View>(R.id.switch_language) }
    private val translation by lazy { requireView().findViewById<View>(R.id.show_translation) }
    private val revert by lazy { requireView().findViewById<View>(R.id.revert) }
    private val next by lazy { requireView().findViewById<View>(R.id.next) }
    private val skip by lazy { requireView().findViewById<View>(R.id.skip) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switch.setOnClickListener { viewModel.onSwitchClicked() }
        revert.setOnClickListener { viewModel.onRevertClicked() }
        translation.setOnClickListener { viewModel.onTranslationClicked() }
        next.setOnClickListener { viewModel.onNextClicked() }
        skip.setOnClickListener { viewModel.onSkipClicked() }

        observe(viewModel.viewState, ::render)
        observe(viewModel.viewEvents, ::process)
        observe(viewModel.timerEvents, { timer.text = it.getHumanTime() })
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startTimer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    private fun render(state: LanguageViewState) {
        goodBadRating.text = state.goodBadRating
        countWords.text = state.countWords
        topWord.text = state.topWord
        bottomWord.text = state.bottomWord
        showedAnswered.text = state.showedAnswered
        bottomWord.isInvisible = !state.showBottom
    }

    private fun process(event: LanguageViewEvent) {
        when (event) {
            is LanguageViewEvent.Error -> showError(event.t)
            is LanguageViewEvent.Retry -> showRetryDialog(event)
        }
    }

    private fun showRetryDialog(event: LanguageViewEvent.Retry) {
        this.materialDialog().apply {
            title(
                text = getString(
                    R.string.material_dialog_retry_language_title,
                    event.answered.toString(),
                    event.notAnswered.toString()
                )
            )
            positiveButton(res = R.string.retry, click = { viewModel.reload() })
            negativeButton(res = R.string.cancel, click = {})
        }.show()
    }
}