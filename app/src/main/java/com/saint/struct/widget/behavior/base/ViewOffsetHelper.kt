package com.saint.struct.widget.behavior.base

import android.view.View
import androidx.core.view.ViewCompat

class ViewOffsetHelper(private val mView: View) {
    var layoutTop: Int = 0
        private set
    var layoutLeft: Int = 0
        private set
    var topAndBottomOffset: Int = 0
        private set
    var leftAndRightOffset: Int = 0
        private set

    fun onViewLayout() {
        // Now grab the intended top
        this.layoutTop = mView.top
        this.layoutLeft = mView.left

        // And offset it as needed
        updateOffsets()
    }

    private fun updateOffsets() {
        ViewCompat.offsetTopAndBottom(
            mView,
            this.topAndBottomOffset - (mView.top - this.layoutTop)
        )
        ViewCompat.offsetLeftAndRight(
            mView,
            this.leftAndRightOffset - (mView.left - this.layoutLeft)
        )
    }

    /**
     * Set the top and bottom offset for this [ViewOffsetHelper]'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    fun setTopAndBottomOffset(offset: Int): Boolean {
        if (this.topAndBottomOffset != offset) {
            this.topAndBottomOffset = offset
            updateOffsets()
            return true
        }
        return false
    }

    /**
     * Set the left and right offset for this [ViewOffsetHelper]'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    fun setLeftAndRightOffset(offset: Int): Boolean {
        if (this.leftAndRightOffset != offset) {
            this.leftAndRightOffset = offset
            updateOffsets()
            return true
        }
        return false
    }
}