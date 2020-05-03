package com.obolonnyy.owlrandom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.app.NavigatorImpl

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator = NavigatorImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.goToMain()
    }
}
