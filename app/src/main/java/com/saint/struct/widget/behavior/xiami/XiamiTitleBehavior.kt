package com.saint.struct.widget.behavior.xiami

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.saint.struct.R
import com.saint.struct.widget.behavior.xiami.XiamiCommentBehavior

/**
 * Title Behavior
 */
class XiamiTitleBehavior : CoordinatorLayout.Behavior<View>{
    private var mContext: Context? = null

    constructor() : this(null, null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
    }
    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        return super.onLayoutChild(parent, child, layoutDirection)
    }

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
        get() = if(mContext == null) 0 else mContext!!.getResources().getDimensionPixelOffset(R.dimen.dp_m_90)

    private val titleHeight: Int
        get() = if(mContext == null) 0 else mContext!!.getResources().getDimensionPixelOffset(R.dimen.dp_60)

    private fun isDependOn(dependency: View?): Boolean {
        return dependency != null && dependency.getId() == R.id.header
    }
}
