package com.sf.struct.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class TouchButton extends AppCompatButton {
    private static final String TAG = "TouchButton";
    public TouchButton(@NonNull Context context) {
        super(context);
    }

    public TouchButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"TouchButton dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"TouchButton dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"TouchButton dispatchTouchEvent ACTION_UP" );
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"TouchButton dispatchTouchEvent ACTION_CANCEL" );
                break;
            default:
                Log.d(TAG,"TouchButton dispatchTouchEvent ACTION = "  + event.getAction());
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"TouchButton onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"TouchButton onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"TouchButton onTouchEvent ACTION_UP" );
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"TouchButton onTouchEvent ACTION_CANCEL" );
                break;
            default:
                Log.d(TAG,"TouchButton onTouchEvent ACTION = "  + event.getAction());
                break;
        }
        return  super.onTouchEvent(event);
    }
}
