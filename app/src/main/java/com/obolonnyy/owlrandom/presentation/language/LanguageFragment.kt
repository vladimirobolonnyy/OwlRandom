package com.obolonnyy.owlrandom.presentation.language

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.MainApplication
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.model.LanguageImages
import com.obolonnyy.owlrandom.utils.activityViewModels
import com.obolonnyy.owlrandom.utils.materialDialog
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.openImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import timber.log.Timber

class LanguageFragment : BaseFragment(R.layout.fragment_language), CoroutineScope by MainScope() {

    private val viewModel by activityViewModels { LanguageViewModel() }

    private val goodBadRating by lazy { requireView().findViewById<TextView>(R.id.good_bad_rating) }
    private val countWords by lazy { requireView().findViewById<TextView>(R.id.count_words) }
    private val topWord by lazy { requireView().findViewById<TextView>(R.id.top_word) }
    private val bottomWord by lazy { requireView().findViewById<TextView>(R.id.bottom_word) }
    private val showedAnswered by lazy { requireView().findViewById<TextView>(R.id.showed_answered) }
    private val timer by lazy { requireView().findViewById<TextView>(R.id.time) }
    private val picture1 by lazy { requireView().findViewById<ImageView>(R.id.image1) }
    private val picture2 by lazy { requireView().findViewById<ImageView>(R.id.image2) }
    private val picture3 by lazy { requireView().findViewById<ImageView>(R.id.image3) }
    private val pictures by lazy { listOf(picture1, picture2, picture3) }

    private val switch by lazy { requireView().findViewById<View>(R.id.switch_language) }
    private val translation by lazy { requireView().findViewById<View>(R.id.show_translation) }
    private val revert by lazy { requireView().findViewById<View>(R.id.revert) }
    private val next by lazy { requireView().findViewById<View>(R.id.next) }
    private val skip by lazy { requireView().findViewById<View>(R.id.skip_new) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switch.setOnClickListener { viewModel.onSwitchClicked() }
        revert.setOnClickListener { viewModel.onRevertClicked() }
        translation.setOnClickListener { viewModel.onTranslationClicked() }
        next.setOnClickListener { viewModel.onNextClicked() }
        skip.setOnClickListener { viewModel.onSkipClicked() }

        observe(viewModel.viewState, ::render)
        observe(viewModel.pictureState, ::renderPictures)
        observe(viewModel.wordNumberState, { clearImages() })
        observe(viewModel.viewEvents, ::process)
        observe(viewModel.timerEvents, { timer.text = it.getHumanTime() })
        observe(viewModel.errorViewState, ::showMessage)

        requestSignIn(requireContext())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnSuccessListener { account ->
                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
                        val credential =
                            GoogleAccountCredential.usingOAuth2(MainApplication.context, scopes)
                        credential.selectedAccount = account.account

                        val jsonFactory = JacksonFactory.getDefaultInstance()
//                        GoogleNetHttpTransport.newTrustedTransport()
                        val httpTransport = AndroidHttp.newCompatibleTransport()
                        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
                            .setApplicationName(getString(R.string.app_name))
                            .build()

                        viewModel.service = service
                    }
                    .addOnFailureListener { e ->
                        Timber.e(e)
                    }
            }
        }
    }

    private fun requestSignIn(context: Context) {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, signInOptions)
        startActivityForResult(client.signInIntent, REQUEST_SIGN_IN)
    }

    private fun render(state: LanguageViewState) {
        goodBadRating.text = state.goodBadRating
        countWords.text = state.countWords
        topWord.text = state.topWord
        bottomWord.text = state.bottomWord
        showedAnswered.text = state.showedAnswered
        bottomWord.isInvisible = !state.showBottom
    }

    private fun renderPictures(state: LanguageImages?) {
        pictures.forEach {
            it.isVisible = state != null
        }
        if (state != null) {
            pictures.forEachIndexed { i, view ->
                view.load(state.picturesUri[i])
                view.onClickOpenUri(state.picturesUri[i])
            }
        }
    }

    private fun clearImages() {
        pictures.forEach {
            it.isVisible = false
            it.setOnClickListener(null)
        }
    }

    private fun process(event: LanguageViewEvent) {
        when (event) {
            is LanguageViewEvent.Error -> showError(event.t)
            is LanguageViewEvent.Retry -> showRetryDialog(event)
        }
    }

    private var dialog: MaterialDialog? = null
    private fun showRetryDialog(event: LanguageViewEvent.Retry) {
        dialog = this.materialDialog()
        dialog!!.apply {
            title(
                text = getString(
                    R.string.material_dialog_retry_language_title,
                    event.answered.toString(),
                    event.notAnswered.toString()
                )
            )
            positiveButton(res = R.string.new_try, click = {
                viewModel.reload()
                dialog = null
            })
            negativeButton(res = R.string.retry_this, click = {
                viewModel.tryAgain()
                dialog = null
            })
            onDismiss { dialog = null }
        }
        dialog!!.show()
    }

    private fun View.onClickOpenUri(uri: Uri) {
        this.setOnClickListener { requireContext().openImage(uri) }
    }

    companion object {
        private const val REQUEST_SIGN_IN = 1
    }
}