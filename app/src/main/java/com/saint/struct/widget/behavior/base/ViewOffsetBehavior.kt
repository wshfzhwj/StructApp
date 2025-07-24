package com.saint.struct.widget.behavior.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

open class ViewOffsetBehavior<V : View>(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<V>(context, attrs) {
    private var mViewOffsetHelper: ViewOffsetHelper? = null

    private var mTempTopBottomOffset = 0
    private var mTempLeftRightOffset = 0

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        // First let lay the child out
        layoutChild(parent, child, layoutDirection)

        if (mViewOffsetHelper == null) {
            mViewOffsetHelper = ViewOffsetHelper(child)
        }
        mViewOffsetHelper!!.onViewLayout()

        if (mTempTopBottomOffset != 0) {
            mViewOffsetHelper!!.setTopAndBottomOffset(mTempTopBottomOffset)
            mTempTopBottomOffset = 0
        }
        if (mTempLeftRightOffset != 0) {
            mViewOffsetHelper!!.setLeftAndRightOffset(mTempLeftRightOffset)
            mTempLeftRightOffset = 0
        }

        return true
    }

    protected open fun layoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int) {
        // Let the parent lay it out by default
        parent.onLayoutChild(child, layoutDirection)
    }

    fun setTopAndBottomOffset(offset: Int): Boolean {
        if (mViewOffsetHelper != null) {
            return mViewOffsetHelper!!.setTopAndBottomOffset(offset)
        } else {
            mTempTopBottomOffset = offset
        }
        return false
    }

    fun setLeftAndRightOffset(offset: Int): Boolean {
        if (mViewOffsetHelper != null) {
            return mViewOffsetHelper!!.setLeftAndRightOffset(offset)
        } else {
            mTempLeftRightOffset = offset
        }
        return false
    }

    val topAndBottomOffset: Int
        get() = if (mViewOffsetHelper != null) mViewOffsetHelper!!.topAndBottomOffset else 0

    val leftAndRightOffset: Int
        get() = if (mViewOffsetHelper != null) mViewOffsetHelper!!.leftAndRightOffset else 0
}
