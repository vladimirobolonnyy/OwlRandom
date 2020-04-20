package com.obolonnyy.owlrandom.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/*
* Use
*
*         fun getIntent(context: Context, param: String): Intent {
            return FragmentContainerActivity.fromFactory(context) {
                SomeFragment().apply {
                    this.param = param
                }
            }
        }
*
*
* */
class FragmentContainerActivity : AppCompatActivity() {

	companion object {

		const val ARG_SCREEN_ORIENTATION = "ARG_SCREEN_ORIENTATION"

		inline fun fromFactory(
				context: Context,
				screenOrientation: Int? = null,
				crossinline constructor: () -> Fragment
		): Intent {
			return Intent(context, FragmentContainerActivity::class.java)
					.apply {
						putFragmentFactory(constructor)
						screenOrientation?.let { putExtra(ARG_SCREEN_ORIENTATION, it) }
					}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		requestedOrientation =
				intent.getIntExtra(ARG_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
		window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

		if (savedInstanceState == null) {
			val fragmentFactory = intent.getFragmentFactory()
			supportFragmentManager
					.beginTransaction()
					.add(android.R.id.content, fragmentFactory.createFragment())
					.commit()
		}
	}

}
