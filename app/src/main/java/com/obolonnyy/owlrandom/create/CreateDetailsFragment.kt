package com.obolonnyy.owlrandom.create

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.viewModels

class CreateDetailsFragment private constructor() : BaseFragment(R.layout.fragment_create_details) {

    companion object {
        private const val GROUP_ID = "GROUP_ID"

        fun new(groupId: Long): CreateDetailsFragment {
            return CreateDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(GROUP_ID, groupId)
                }
            }
        }
    }

    private lateinit var recycler: RecyclerView
    private lateinit var titleEdit: TextInputEditText
    private val createDetailsAdapter = CreateDetailsAdapter(::onItemChanged)
    private val groupId by lazy { arguments!!.getLong(GROUP_ID) }
    private val viewModel by viewModels { CreateDetailsViewModel(groupId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.create_details_recycler)
        recycler.adapter = createDetailsAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        titleEdit = view.findViewById(R.id.create_details_item_title)
        titleEdit.doAfterTextChanged { viewModel.onTitleChanged(it.toString()) }
        val toolbar: MaterialToolbar = view.findViewById(R.id.create_details_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onStart() {
        super.onStart()
        observe(viewModel.viewState, ::render)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    private fun render(state: CreateDetailsViewState) {
        createDetailsAdapter.setData(state.list)
        if (titleEdit.text.toString() != state.title) {
            titleEdit.setText(state.title)
        }
    }

    private fun onItemChanged(text: String, item: CreateDetailsAdapterItem) {
        viewModel.onItemChanged(text, item)
    }
}
