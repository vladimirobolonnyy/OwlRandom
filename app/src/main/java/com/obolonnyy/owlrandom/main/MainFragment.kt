package com.obolonnyy.owlrandom.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.viewModels

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private lateinit var recycler: RecyclerView
    private val mainAdapter = MainAdapter(::onItemClicked)

    private lateinit var emptyText: View
    private val viewModel by viewModels { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.main_recycler)
        recycler.adapter = mainAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        emptyText = view.findViewById(R.id.main_empty_text)
        emptyText.setOnClickListener { addNewItem() }

        view.findViewById<View>(R.id.main_add_button).setOnClickListener { addNewItem() }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
        observe(viewModel.viewState, ::render)
    }

    private fun render(state: MainViewState) {
        emptyText.isVisible = state is MainViewState.Empty
        recycler.isGone = state is MainViewState.Empty
        when (state) {
            is MainViewState.Loaded -> mainAdapter.setData(state.groups)
        }
    }

    private fun onItemClicked(mainItem: MainItem) {
        navigator.goToCreateDetails(this, mainItem.groupId)
    }

    private fun addNewItem() {
        when (val state = viewModel.viewState.value) {
            MainViewState.Empty -> navigator.goToCreateDetails(this, 1)
            is MainViewState.Loaded -> {
                navigator.goToCreateDetails(this, (state.groups.size + 1).toLong())
            }
        }
    }
}