package com.obolonnyy.owlrandom.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl
import com.orra.core_presentation.utils.activityViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by activityViewModel { MainActivityViewModel() }
    private val navigator: Navigator = NavigatorImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.viewEvents.observe(this, ::process)
    }

    private fun process(event: MainActivityViewEvent): Unit = when (event) {
        is MainActivityViewEvent.GoToMain -> navigator.goToMain()
    }
}
