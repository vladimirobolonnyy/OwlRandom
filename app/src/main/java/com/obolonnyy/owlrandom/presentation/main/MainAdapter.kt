package com.obolonnyy.owlrandom.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseViewHolder
import com.obolonnyy.owlrandom.model.MyGroup


class MainAdapter(
    private val onItemClicked: (MainItem) -> Unit
) : RecyclerView.Adapter<MainItemViewHolder>() {

    private val items = mutableListOf<MainItem>()
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        return MainItemViewHolder(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<MyGroup>) {
        val newList =
            list.map { MainItem(groupId = it.id, title = it.title, count = it.items.size) }
        this.items.clear()
        this.items.addAll(newList)
        notifyDataSetChanged()
    }
}

class MainItemViewHolder(
    viewGroup: ViewGroup,
    private val onItemClicked: (MainItem) -> Unit
) : BaseViewHolder(viewGroup, R.layout.item_main) {

    private val text: MaterialTextView = itemView.findViewById(R.id.main_item_text)

    fun bind(item: MainItem) {
        val message = "${item.title} (${item.count})"
        text.text = message
        itemView.setOnClickListener { onItemClicked(item) }
    }
}