package com.saint.struct.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.widget.behavior.RecyclerViewBehavior

class SampleHeaderBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<TextView?> (context, attrs){
    constructor():this(null,null)

    // 界面整体向上滑动，达到列表可滑动的临界点
    private var upReach = false

    // 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private var downReach = false

    // 列表上一个全部可见的item位置
    private var lastPosition = -1

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: TextView,
        ev: MotionEvent
    ): Boolean {
        when (ev.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                downReach = false
                upReach = false
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (target is RecyclerView) {
            val list = target
            // 列表第一个全部可见Item的位置
            val pos =
                (list.getLayoutManager() as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            if (pos == 0 && pos < lastPosition) {
                downReach = true
            }
            // 整体可以滑动，否则RecyclerView消费滑动事件
            if (canScroll(child, dy.toFloat()) && pos == 0) {
                var finalY = child.getTranslationY() - dy
                if (finalY < -child.getHeight()) {
                    finalY = -child.getHeight().toFloat()
                    upReach = true
                } else if (finalY > 0) {
                    finalY = 0f
                }
                child.setTranslationY(finalY)
                // 让CoordinatorLayout消费滑动事件
                consumed[1] = dy
            }
            lastPosition = pos
        }
    }

    private fun canScroll(child: View, scrollY: Float): Boolean {
        if (scrollY > 0 && child.getTranslationY() == -child.getHeight().toFloat() && !upReach) {
            return false
        }

        if (downReach) {
            return false
        }
        return true
    }
}
