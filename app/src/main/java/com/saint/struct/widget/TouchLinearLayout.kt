package com.saint.struct.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent
import com.saint.struct.widget.TouchLinearLayout
class TouchLinearLayout  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr : Int
) : LinearLayout (context, attrs, defStyleAttr){
//    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> Log.d(TAG, "TouchLinearLayout onInterceptTouchEvent ACTION_DOWN")
//            MotionEvent.ACTION_MOVE -> Log.d(TAG, "TouchLinearLayout onInterceptTouchEvent ACTION_MOVE")
//            MotionEvent.ACTION_UP -> Log.d(TAG, "TouchLinearLayout onInterceptTouchEvent ACTION_UP")
//            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "TouchLinearLayout onInterceptTouchEvent ACTION_CANCEL")
//            else -> Log.d(TAG, "TouchLinearLayout onInterceptTouchEvent ACTION = " + event.action)
//        }
//        return super.onInterceptTouchEvent(event)
//    }
//
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> Log.d(TAG, "TouchLinearLayout dispatchTouchEvent ACTION_DOWN")
//            MotionEvent.ACTION_MOVE -> Log.d(TAG, "TouchLinearLayout dispatchTouchEvent ACTION_MOVE")
//            MotionEvent.ACTION_UP -> Log.d(TAG, "TouchLinearLayout dispatchTouchEvent ACTION_UP")
//            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "TouchLinearLayout dispatchTouchEvent ACTION_CANCEL")
//            else -> Log.d(TAG, "TouchLinearLayout dispatchTouchEvent ACTION = " + event.action)
//        }
//        return super.dispatchTouchEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> Log.d(TAG, "TouchLinearLayout onTouchEvent ACTION_DOWN")
//            MotionEvent.ACTION_MOVE -> Log.d(TAG, "TouchLinearLayout onTouchEvent ACTION_MOVE")
//            MotionEvent.ACTION_UP -> Log.d(TAG, "TouchLinearLayout onTouchEvent ACTION_UP")
//            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "TouchLinearLayout onTouchEvent ACTION_CANCEL")
//            else -> Log.d(TAG, "TouchLinearLayout onTouchEvent ACTION = " + event.action)
//        }
//        return super.onTouchEvent(event)
//    }

    companion object {
        private const val TAG = "TouchTest TouchLinearLayout"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for(i in 0 until childCount){
            var childView  = getChildAt(i)
            var lp = childView.layoutParams
            var selfWidth = MeasureSpec.getMode(widthMeasureSpec)
            when(lp.width){
                android.view.ViewGroup.LayoutParams.MATCH_PARENT ->  {
                    if(selfWidth == MeasureSpec.EXACTLY || selfWidth == MeasureSpec.AT_MOST){
                    }
                }
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT->{

                }
            }

        }
    }
}