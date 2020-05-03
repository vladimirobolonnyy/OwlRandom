package com.obolonnyy.owlrandom.base.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.utils.getColoredDrawable

/**
 * Простой декотарор - разделитель для RecyclerView.
 * По умолчанию это полоска шириной 1dp с бекграундом [R.drawable.line_divider].
 *
 * @param backgroundColor - если нужно задать другой цвет бекграунда, то можно утановить этот
 * параметр через context.resources.getColor(R.color.some_color). По умолчанию null.
 */
class SimpleDividerItemDecoration(
    context: Context,
    @ColorInt backgroundColor: Int? = null
) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable = if (backgroundColor != null) {
        context.getColoredDrawable(backgroundColor, R.drawable.line_divider)
    } else {
        context.resources.getDrawable(R.drawable.line_divider, context.theme)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}