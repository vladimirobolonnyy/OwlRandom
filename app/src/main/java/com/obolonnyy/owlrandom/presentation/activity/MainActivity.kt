package com.obolonnyy.owlrandom.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.obolonnyy.owlrandom.utils.viewModels

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels { MainActivityViewModel() }
    private val navigator: Navigator = NavigatorImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.viewEvents.observe(this, ::process)
        findViewById<View>(R.id.btn_main).setOnClickListener { navigator.goToMain() }
        findViewById<View>(R.id.btn_language).setOnClickListener { navigator.goToLanguage() }
        findViewById<View>(R.id.btn_settings).setOnClickListener { navigator.goToSettings() }
    }

    private fun process(event: MainActivityViewEvent): Unit = when (event) {
        is MainActivityViewEvent.GoToMain -> navigator.goToMain()
        is MainActivityViewEvent.GoToLanguage -> navigator.goToLanguage()
        is MainActivityViewEvent.GoToSettings -> navigator.goToSettings()
    }
}
