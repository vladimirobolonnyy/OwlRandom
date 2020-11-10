package com.obolonnyy.owlrandom.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {


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


    @Composable
    fun NewsStory() {
        Text("A day in Shark Fin Cove")
        Text("Davenport, California")
        Text("December 2018")
    }
}