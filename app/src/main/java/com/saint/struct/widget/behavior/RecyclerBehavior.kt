package com.saint.struct.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.widget.behavior.xiami.XiamiTitleBehavior

class RecyclerBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {
    constructor() : this(null, null)
    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        // 传入的 child 与 dependency 都是 parent 的直接子 View，因此直接判断
        // 被依赖的是不是 TextView 就好
//        return dependency.id == R.id.banner
        return dependency is TextView
    }

    // OnPreDrawListener 传递 EVENT_PRE_DRAW，在绘制之前会回调一次
    // onDependentViewChanged()，因此跟随依赖变化即可
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        // 滑动距离是变化后的 dependency 的底部与还未变化的 child 的头部
        ViewCompat.offsetTopAndBottom(child, dependency.bottom - child.top)
        return false
    }
}