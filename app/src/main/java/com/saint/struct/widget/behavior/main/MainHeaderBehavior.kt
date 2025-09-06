package com.saint.struct.widget.behavior.main

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.widget.NestedLinearLayout
import com.saint.struct.widget.behavior.base.ViewOffsetBehavior
import java.lang.ref.WeakReference

class MainHeaderBehavior(val mContext: Context?, attrs: AttributeSet?): ViewOffsetBehavior<View>(mContext, attrs){
    private var mCurState: Int = STATE_OPENED
    private var mHeaderStateListener: OnHeaderStateListener? = null

    private var mOverScroller: OverScroller = OverScroller(mContext)

    private lateinit var mParent: WeakReference<CoordinatorLayout> //CoordinatorLayout
    private lateinit var mChild: WeakReference<View> //CoordinatorLayout的子View，即header

    //界面整体向上滑动，达到列表可滑动的临界点
    private var upReach = false

    //列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private var downReach = false

    //列表上一个全部可见的item位置
    private var lastPosition = -1

    private var mFlingRunnable: FlingRunnable? = null

    //tab上移结束后是否悬浮在固定位置
    private var tabSuspension = false

    override fun layoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        super.layoutChild(parent, child, layoutDirection)
        mParent = WeakReference<CoordinatorLayout>(parent)
        mChild = WeakReference<View>(child)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        if (tabSuspension) {
            return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !this.isClosed
        }
        return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }


    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        lastPosition = -1
        return !this.isClosed
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: View,
        ev: MotionEvent
    ): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downReach = false
                upReach = false
            }

            MotionEvent.ACTION_UP -> handleActionUp(child)
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }


    /**
     * @param coordinatorLayout
     * @param child             代表header
     * @param target            代表RecyclerView
     * @param dx
     * @param dy                上滑 dy>0， 下滑dy<0
     * @param consumed
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        //制造滑动视察，使header的移动比手指滑动慢
        val scrollY = dy / 4.0f

        if (target is NestedLinearLayout) { //处理header滑动
            var finalY = child.getTranslationY() - scrollY
            if (finalY < this.headerOffset) {
                finalY = this.headerOffset.toFloat()
            } else if (finalY > 0) {
                finalY = 0f
            }
            child.translationY = finalY
            consumed[1] = dy
        } else if (target is RecyclerView) { //处理列表滑动
            val list = target
            val pos =
                (list.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

            //header closed状态下，列表上滑再下滑到第一个item全部显示，此时不让CoordinatorLayout整体下滑，
            //手指重新抬起再下滑才可以整体滑动
            if (pos == 0 && pos < lastPosition) {
                downReach = true
            }

            if (pos == 0 && canScroll(child, scrollY)) {
                //如果列表第一个item全部可见、或者header已展开，则让CoordinatorLayout消费掉事件
                var finalY = child.getTranslationY() - scrollY
                //header已经closed，整体不能继续上滑，手指抬起重新上滑列表开始滚动
                if (finalY < this.headerOffset) {
                    finalY = this.headerOffset.toFloat()
                    upReach = true
                } else if (finalY > 0) { //header已经opened，整体不能继续下滑
                    finalY = 0f
                }
                child.setTranslationY(finalY)
                consumed[1] = dy //让CoordinatorLayout消费掉事件，实现整体滑动
            }
            lastPosition = pos
        }
    }

    /**
     * 是否可以整体滑动
     *
     * @return
     */
    private fun canScroll(child: View, scrollY: Float): Boolean {
        if (scrollY > 0 && child.getTranslationY() > this.headerOffset) {
            return true
        }

        if (child.getTranslationY() == this.headerOffset.toFloat() && upReach) {
            return true
        }

        if (scrollY < 0 && !downReach) {
            return true
        }

        return false
    }

    private val headerOffset: Int
        get() = mContext!!.getResources().getDimensionPixelOffset(R.dimen.dp_m_90)

    private fun handleActionUp(child: View) {
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable)
            mFlingRunnable = null
        }
        //手指抬起时，header上滑距离超过总距离三分之一，则整体自动上滑到关闭状态
        if (child.getTranslationY() < this.headerOffset / 3.0f) {
            scrollToClose(MainHeaderBehavior.Companion.DURATION_SHORT)
        } else {
            scrollToOpen(MainHeaderBehavior.Companion.DURATION_SHORT)
        }
    }

    private fun onFlingFinished(layout: View) {
        changeState(if (isClosed(layout)) MainHeaderBehavior.Companion.STATE_CLOSED else MainHeaderBehavior.Companion.STATE_OPENED)
    }

    /**
     * 直接展开
     */
    fun openHeader() {
        openHeader(MainHeaderBehavior.Companion.DURATION_LONG)
    }

    private fun openHeader(duration: Int) {
        if (this.isClosed && mChild!!.get() != null) {
            if (mFlingRunnable != null) {
                mChild!!.get()!!.removeCallbacks(mFlingRunnable)
                mFlingRunnable = null
            }
            scrollToOpen(duration)
        }
    }

    fun closeHeader() {
        closeHeader(MainHeaderBehavior.Companion.DURATION_LONG)
    }

    private fun closeHeader(duration: Int) {
        if (!this.isClosed && mChild!!.get() != null) {
            if (mFlingRunnable != null) {
                mChild!!.get()!!.removeCallbacks(mFlingRunnable)
                mFlingRunnable = null
            }
            scrollToClose(duration)
        }
    }

    private fun isClosed(child: View): Boolean {
        return child.getTranslationY() == this.headerOffset.toFloat()
    }

    val isClosed: Boolean
        get() = mCurState == MainHeaderBehavior.Companion.STATE_CLOSED

    private fun changeState(newState: Int) {
        if (mCurState != newState) {
            mCurState = newState

            if (mHeaderStateListener == null) {
                return
            }

            if (mCurState == MainHeaderBehavior.Companion.STATE_OPENED) {
                mHeaderStateListener!!.onHeaderOpened()
            } else {
                mHeaderStateListener!!.onHeaderClosed()
            }
        }
    }

    private fun scrollToClose(duration: Int) {
        val curTranslationY = mChild!!.get()!!.getTranslationY().toInt()
        val dy = this.headerOffset - curTranslationY
        mOverScroller!!.startScroll(0, curTranslationY, 0, dy, duration)
        start()
    }

    private fun scrollToOpen(duration: Int) {
        val curTranslationY = mChild!!.get()!!.getTranslationY()
        mOverScroller!!.startScroll(
            0,
            curTranslationY.toInt(),
            0,
            -curTranslationY.toInt(),
            duration
        )
        start()
    }

    private fun start() {
        if (mOverScroller!!.computeScrollOffset()) {
            mFlingRunnable = this@MainHeaderBehavior.FlingRunnable(mParent!!.get(), mChild!!.get())
            ViewCompat.postOnAnimation(mChild!!.get()!!, mFlingRunnable!!)
        } else {
            onFlingFinished(mChild!!.get()!!)
        }
    }

    private inner class FlingRunnable(
        private val mParent: CoordinatorLayout?,
        private val mLayout: View?
    ) : Runnable {
        override fun run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller!!.computeScrollOffset()) {
                    mLayout.translationY = mOverScroller!!.currY.toFloat()
                    ViewCompat.postOnAnimation(mLayout, this)
                } else {
                    onFlingFinished(mLayout)
                }
            }
        }
    }

    fun setTabSuspension(tabSuspension: Boolean) {
        this.tabSuspension = tabSuspension
    }

    fun setHeaderStateListener(headerStateListener: OnHeaderStateListener?) {
        mHeaderStateListener = headerStateListener
    }

    interface OnHeaderStateListener {
        fun onHeaderClosed()

        fun onHeaderOpened()
    }

    companion object {
        private const val STATE_OPENED = 0
        private const val STATE_CLOSED = 1
        private const val DURATION_SHORT = 300
        private const val DURATION_LONG = 600
    }
}