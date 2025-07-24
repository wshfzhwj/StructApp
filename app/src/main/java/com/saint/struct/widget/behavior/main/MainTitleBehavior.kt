package com.saint.struct.widget.behavior.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.saint.struct.R

/**
 * Title Behavior
 */
class MainTitleBehavior(context: Context?, attrs: AttributeSet?): CoordinatorLayout.Behavior<View>(context, attrs){

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
        val y = -(1 - dependency.getTranslationY() / this.headerOffset) * this.titleHeight
        child.setY(y)
        return true
    }

    private val headerOffset: Int
        get() = if(mContext == null) 0 else mContext!!.resources.getDimensionPixelOffset(R.dimen.header_offset)

    private val titleHeight: Int
        get() = if(mContext == null) 0 else mContext!!.resources.getDimensionPixelOffset(R.dimen.title_height)

    private fun isDependOn(dependency: View?): Boolean {
        return dependency != null && dependency.getId() == R.id.header
    }
}
