package com.saint.struct.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View

object Util {
    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions
     * @return
     */
    fun findMax(lastVisiblePositions: IntArray): Int {
        var max = lastVisiblePositions[0]
        for (value in lastVisiblePositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    fun inflate(context: Context, layoutId: Int): View? {
        if (layoutId <= 0) {
            return null
        }
        return LayoutInflater.from(context).inflate(layoutId, null)
    }
}
