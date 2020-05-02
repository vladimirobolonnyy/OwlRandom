package com.obolonnyy.owlrandom.presentation.details

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseViewHolder
import com.obolonnyy.owlrandom.presentation.create.CreateDetailsAdapterItem
import com.obolonnyy.owlrandom.utils.clearAndAdd
import com.obolonnyy.owlrandom.utils.setBgColor


class DetailsAdapter(
) : RecyclerView.Adapter<DetailsViewHolder>() {

    private val items = mutableListOf<DetailsAdapterItem>()
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<DetailsAdapterItem>) {
        val diffResult = DiffUtil.calculateDiff(CreateDiffUtilCallback(items, list))
        items.clearAndAdd(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CreateDiffUtilCallback(
        private val oldList: List<DetailsAdapterItem>,
        private val newList: List<DetailsAdapterItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.position == new.position
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}

class DetailsViewHolder(
    viewGroup: ViewGroup,
    private val onItemChanged: (String, CreateDetailsAdapterItem) -> Unit = { _, _ -> }
) : BaseViewHolder(viewGroup, R.layout.item_details) {
    private val text: MaterialTextView = itemView.findViewById(R.id.details_item_text)

    fun bind(item: DetailsAdapterItem) {
        text.text = item.text
        //Setting a custom background is not supported. todo check this
        itemView.setBgColor(item.bgColor)
    }
}