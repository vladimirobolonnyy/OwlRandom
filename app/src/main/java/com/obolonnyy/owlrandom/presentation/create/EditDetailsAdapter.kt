package com.obolonnyy.owlrandom.presentation.create

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseViewHolder


class CreateDetailsAdapter (
    private val onItemChanged: (String, EditDetailsAdapterItem) -> Unit
) : RecyclerView.Adapter<CreateDetailsViewHolder>() {

    private val items = mutableListOf<EditDetailsAdapterItem>()
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateDetailsViewHolder {
        return CreateDetailsViewHolder(parent, onItemChanged)
    }

    override fun onBindViewHolder(holder: CreateDetailsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<EditDetailsAdapterItem>){
        val callback = CreateDiffUtilCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(callback)
        this.items.clear()
        this.items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}

class CreateDetailsViewHolder(
    viewGroup: ViewGroup,
    private val onItemChanged: (String, EditDetailsAdapterItem) -> Unit
) : BaseViewHolder(viewGroup, R.layout.item_create_details) {

    private val edit: TextInputEditText = itemView.findViewById(R.id.create_details_item_edit)
    private var wrapper : SaveEditTextWrapper? = null

    fun bind(item: EditDetailsAdapterItem) {
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
    private val item: EditDetailsAdapterItem,
    private val onItemChanged: (String, EditDetailsAdapterItem) -> Unit
): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        onItemChanged(s.toString(), item)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

class CreateDiffUtilCallback(
    private val oldList: List<EditDetailsAdapterItem>,
    private val newList: List<EditDetailsAdapterItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].position == newList[newItemPosition].position
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].position == newList[newItemPosition].position
    }

}