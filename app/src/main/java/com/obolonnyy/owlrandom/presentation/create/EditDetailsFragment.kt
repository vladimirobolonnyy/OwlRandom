package com.obolonnyy.owlrandom.presentation.create

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseFragment
import com.obolonnyy.owlrandom.utils.observe
import com.obolonnyy.owlrandom.utils.viewModels

class EditDetailsFragment : BaseFragment(R.layout.fragment_create_details) {

    companion object {
        private const val GROUP_ID = "GROUP_ID"

        fun new(groupId: Long?): EditDetailsFragment {
            groupId ?: return EditDetailsFragment()
            return EditDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(GROUP_ID, groupId)
                }
            }
        }
    }

    private lateinit var titleEdit: TextInputEditText
    private lateinit var itemsEdit: TextInputEditText
    private lateinit var deleteBtn: View

    private val groupId: Long? by lazy { arguments?.getLong(GROUP_ID) }
    private val viewModel by viewModels { EditDetailsViewModel(groupId.takeIf { it != -1L }) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEdit = view.findViewById(R.id.create_details_item_title)
        itemsEdit = view.findViewById(R.id.create_details_items)
        val toolbar: MaterialToolbar = view.findViewById(R.id.create_details_toolbar)
        toolbar.setNavigationOnClickListener { navigateBack() }
        deleteBtn = view.findViewById<View>(R.id.create_details_btn_delete)
        deleteBtn.setOnClickListener {
//            this.sureMaterialDialog(positive = { viewModel.delete() }, negative = {}).show()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.onTitleChanged(titleEdit.text.toString())
        viewModel.onItemsChanged(itemsEdit.text.toString())
        viewModel.saveItems()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.viewEvents, ::process)
        observe(viewModel.viewState, ::render)
    }

    private fun render(state: CreateDetailsViewState) {
        deleteBtn.isVisible = state.deleteBtnIsVisible
        if (titleEdit.text.toString() != state.title) {
            titleEdit.setText(state.title)
        }
        if (itemsEdit.text.toString() != state.items) {
            itemsEdit.setText(state.items)
        }
    }

    private fun process(event: CreateDetailsViewEvent): Unit? = when (event) {
        is CreateDetailsViewEvent.NavigateToMain -> navigator.goToMain()
    }

    private fun navigateBack() {
        activity?.onBackPressed()
    }
}
