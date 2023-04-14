package com.saint.struct.tool

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.ViewGroup.MarginLayoutParams

object StatusBarUtils {
    fun setDeepStatusBar(isChange: Boolean, mActivity: Activity): Boolean {
        if (!isChange) {
            return false
        }
        // 透明状态栏
        val window = mActivity.window
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN /*| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        //设置状态栏文字颜色及图标为深色
        mActivity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        return true
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    //沉浸式通知栏
    fun setMarginTopStatusBarHeight(context: Context, view: View) {
        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        val layoutParams = view.layoutParams as MarginLayoutParams
        layoutParams.topMargin = getStatusBarHeight(context)
        view.layoutParams = layoutParams
    }
}