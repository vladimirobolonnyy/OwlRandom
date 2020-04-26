package com.obolonnyy.owlrandom.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.materialDialog
import com.obolonnyy.owlrandom.utils.viewModels

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private lateinit var recycler: RecyclerView
    private lateinit var rollButton: View

    private val viewModel by viewModels { DetailsViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.details_recycler)
        rollButton = view.findViewById(R.id.details_btn_roll)
        rollButton.setOnClickListener { showPickDialog() }
    }

    private fun showPickDialog() {
        materialDialog().show {
            positiveButton(R.string.submit)
        }
    }

}