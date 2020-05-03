package com.obolonnyy.owlrandom.presentation.details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.appbar.MaterialToolbar
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.materialDialog
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.viewModels

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    companion object {
        private const val GROUP_ID = "GROUP_ID"

        fun new(groupId: Long): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(GROUP_ID, groupId)
                }
            }
        }
    }

    private val groupId by lazy { arguments!!.getLong(GROUP_ID) }
    private lateinit var recycler: RecyclerView
    private val detailsAdapter: DetailsAdapter = DetailsAdapter()
    private lateinit var toolbar: MaterialToolbar

    private val viewModel by viewModels { DetailsViewModel(groupId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.details_recycler)
        recycler.apply {
            adapter = detailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        view.findViewById<View>(R.id.details_btn_roll).setOnClickListener {
            viewModel.onRollClicked()
        }
        view.findViewById<View>(R.id.details_btn_edit).setOnClickListener {
            viewModel.onEditClicked()
        }
        toolbar = view.findViewById(R.id.details_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.viewState, ::render)
        observe(viewModel.viewEvents, ::process)
    }

    private fun process(event: DetailsViewEvent) {
        when (event) {
           is  DetailsViewEvent.ShowPickDialog -> showPickDialog(event.items)
           is DetailsViewEvent.NavigateToEdit -> {
               navigator.goToCreateDetails(event.groupId)
           }
        }
    }

    private fun render(state: DetailsViewState) {
        when (state) {
            DetailsViewState.Empty -> renderEmptyState()
            DetailsViewState.Error -> toolbar.title = "Error"
            is DetailsViewState.Loaded -> renderState(state)
        }
    }

    private fun renderEmptyState() {
        detailsAdapter.setData(emptyList())
        toolbar.title = ""
    }

    private fun renderState(state: DetailsViewState.Loaded) {
        detailsAdapter.setData(state.adapterItems)
        toolbar.title = state.group.title
    }

    private fun showPickDialog(items: List<String>) {
        materialDialog().show {
            listItemsSingleChoice( items = items) { _, index, text ->
                viewModel.onRandomTypePicked(index, text)
            }
        }
    }
}