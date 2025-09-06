package com.saint.struct.widget.behavior.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.saint.struct.R
import com.saint.struct.widget.behavior.base.ViewOffsetBehavior

/**
 * Tab Behavior
 */
class MainTabBehavior(mContext: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(mContext, attrs){
    private var mContext: Context? = null

    constructor():this(null,null)

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
        val tabScrollY =
            dependency.translationY / this.headerOffset * (dependency.height - this.titleHeight)
        val y = dependency.height - tabScrollY
        child.y = y
        return true
    }

    private val headerOffset: Int
        get() = if(mContext == null) 0 else mContext!!.resources.getDimensionPixelOffset(R.dimen.dp_m_90)

    private val titleHeight: Int
        get() = if(mContext == null) 0 else mContext!!.resources.getDimensionPixelOffset(R.dimen.dp_45)


    private fun isDependOn(dependency: View?): Boolean {
        return dependency != null && dependency.id == R.id.header
    }
}
