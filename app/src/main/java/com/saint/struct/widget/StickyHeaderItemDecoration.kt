package com.saint.struct.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderItemDecoration(private val mListener: StickyHeaderInterface) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawStickyHeader(c, parent)
    }

    private fun drawStickyHeader(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (mListener.isStickyHeader(adapterPosition)) {
                // Draw your sticky header view here.
                // You can get the header view from the listener and draw it on the canvas.
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (mListener.isStickyHeader(position)) {
            // Set top margin to the height of your sticky header view.
            outRect.top = mListener.stickyHeaderHeight
        }
    }

    interface StickyHeaderInterface {
        fun isStickyHeader(position: Int): Boolean
        val stickyHeaderHeight: Int
    }
}
