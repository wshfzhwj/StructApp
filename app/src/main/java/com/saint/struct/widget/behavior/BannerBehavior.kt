package com.saint.struct.widget.behavior

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.widget.behavior.xiami.XiamiTitleBehavior

class BannerBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var maxHeight = 200f
    private var minHeight = 100f

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.recyclerView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val params = child.layoutParams as CoordinatorLayout.LayoutParams
        val margin = params.topMargin
        val scrollY = dependency.scrollY

        // 动态计算高度变化
        val newHeight = (maxHeight - scrollY.coerceAtLeast(0)).coerceAtLeast(minHeight)
        child.layoutParams.height = newHeight.toInt()
        child.requestLayout()
        return true
    }

}