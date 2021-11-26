package com.sf.struct.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class TouchButton extends androidx.appcompat.widget.AppCompatButton {
    private static final String TAG = TouchButton.class.getSimpleName();
    public TouchButton(Context context) {
        super(context);
    }

    public TouchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"event = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
