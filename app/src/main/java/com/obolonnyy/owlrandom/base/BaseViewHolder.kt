package com.obolonnyy.owlrandom.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.obolonnyy.owlrandom.utils.inflateView

open class BaseViewHolder(parent: ViewGroup, @LayoutRes res: Int) :
    RecyclerView.ViewHolder(parent.inflateView(res)) {

}