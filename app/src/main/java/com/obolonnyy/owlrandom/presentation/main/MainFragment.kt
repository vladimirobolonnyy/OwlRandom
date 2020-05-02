package com.obolonnyy.owlrandom.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.base.view.SimpleDividerItemDecoration
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
        recycler.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        }
        emptyText = view.findViewById(R.id.main_empty_text)
        emptyText.setOnClickListener { addNewItem() }

        view.findViewById<View>(R.id.main_add_button).setOnClickListener { addNewItem() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
//        navigator.goToCreateDetails(this, mainItem.groupId)
        navigator.goToDetails(this, mainItem.groupId)
    }

    private fun addNewItem() {
        //todo remove in view model
        when (val state = viewModel.viewState.value) {
            MainViewState.Empty -> navigator.goToCreateDetails(this, 1)
            is MainViewState.Loaded -> {
                navigator.goToCreateDetails(this, (state.groups.size + 1).toLong())
            }
        }
    }
}