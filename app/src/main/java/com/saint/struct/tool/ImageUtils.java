package com.saint.struct.tool;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtils {
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;


    /**
     * 把图片某固定角变成圆角
     *
     * @param bitmap  需要修改的图片
     * @param pixels  圆角的弧度
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

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}