package com.saint.struct.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.tool.TAG
import com.saint.struct.widget.behavior.RecyclerViewBehavior

class SampleTitleBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private var deltaY = 0f

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        return dependency is RecyclerView
    }

    //被观察的view发生改变时回调
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (deltaY == 0f) {
            deltaY = dependency.getY() - child.getHeight()
        }

        var dy = dependency.getY() - child.getHeight()
        dy = if (dy < 0) 0f else dy

        val y = -(dy / deltaY) * child.getHeight()
        child.setTranslationY(y)

        //        float alpha = 1 - (dy / deltaY);
//        child.setAlpha(alpha);
        return true
    }
}
