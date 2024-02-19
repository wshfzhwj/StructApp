package com.saint.struct.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircleRect : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.RED
        paint.setPathEffect(DashPathEffect(floatArrayOf(4f, 4f), 0f))
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val cornerRadius = 80f
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
    }
}
