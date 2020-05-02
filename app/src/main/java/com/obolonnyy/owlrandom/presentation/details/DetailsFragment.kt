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
            //todo move to vm
            showPickDialog()
        }
        view.findViewById<View>(R.id.details_btn_edit).setOnClickListener {
            //todo move to vm
            navigator.goToCreateDetails(this, groupId)
        }
        toolbar = view.findViewById(R.id.details_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.viewState, ::render)
    }

    private fun render(state: DetailsViewState) {
        when (state) {
            DetailsViewState.Empty -> renderEmptyState()
            DetailsViewState.Error -> {
            }
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

    private fun showPickDialog() {
        //Todo refactor here
        materialDialog().show {
            listItemsSingleChoice(
                items = RandomTypes.values().map { it.text }
            ) { _, index, text ->
                viewModel.onRandomTypePicked(index, text)
            }
        }
    }
}