package com.saint.struct.widget


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.saint.struct.R
import java.util.Locale
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt
import androidx.core.graphics.toColorInt

class BMIGaugeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()
    private val path = Path()

    var bmiValue: Float = 0f
        set(value) {
            field = value
            invalidate()
        }


    private val strokeWidth = 40f

    data class Segment(val startAngle: Float, val sweepAngle: Float)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val centerX = w / 2f
        val centerY = h / 2f
        val radius = (min(w, h) / 2f) - strokeWidth
        rect.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
    }

    private val totalAngle = 360f  // 总角度
    private val partitions = 5f    // 分成5份
    private val singleAngle = totalAngle / partitions  // 每份72度

    // 从左侧开始，顺时针方向
    private val segments = listOf(
        // 左侧两个区段（12点钟左侧）
        Segment(-234f, singleAngle),           // 蓝色区段 (-234° to -162°) // 偏瘦区域（蓝色）BMI < 18.5
        Segment(-162f, singleAngle),           // 绿色区段 (-162° to -90°) // 正常区域（绿色）18.5 ≤ BMI < 24.9
//        // 右侧两个区段（12点钟右侧）
        Segment(-90f, singleAngle),            // 黄色区段 (-90° to -18°)// 超重区域（黄色）25 ≤ BMI < 29.9
        Segment(-18f, singleAngle)             // 红色区段 (-18° to 54°)// 肥胖区域（红色）BMI ≥ 30
    )

    // 定义每个区段的渐变色  由浅到深渐变
    private val gradientColors = listOf(
        // 蓝色渐变
        intArrayOf(
            "#7CB9E8".toColorInt(),
            "#B5D8FF".toColorInt()
        ),
        // 绿色渐变
        intArrayOf(
            "#C8FFB5".toColorInt(),
            "#90EE90".toColorInt()

        ),
        // 黄色渐变
        intArrayOf(
            "#FFF3B5".toColorInt(),
            "#FFD700".toColorInt()
        ),
        // 红色渐变
        intArrayOf(
            "#FFB5B5".toColorInt(),
            "#FF6B6B".toColorInt()
        )
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        drawCirque(canvas, centerX, centerY)
        drawBMI(canvas, centerX, centerY)
        drawUnit(canvas, centerX, centerY)
        drawPoint(canvas, centerX, centerY)
    }

    private fun drawCirque(canvas: Canvas, centerX: Float, centerY: Float) {
        // 反转绘制顺序：红色 -> 黄色 -> 绿色 -> 蓝色
        segments.indices.reversed().forEach { index ->
            val segment = segments[index]

            // 先绘制白色描边
            paint.apply {
                style = Paint.Style.STROKE
                strokeWidth = this@BMIGaugeView.strokeWidth + 4f
                strokeCap = Paint.Cap.ROUND
                shader = null
                color = Color.YELLOW
            }

            canvas.drawArc(
                rect,
                segment.startAngle,
                segment.sweepAngle,
                false,
                paint
            )

//            // 再绘制渐变弧
            paint.apply {
                strokeWidth = this@BMIGaugeView.strokeWidth
                strokeCap = Paint.Cap.ROUND
            }

            // 创建渐变
            val startX =
                centerX + rect.width() / 2f * cos(Math.toRadians(segment.startAngle.toDouble())).toFloat()
            val startY =
                centerY + rect.width() / 2f * sin(Math.toRadians(segment.startAngle.toDouble())).toFloat()
            val endX =
                centerX + rect.width() / 2f * cos(Math.toRadians((segment.startAngle + segment.sweepAngle).toDouble())).toFloat()
            val endY =
                centerY + rect.width() / 2f * sin(Math.toRadians((segment.startAngle + segment.sweepAngle).toDouble())).toFloat()

            val linearGradient = LinearGradient(
                startX,
                startY,
                endX,
                endY,
                gradientColors[index],
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP
            )

            paint.shader = linearGradient

            canvas.drawArc(
                rect,
                segment.startAngle,
                segment.sweepAngle,
                false,
                paint
            )

            paint.shader = null
        }
    }


    private fun drawBMI(canvas: Canvas, x: Float, y: Float) {
        // 绘制BMI值
        paint.apply {
            style = Paint.Style.FILL
            textSize = 180f
            isFakeBoldText = false
            textAlign = Paint.Align.CENTER
            color = Color.parseColor("#303437")
        }
        canvas.drawText(String.format(Locale.US, "%.1f", bmiValue), x, y + 50, paint)
    }

    private fun drawUnit(canvas: Canvas, centerX: Float, centerY: Float) {
        // 绘制单位
        paint.apply {
            style = Paint.Style.FILL
            textSize = 45f
            isFakeBoldText = false
            textAlign = Paint.Align.CENTER
            color = Color.parseColor("#303437")
            typeface = Typeface.DEFAULT
            setTypeface(typeface)
        }
        canvas.drawText(context.getString(R.string.bmi), centerX, centerY * (1.8f), paint)
    }

    private fun drawPoint(canvas: Canvas, centerX: Float, centerY: Float) {
        // 绘制指针
        val angle = getBmiAngle(bmiValue)
        drawPointer(canvas, centerX, centerY, angle)
    }


    private fun getBmiAngle(bmi: Float): Float {
        return when {
            bmi < 18.5f -> {
                // 蓝色区域（偏瘦）
                val progress = (bmi - 15f) / (18.5f - 15f)
                segments[0].startAngle + segments[0].sweepAngle * progress
            }

            bmi < 24.9f -> {
                // 绿色区域（正常）
                val progress = (bmi - 18.5f) / (24.9f - 18.5f)
                segments[1].startAngle + segments[1].sweepAngle * progress
            }

            bmi < 29.9f -> {
                // 黄色区域（超重）
                val progress = (bmi - 24.9f) / (29.9f - 24.9f)
                segments[2].startAngle + segments[2].sweepAngle * progress
            }

            else -> {
                // 红色区域（肥胖）
                val progress = (bmi - 29.9f) / (35f - 29.9f)
                segments[3].startAngle + segments[3].sweepAngle * progress
            }
        }.coerceIn(-234f, 54f)
    }

    private fun drawPointer(canvas: Canvas, centerX: Float, centerY: Float, angle: Float) {
        val radius = rect.width() / 2f * 0.85f
        val triangleHeight = 40f  // 三角形高度

        // 计算等腰三角形的底边长度（基于 120° 顶角）
        // 等腰三角形顶角 120°，则底角各 30°
        // 如果高度为 h，则底边长度为 2h/√3
        val triangleBase = (2 * triangleHeight / sqrt(4f))

        // 圆角半径
        val cornerRadius = 12f

        // 计算三角形在圆弧上的位置
        val startX =
            centerX + (radius - strokeWidth) * cos(Math.toRadians(angle.toDouble())).toFloat()
        val startY =
            centerY + (radius - strokeWidth) * sin(Math.toRadians(angle.toDouble())).toFloat()

        // 计算三角形的三个顶点
        val tipX =
            centerX + (radius + triangleHeight) * cos(Math.toRadians(angle.toDouble())).toFloat()
        val tipY =
            centerY + (radius + triangleHeight) * sin(Math.toRadians(angle.toDouble())).toFloat()

        // 计算底边两端点的偏移量
        val dx = triangleBase / 2 * sin(Math.toRadians(angle.toDouble())).toFloat() * 3
        val dy = -triangleBase / 2 * cos(Math.toRadians(angle.toDouble())).toFloat() * 3

        // 使用 Path 绘制圆角三角形
        path.reset()

        // 移动到起始点
        path.moveTo(startX + dx, startY + dy)

        // 使用二次贝塞尔曲线绘制圆角
        // 底边到右边
        val rightX = tipX
        val rightY = tipY
        path.quadTo(
            startX + dx + cornerRadius * sin(Math.toRadians((angle - 30).toDouble())).toFloat(),
            startY + dy - cornerRadius * cos(Math.toRadians((angle - 30).toDouble())).toFloat(),
            rightX, rightY
        )

        // 右边到左边
        path.quadTo(
            tipX + cornerRadius * cos(Math.toRadians(angle.toDouble())).toFloat(),
            tipY + cornerRadius * sin(Math.toRadians(angle.toDouble())).toFloat(),
            startX - dx, startY - dy
        )

        // 左边到底边
        path.quadTo(
            startX - dx - cornerRadius * sin(Math.toRadians((angle + 30).toDouble())).toFloat(),
            startY - dy + cornerRadius * cos(Math.toRadians((angle + 30).toDouble())).toFloat(),
            startX + dx, startY + dy
        )

        // 绘制黑色填充
        paint.apply {
            style = Paint.Style.FILL
            color = Color.BLACK
            setShadowLayer(8f, 0f, 8f, Color.parseColor("#20000000"))
        }
        canvas.drawPath(path, paint)
        paint.clearShadowLayer()

        // 绘制白色边框
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            color = Color.WHITE
        }
        canvas.drawPath(path, paint)
    }
}