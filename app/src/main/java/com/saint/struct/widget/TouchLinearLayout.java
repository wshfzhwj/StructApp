package com.saint.struct.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TouchLinearLayout extends LinearLayout {
    private static final String TAG = "TouchTest TouchLinearLayout";
    public TouchLinearLayout(Context context) {
        super(context);
    }

    public TouchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"TouchLinearLayout onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"TouchLinearLayout onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"TouchLinearLayout onInterceptTouchEvent ACTION_UP" );
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"TouchLinearLayout onInterceptTouchEvent ACTION_CANCEL" );
                break;
            default:
                Log.d(TAG,"TouchLinearLayout onInterceptTouchEvent ACTION = "  + event.getAction());
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"TouchLinearLayout dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"TouchLinearLayout dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"TouchLinearLayout dispatchTouchEvent ACTION_UP" );
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"TouchLinearLayout dispatchTouchEvent ACTION_CANCEL" );
                break;
            default:
                Log.d(TAG,"TouchLinearLayout dispatchTouchEvent ACTION = "  + event.getAction());
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"TouchLinearLayout onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"TouchLinearLayout onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"TouchLinearLayout onTouchEvent ACTION_UP" );
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"TouchLinearLayout onTouchEvent ACTION_CANCEL" );
                break;
            default:
                Log.d(TAG,"TouchLinearLayout onTouchEvent ACTION = "  + event.getAction());
                break;
        }
        return  super.onTouchEvent(event);
    }
}
