package com.saint.struct.tool

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF

object ImageUtils {
    private const val CORNER_TOP_LEFT = 1
    private  const val CORNER_TOP_RIGHT = 1 shl 1
    private const val CORNER_BOTTOM_LEFT = 1 shl 2
    private const val CORNER_BOTTOM_RIGHT = 1 shl 3
    private const val CORNER_ALL = CORNER_TOP_LEFT or CORNER_TOP_RIGHT or CORNER_BOTTOM_LEFT or CORNER_BOTTOM_RIGHT

    /**
     * 把图片某固定角变成圆角
     *
     * @param bitmap  需要修改的图片
     * @param pixels  圆角的弧度
     * @param corners 需要显示圆弧的位置
     * @return 圆角图片
     */
    fun toRoundCorner(bitmap: Bitmap, pixels: Int, corners: Int): Bitmap {
        //创建一个等大的画布
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        //获取一个跟图片相同大小的矩形
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        //生成包含坐标的矩形对象
        val rectF = RectF(rect)
        //圆角的半径
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true //去锯齿
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        //绘制圆角矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        //异或将需要变为圆角的位置的二进制变为0
        val notRoundedCorners = corners xor CORNER_ALL
        //哪个角不是圆角我再把你用矩形画出来
        if (notRoundedCorners and CORNER_TOP_LEFT != 0) {
            canvas.drawRect(0f, 0f, roundPx, roundPx, paint)
        }
        if (notRoundedCorners and CORNER_TOP_RIGHT != 0) {
            canvas.drawRect(rectF.right - roundPx, 0f, rectF.right, roundPx, paint)
        }
        if (notRoundedCorners and CORNER_BOTTOM_LEFT != 0) {
            canvas.drawRect(0f, rectF.bottom - roundPx, roundPx, rectF.bottom, paint)
        }
        if (notRoundedCorners and CORNER_BOTTOM_RIGHT != 0) {
            canvas.drawRect(rectF.right - roundPx, rectF.bottom - roundPx, rectF.right, rectF.bottom, paint)
        }
        //通过SRC_IN的模式取源图片和圆角矩形重叠部分
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        //绘制成Bitmap对象
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun decodeSampledBitmapFromResource(res: Resources?, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // 源图片的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }
}