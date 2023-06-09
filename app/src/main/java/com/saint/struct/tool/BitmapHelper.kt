package com.saint.struct.tool

import android.graphics.*

object BitmapHelper {
    fun toRoundCorner(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()

        //创建和原始照片一样大小的矩形
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true //抗锯齿
        canvas.drawARGB(0, 0, 0, 0)

        //paint.setAlpha(1); //这个透明度好像不起作用，可以在主类里再设置
        paint.color = color

        //画一个基于前面创建的矩形大小的圆角矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        //设置相交模式
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        //图片画到矩形
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    /**
     * 将图片的四角圆弧化
     *
     * @param bitmap      原图
     * @param roundPixels 弧度
     * @param half        （上/下/左/右）半部分圆角
     * @return
     */
    fun getRoundCornerImage(bitmap: Bitmap, roundPixels: Int, half: HalfType?): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) //创建一个和原始图片一样大小的位图
        val canvas = Canvas(roundConcerImage) //创建位图画布
        val paint = Paint() //创建画笔
        val rect = Rect(0, 0, width, height) //创建一个和原始图片一样大小的矩形
        val rectF = RectF(rect)
        paint.isAntiAlias = true // 抗锯齿
        canvas.drawRoundRect(rectF, roundPixels.toFloat(), roundPixels.toFloat(), paint) //画一个基于前面创建的矩形大小的圆角矩形
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) //设置相交模式
        canvas.drawBitmap(bitmap, null, rect, paint) //把图片画到矩形去
        return when (half) {
            HalfType.LEFT -> Bitmap.createBitmap(roundConcerImage, 0, 0, width - roundPixels, height)
            HalfType.RIGHT -> Bitmap.createBitmap(
                roundConcerImage,
                width - roundPixels,
                0,
                width - roundPixels,
                height
            )
            HalfType.TOP -> Bitmap.createBitmap(roundConcerImage, 0, 0, width, height - roundPixels)
            HalfType.BOTTOM -> Bitmap.createBitmap(
                roundConcerImage,
                0,
                height - roundPixels,
                width,
                height - roundPixels
            )
            HalfType.ALL -> roundConcerImage
            else -> roundConcerImage
        }
    }

    const val CORNER_TOP_LEFT = 1
    const val CORNER_TOP_RIGHT = 1 shl 1
    const val CORNER_BOTTOM_LEFT = 1 shl 2
    const val CORNER_BOTTOM_RIGHT = 1 shl 3
    const val CORNER_ALL = CORNER_TOP_LEFT or CORNER_TOP_RIGHT or CORNER_BOTTOM_LEFT or CORNER_BOTTOM_RIGHT

    /**
     * 把图片某固定角变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
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
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        //绘制成Bitmap对象
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    /**
     * 图片圆角规则 eg. TOP：上半部分
     */
    enum class HalfType {
        LEFT,  // 左上角 + 左下角
        RIGHT,  // 右上角 + 右下角
        TOP,  // 左上角 + 右上角
        BOTTOM,  // 左下角 + 右下角
        ALL // 四角
    }
}