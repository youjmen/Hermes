package com.jaemin.hermes.features.main.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BookThumbnailItemDecoration(private val space : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = space
    }
}