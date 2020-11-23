package com.obolonnyy.owlrandom.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.api.services.sheets.v4.model.SpreadsheetProperties
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.MainApplication
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.log
import kotlinx.coroutines.*
import timber.log.Timber

class SettingsFragment : BaseFragment(R.layout.fragment_settings) , CoroutineScope by MainScope() {

    companion object {
        private const val REQUEST_SIGN_IN = 1
    }

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
        requestSignIn(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnSuccessListener { account ->
                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
                        val credential = GoogleAccountCredential.usingOAuth2(MainApplication.context, scopes)
                        credential.selectedAccount = account.account

                        val jsonFactory = JacksonFactory.getDefaultInstance()
                        // GoogleNetHttpTransport.newTrustedTransport()
                        val httpTransport = AndroidHttp.newCompatibleTransport()
                        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
                            .setApplicationName(getString(R.string.app_name))
                            .build()

//                        createSpreadsheet(service)
                    }
                    .addOnFailureListener { e ->
                        Timber.e(e)
                    }
            }
        }
    }


    private fun requestSignIn(context: Context) {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .build()
        val client = GoogleSignIn.getClient(context, signInOptions)
        startActivityForResult(client.signInIntent, REQUEST_SIGN_IN)
    }

    private fun createSpreadsheet(service: Sheets) {
        var spreadsheet = Spreadsheet()
            .setProperties(
                SpreadsheetProperties()
                    .setTitle("CreateNewSpreadsheet")
            )

        launch(Dispatchers.Default) {
//            spreadsheet = service.spreadsheets().create(spreadsheet).execute()
//            Timber.d("ID: ${spreadsheet.spreadsheetId}")

            val spr = service.spreadsheets().get("1b_itSmdduwk6OX_fXr7GrReSAEeuqU27jCJMFEmcWi8").apply {
                log("## values:= ${this.values}")

            }
        }
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