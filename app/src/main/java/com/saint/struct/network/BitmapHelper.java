package com.saint.struct.network;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapHelper {
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        //创建和原始照片一样大小的矩形
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);//抗锯齿
        canvas.drawARGB(0, 0, 0, 0);

        //paint.setAlpha(1); //这个透明度好像不起作用，可以在主类里再设置
        paint.setColor(color);

        //画一个基于前面创建的矩形大小的圆角矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        //设置相交模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //图片画到矩形
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    /**
     * 将图片的四角圆弧化
     *
     * @param bitmap      原图
     * @param roundPixels 弧度
     * @param half        （上/下/左/右）半部分圆角
     * @return
     */
    public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels, HalfType half) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建一个和原始图片一样大小的位图
        Canvas canvas = new Canvas(roundConcerImage);//创建位图画布
        Paint paint = new Paint();//创建画笔

        Rect rect = new Rect(0, 0, width, height);//创建一个和原始图片一样大小的矩形
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);// 抗锯齿

        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);//画一个基于前面创建的矩形大小的圆角矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置相交模式
        canvas.drawBitmap(bitmap, null, rect, paint);//把图片画到矩形去



        switch (half) {
            case LEFT:
                return Bitmap.createBitmap(roundConcerImage, 0, 0, width - roundPixels, height);
            case RIGHT:
                return Bitmap.createBitmap(roundConcerImage, width - roundPixels, 0, width - roundPixels, height);
            case TOP: // 上半部分圆角化 “- roundPixels”实际上为了保证底部没有圆角，采用截掉一部分的方式，就是截掉和弧度一样大小的长度
                return Bitmap.createBitmap(roundConcerImage, 0, 0, width, height - roundPixels);
            case BOTTOM:
                return Bitmap.createBitmap(roundConcerImage, 0, height - roundPixels, width, height - roundPixels);
            case ALL:
                return roundConcerImage;
            default:
                return roundConcerImage;
        }
    }
    /**
     * 图片圆角规则 eg. TOP：上半部分
     */
    public enum HalfType {
        LEFT, // 左上角 + 左下角
        RIGHT, // 右上角 + 右下角
        TOP, // 左上角 + 右上角
        BOTTOM, // 左下角 + 右下角
        ALL // 四角
    }
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;


    /**
     * 把图片某固定角变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @param corners 需要显示圆弧的位置
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels, int corners) {
        //创建一个等大的画布
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        //获取一个跟图片相同大小的矩形
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        //生成包含坐标的矩形对象
        final RectF rectF = new RectF(rect);
        //圆角的半径
        final float roundPx = pixels;

        paint.setAntiAlias(true); //去锯齿
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        //绘制圆角矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        //异或将需要变为圆角的位置的二进制变为0
        int notRoundedCorners = corners ^ CORNER_ALL;

        //哪个角不是圆角我再把你用矩形画出来
        if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
            canvas.drawRect(0, 0, roundPx, roundPx, paint);
        }
        if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
            canvas.drawRect(rectF.right - roundPx, 0, rectF.right, roundPx, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
            canvas.drawRect(0, rectF.bottom - roundPx, roundPx, rectF.bottom, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
            canvas.drawRect(rectF.right - roundPx, rectF.bottom - roundPx, rectF.right, rectF.bottom, paint);
        }
        //通过SRC_IN的模式取源图片和圆角矩形重叠部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制成Bitmap对象
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
