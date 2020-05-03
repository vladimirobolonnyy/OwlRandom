package com.obolonnyy.owlrandom.presentation.create

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.sureMaterialDialog
import com.obolonnyy.owlrandom.utils.viewModels

class CreateDetailsFragment : BaseFragment(R.layout.fragment_create_details) {

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
    private lateinit var deleteBtn: View
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
        toolbar.setNavigationOnClickListener { navigateBack() }
        deleteBtn = view.findViewById<View>(R.id.create_details_btn_delete)
        deleteBtn.setOnClickListener {
            this.sureMaterialDialog(positive = { viewModel.delete() }, negative = {}).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.viewEvents, ::process)
        observe(viewModel.viewState, ::render)
    }

    private fun render(state: CreateDetailsViewState) {
        when (state) {
            CreateDetailsViewState.Empty -> {
                createDetailsAdapter.setData(emptyList())
                deleteBtn.isVisible = false
            }
            is CreateDetailsViewState.Loaded -> {
                deleteBtn.isVisible = state.deleteBtnIsVisible
                createDetailsAdapter.setData(state.list)
                if (titleEdit.text.toString() != state.title) {
                    titleEdit.setText(state.title)
                }
            }
        }
    }

    private fun process(event: CreateDetailsViewEvent) {
        when (event) {
            CreateDetailsViewEvent.NavigateToMain -> {
               navigator.goToMain()
            }
        }
    }

    private fun navigateBack() {
        activity?.onBackPressed()
    }

    private fun onItemChanged(text: String, item: CreateDetailsAdapterItem) {
        viewModel.onItemChanged(text, item)
    }
}
