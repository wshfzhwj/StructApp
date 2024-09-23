package com.saint.struct.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import android.view.MotionEvent
import com.saint.struct.widget.TouchButton

class TouchButton : AppCompatButton {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> Log.d(TAG, "TouchButton dispatchTouchEvent ACTION_DOWN")
//            MotionEvent.ACTION_MOVE -> Log.d(TAG, "TouchButton dispatchTouchEvent ACTION_MOVE")
//            MotionEvent.ACTION_UP -> Log.d(TAG, "TouchButton dispatchTouchEvent ACTION_UP")
//            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "TouchButton dispatchTouchEvent ACTION_CANCEL")
//            else -> Log.d(TAG, "TouchButton dispatchTouchEvent ACTION = " + event.action)
//        }
//        return super.dispatchTouchEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> Log.d(TAG, "TouchButton onTouchEvent ACTION_DOWN")
//            MotionEvent.ACTION_MOVE -> Log.d(TAG, "TouchButton onTouchEvent ACTION_MOVE")
//            MotionEvent.ACTION_UP -> Log.d(TAG, "TouchButton onTouchEvent ACTION_UP")
//            MotionEvent.ACTION_CANCEL -> Log.d(TAG, "TouchButton onTouchEvent ACTION_CANCEL")
//            else -> Log.d(TAG, "TouchButton onTouchEvent ACTION = " + event.action)
//        }
//        return super.onTouchEvent(event)
//    }


    companion object {
        private const val TAG = "TouchTest TouchButton"
    }
}