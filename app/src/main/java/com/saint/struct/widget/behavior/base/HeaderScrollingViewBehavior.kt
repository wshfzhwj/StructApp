package com.saint.struct.widget.behavior.base

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import androidx.core.math.MathUtils
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

abstract class HeaderScrollingViewBehavior(context: Context?, attrs: AttributeSet?): ViewOffsetBehavior<View>(context, attrs) {
    val mTempRect1: Rect = Rect()
    val mTempRect2: Rect = Rect()

    constructor():this(null,null)

    /**
     * The gap between the top of the scrolling view and the bottom of the header layout in pixels.
     */
    var verticalLayoutGap: Int = 0
        private set
    /**
     * Returns the distance that this view should overlap any [AppBarLayout].
     */
    /**
     * Set the distance that this view should overlap any [AppBarLayout].
     *
     * @param overlayTop the distance in px
     */
    var overlayTop: Int = 0

    override fun onMeasureChild(
        parent: CoordinatorLayout, child: View,
        parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        val childLpHeight = child.getLayoutParams().height
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
            || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT
        ) {
            // If the menu's height is set to match_parent/wrap_content then measure it
            // with the maximum visible height

            val dependencies = parent.getDependencies(child)
            val header = findFirstDependency(dependencies)
            if (header != null) {
                if (ViewCompat.getFitsSystemWindows(header)
                    && !ViewCompat.getFitsSystemWindows(child)
                ) {
                    // If the header is fitting system windows then we need to also,
                    // otherwise we'll get CoL's compatible measuring
                    ViewCompat.setFitsSystemWindows(child, true)

                    if (ViewCompat.getFitsSystemWindows(child)) {
                        // If the set succeeded, trigger a new layout and return true
                        child.requestLayout()
                        return true
                    }
                }

                var availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
                if (availableHeight == 0) {
                    // If the measure spec doesn't specify a size, use the current height
                    availableHeight = parent.getHeight()
                }

                val height = (availableHeight - header.getMeasuredHeight()
                        + getScrollRange(header))
                val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    height,
                    if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT)
                        View.MeasureSpec.EXACTLY
                    else
                        View.MeasureSpec.AT_MOST
                )

                // Now measure the scrolling view with the correct height
                parent.onMeasureChild(
                    child, parentWidthMeasureSpec,
                    widthUsed, heightMeasureSpec, heightUsed
                )

                return true
            }
        }
        return false
    }

    override fun layoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        val dependencies = parent.getDependencies(child!!)
        val header = findFirstDependency(dependencies)

        if (header != null) {
            val lp =
                child.getLayoutParams() as CoordinatorLayout.LayoutParams
            val available = mTempRect1
            available.set(
                parent.getPaddingLeft() + lp.leftMargin,
                header.getBottom() + lp.topMargin,
                parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                parent.getHeight() + header.getBottom() - parent.getPaddingBottom() - lp.bottomMargin
            )

            //修改代码，通过反射执行getLastWindowInsets()方法
            try {
                val method = parent.javaClass.getDeclaredMethod(
                    "getLastWindowInsets",
                    *arrayOf<Any?>() as Array<Class<*>?>
                )
                method.setAccessible(true)
                val parentInsets = method.invoke(parent) as WindowInsetsCompat?
                if (parentInsets != null && ViewCompat.getFitsSystemWindows(parent)
                    && !ViewCompat.getFitsSystemWindows(child)
                ) {
                    // If we're set to handle insets but this child isn't, then it has been measured as
                    // if there are no insets. We need to lay it out to match horizontally.
                    // Top and bottom and already handled in the logic above
                    available.left += parentInsets.getSystemWindowInsetLeft()
                    available.right -= parentInsets.getSystemWindowInsetRight()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val out = mTempRect2
            GravityCompat.apply(
                resolveGravity(lp.gravity), child.getMeasuredWidth(),
                child.getMeasuredHeight(), available, out, layoutDirection
            )

            val overlap = getOverlapPixelsForOffset(header)

            child.layout(out.left, out.top - overlap, out.right, out.bottom - overlap)
            this.verticalLayoutGap = out.top - header.getBottom()
        } else {
            // If we don't have a dependency, let super handle it
            super.layoutChild(parent, child, layoutDirection)
            this.verticalLayoutGap = 0
        }
    }

    fun getOverlapRatioForOffset(header: View?): Float {
        return 1f
    }

    fun getOverlapPixelsForOffset(header: View?): Int {
        return if (this.overlayTop == 0) 0 else MathUtils.clamp(
            (getOverlapRatioForOffset(header) * this.overlayTop).toInt(), 0, this.overlayTop
        )
    }

    protected abstract fun findFirstDependency(views: MutableList<View>): View?

    protected open fun getScrollRange(v: View): Int {
        return v.getMeasuredHeight()
    }

    companion object {
        private fun resolveGravity(gravity: Int): Int {
            return if (gravity == Gravity.NO_GRAVITY) GravityCompat.START or Gravity.TOP else gravity
        }
    }
}
