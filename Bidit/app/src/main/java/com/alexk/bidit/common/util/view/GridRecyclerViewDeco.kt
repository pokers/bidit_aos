package com.alexk.bidit.common.util.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridRecyclerViewDeco(
    private val leftMargin: Int,
    private val rightMargin: Int,
    private val topMargin: Int,
    private val bottomMargin: Int
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            top = topMargin
            bottom = bottomMargin
            left = leftMargin
            right = rightMargin
        }
    }
}