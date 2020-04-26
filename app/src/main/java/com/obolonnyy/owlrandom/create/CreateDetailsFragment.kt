package com.obolonnyy.owlrandom.create

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.app.Navigator
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.viewModels

class CreateDetailsFragment : BaseFragment(R.layout.fragment_create_details) {

    private lateinit var recycler: RecyclerView
    private lateinit var titleEdit: TextInputEditText
    private val createDetailsAdapter = CreateDetailsAdapter(::onItemChanged)

    private val viewModel by viewModels { CreateDetailsViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.create_details_recycler)
        recycler.adapter = createDetailsAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        titleEdit = view.findViewById(R.id.create_details_item_title)
    }

    override fun onStart() {
        super.onStart()
        viewModel.viewState.observe(this, Observer(::render))
    }

    private fun render(state: CreateDetailsViewState) {
        createDetailsAdapter.setData(state.list)
    }

    private fun onItemChanged(text: String, item: CreateDetailsAdapterItem) {
        viewModel.onItemChanged(text, item)
    }
}
