package com.obolonnyy.owlrandom.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.materialDialog
import com.obolonnyy.owlrandom.utils.viewModels

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private lateinit var recycler: RecyclerView
    private lateinit var emptyText: View
    private val viewModel by viewModels { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.main_recycler)
        emptyText = view.findViewById(R.id.main_empty_text)
        emptyText.setOnClickListener { showPickDialog() }
    }

    override fun onStart() {
        super.onStart()
        //todo временно
        navigator.goToCreateDetails()
    }

    private fun showPickDialog() {
        navigator.goToCreateDetails()
    }
}