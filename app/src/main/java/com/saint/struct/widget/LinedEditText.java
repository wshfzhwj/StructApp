package com.saint.struct.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


/**
 * Created by wangsf on 2017/2/6.
 * @author wangsf
 */

public class LinedEditText extends AppCompatEditText {
    private final Rect mRect;
    private final Paint mPaint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFFe5e5e5);
    }

    /**
     * This is called to draw the LinedEditText object
     *
     * @param canvas The canvas on which the background is drawn.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        int height = canvas.getHeight();
        int curHeight = 0;
        Rect r = mRect;
        Paint paint = mPaint;
        int baseline = getLineBounds(0, r);
        for (curHeight = baseline + 1; curHeight < height; curHeight += getLineHeight()) {
            int margin = 30;
            canvas.drawLine(r.left, curHeight + margin, r.right, curHeight + margin, paint);
        }
        super.onDraw(canvas);
    }

}