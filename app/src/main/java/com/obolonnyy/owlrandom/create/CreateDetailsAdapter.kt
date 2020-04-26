package com.obolonnyy.owlrandom.create

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseViewHolder

class CreateDetailsAdapter (
    private val onItemChanged: (String, CreateDetailsAdapterItem) -> Unit
) : RecyclerView.Adapter<CreateDetailsViewHolder>() {

    private val items = mutableListOf<CreateDetailsAdapterItem>()
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateDetailsViewHolder {
        return CreateDetailsViewHolder(parent, onItemChanged)
    }

    override fun onBindViewHolder(holder: CreateDetailsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<CreateDetailsAdapterItem>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun setFocus(focusOnItem: Int) {

    }
}

class CreateDetailsViewHolder(
    viewGroup: ViewGroup,
    private val onItemChanged: (String, CreateDetailsAdapterItem) -> Unit
) : BaseViewHolder(viewGroup, R.layout.item_create_details) {

    private val edit: TextInputEditText = itemView.findViewById(R.id.create_details_item_edit)
    private var wrapper : SaveEditTextWrapper? = null

    fun bind(item: CreateDetailsAdapterItem) {
        edit.removeTextChangedListener(wrapper)
        edit.setText(item.text)
        wrapper = SaveEditTextWrapper(item, onItemChanged)
        edit.addTextChangedListener(wrapper)
        if (item.requestFocus) {
            edit.requestFocus(item.text.length)
        }
    }
}

class SaveEditTextWrapper (
    private val item: CreateDetailsAdapterItem,
    private val onItemChanged: (String, CreateDetailsAdapterItem) -> Unit
): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        onItemChanged(s.toString(), item)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}