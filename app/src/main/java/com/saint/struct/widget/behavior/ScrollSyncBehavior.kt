package com.saint.struct.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.widget.behavior.RecyclerViewBehavior

class ScrollSyncBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {
    constructor():this(null,null)
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    @Deprecated("Deprecated in Java")
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        val banner = coordinatorLayout.findViewById<View>(R.id.banner)
        banner.translationY = -dyConsumed.toFloat()
    }
}