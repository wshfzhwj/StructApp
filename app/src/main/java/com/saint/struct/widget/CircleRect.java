package com.saint.struct.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleRect extends View {
    public CircleRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));

        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        float cornerRadius = 80;
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);


    }
}
