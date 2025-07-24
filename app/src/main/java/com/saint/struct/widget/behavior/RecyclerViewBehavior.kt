package com.saint.struct.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBehavior(context: Context?, attrs: AttributeSet?): CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {

    constructor():this(null,null)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        return dependency is TextView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        //计算列表y坐标，最小为0
        var y = dependency.getHeight() + dependency.getTranslationY()
        if (y < 0) {
            y = 0f
        }
        child.setY(y)
        return true
    }
}
