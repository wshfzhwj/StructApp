package com.saint.struct.widget.behavior.xiami

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.saint.struct.R
import com.saint.struct.widget.behavior.base.HeaderScrollingViewBehavior
import kotlin.math.max

/**
 * 列表Behavior
 */
class XiamiCommentBehavior(mContext: Context, attrs: AttributeSet?) :
    HeaderScrollingViewBehavior(mContext, attrs) {

    private val headerOffset: Int =
        mContext.resources.getDimensionPixelOffset(R.dimen.header_offset)
    private val titleHeight: Int =
        mContext.resources.getDimensionPixelOffset(R.dimen.xiami_title_height)

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
        val commentScrollY =
            dependency.getTranslationY() / this.headerOffset * (dependency.getHeight() - this.titleHeight)
        val y = dependency.getHeight() - commentScrollY
        child.setY(y)
        return true
    }

    override fun findFirstDependency(views: MutableList<View>): View? {
        var i = 0
        val z = views.size
        while (i < z) {
            val view = views.get(i)
            if (isDependOn(view)) return view
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
            return max(0.0, (v.getMeasuredHeight() - this.titleHeight).toDouble()).toInt()
        } else {
            return super.getScrollRange(v)
        }
    }



//    private val titleHeight: Int
//        get() = if(mContext == null) 0  else mContext.resources.getDimensionPixelOffset(R.dimen.xiami_title_height)


    private fun isDependOn(dependency: View?): Boolean {
        return dependency != null && dependency.getId() == R.id.header
    }
}
