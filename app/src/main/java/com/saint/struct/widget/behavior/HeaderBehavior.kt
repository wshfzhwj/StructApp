package com.saint.struct.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.saint.struct.widget.behavior.xiami.XiamiTitleBehavior

class HeaderBehavior(context: Context?, attributeSet: AttributeSet?) : CoordinatorLayout.Behavior<View>(context,attributeSet) {
    constructor() : this(null, null)
    private var mLayoutTop = 0

    /**
     * 上一次进行完滑动后，滑动到纵向的哪个位置，可以理解为
     * 一个纵向的坐标，标记的是 Header 的 getTop() 的值，
     * 在 [-headerHeight , 0] 之间
     */
    private var mOffsetTopAndBottom = 0

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        // 一种比较偷懒的方法，让 CoordinatorLayout 去布局，并且返回 true 表示
        // Behavior 进行布局
        parent.onLayoutChild(child, layoutDirection)
        mLayoutTop = child.top
        return true
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // 先计算假如传入的 dy 如果都消费掉，偏移的位置
        var offset = mOffsetTopAndBottom - dy
        // 偏移范围，当 child 的 getTop() 为 0 时是初始状态，child
        // 在屏幕顶部，不能再向下滑；当 child 的 getTop() 为 -child.height
        // 时，child 的下边缘在屏幕顶部，不能再向上滑
        val minOffset = -getChildScrollingRange(child)
        val maxOffset = 0

        if (offset < minOffset) {
            offset = minOffset
        } else if (offset > maxOffset) {
            offset = maxOffset
        }

//        ViewCompat.offsetTopAndBottom(child, -mOffsetTopAndBottom + offset)
        ViewCompat.offsetTopAndBottom(child, offset - (child.top - mLayoutTop))
        // 本次滑动消费掉的滑动距离
        consumed[1] = mOffsetTopAndBottom - offset
        // 记录本次滑动到的位置
        mOffsetTopAndBottom = offset
    }

    // 获取 Header 滑动的最大距离
    private fun getChildScrollingRange(child: View?): Int {
        return child?.height ?: 0
    }
}