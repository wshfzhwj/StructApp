package com.saint.struct.widget.behavior.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import com.saint.struct.R
import com.saint.struct.widget.behavior.base.HeaderScrollingViewBehavior
import kotlin.math.max

/**
 * 列表Behavior
 */
class MainContentBehavior(val mContext: Context?, attrs: AttributeSet?) :
    HeaderScrollingViewBehavior(mContext, attrs) {
    constructor() : this(null, null)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return isDependOn(dependency)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val contentScrollY =
            dependency.translationY / this.headerOffset * (dependency.height - this.finalHeight)
        val y = dependency.height - contentScrollY
        child.y = y
        return true
    }

    private fun offsetChildAsNeeded(parent: CoordinatorLayout?, child: View, dependency: View) {
        child.setTranslationY(
            (-dependency.getTranslationY() / this.headerOffset * getScrollRange(
                dependency
            ))
        )
    }


    override fun findFirstDependency(views: MutableList<View>): View? {
        var i = 0
        val z = views.size
        while (i < z) {
            val view = views[i]
            if (isDependOn(view)) {
                return view
            }
            i++
        }
        return null
    }

    /**
     * 计算content的最大向上偏移距离
     *
     * @param v
     * @return
     */
    override fun getScrollRange(v: View): Int {
        if (isDependOn(v)) {
            return max(0.0, (v.measuredHeight - this.finalHeight).toDouble()).toInt()
        } else {
            return super.getScrollRange(v)
        }
    }

    private val headerOffset: Int
        get() = if (mContext == null) 0 else mContext.getResources()
            .getDimensionPixelOffset(R.dimen.dp_m_90)

    private val finalHeight: Int
        get() = if (mContext == null) 0 else (mContext.getResources()
            .getDimensionPixelOffset(R.dimen.dp_45)
                + mContext.resources.getDimensionPixelOffset(R.dimen.dp_45))


    private fun isDependOn(dependency: View?): Boolean {
        return dependency != null && dependency.id == R.id.banner
    }
}
