package com.obolonnyy.owlrandom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.obolonnyy.owlrandom.app.Navigator

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator = Navigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.goToMain()
    }
}
