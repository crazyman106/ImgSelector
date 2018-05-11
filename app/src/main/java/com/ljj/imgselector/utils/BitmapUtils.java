package com.ljj.imgselector.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lijunjie on 2018/4/27 0027.
 * @description * 图片加载及转化工具 ----- 延伸：一个Bitmap到底占用多大内存？系统给每个应用程序分配多大内存？ Bitmap占用的内存为：像素总数
 * 每个像素占用的内存。在Android中， Bitmap有四种像素类型：ARGB_8888、ARGB_4444、ARGB_565、ALPHA_8， 他们每个像素占用的字节数分别为4、2、2、1。
 * 因此，一个2000*1000的ARGB_8888类型的Bitmap占用的内存为2000*1000*4=8000000B=8MB。
 */

public class BitmapUtils {


    /*******图片加载与保存*******/
    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    //保存图片到本地路径
    public boolean saveBitmap(Bitmap bitmap, String fileName, String path) {
        File file = new File(path);
        FileOutputStream fos = null;
        if (!file.exists()) {
            file.mkdir();
        }
        File imageFile = new File(file, fileName);
        try {
            imageFile.createNewFile();
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
        }
        return true;
    }

    // 从view得到bitmap
    public Bitmap getViewBitmap(View view) {
        Bitmap bitmap = null;
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width != 0 && height != 0) {
                bitmap = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
            }
        } catch (Exception e) {
            bitmap = null;
        }
        return bitmap;
    }


    /**
     * @param @param  url 本地路径
     * @param @return
     * @return Bitmap
     * @throws
     * @Title: getLoacalBitmap
     * @Description: 加载本地图片
     */
    public Bitmap getLoacalBitmap(String url) {
        if (url != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(url);
                return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fis = null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * 通过URL地址获取Bitmap对象
     *
     * @param @param  url
     * @param @return
     * @param @throws Exception
     * @return Bitmap
     * @throws
     * @Title: getBitMapByUrl
     */
    public Bitmap getBitMapByUrl(final String url) {
        URL fileUrl = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
        return bitmap;
    }

/*******获取图片信息*******/
    /**
     * @brief 从文件载入只获边框的，从返回的Options.outWidth和Options.outHight里取出即可
     * @see android.graphics.BitmapFactory.Options#inJustDecodeBounds
     */
    public BitmapFactory.Options loadJustDecodeBounds(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        // TODO loadFromFile(path, opts);
        return opts;
    }

    /**
     * @param path 图片路径
     * @return 角度
     * @brief 读取图片方向信息
     */
    public int readPhotoDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*******图片转换*******/
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ?
                        Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    //Stream转换成Byte
    static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {

        }
        return os.toByteArray();
    }

    /**
     * Bitmap 格式转换
     *
     * @param src     需要重新编码的Bitmap
     * @param format  编码后的格式（目前只支持png和jpeg这两种格式）
     * @param quality 重新生成后的bitmap的质量
     * @return 返回重新生成后的bitmap
     */
    private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

/*******图片缩放剪裁和形变*******/

    /**
     * 放大缩小图片，不保证宽高比
     *
     * @param @param bitmap
     * @param @param w
     * @param @param h
     * @return Bitmap
     * @throws
     * @Title: zoomBitmap
     */
    public Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        bitmap = null;
        return newbmp;
    }

    /**
     * @param src       源Bitmap
     * @param dstWidth  目标宽度
     * @param dstHeight 目标高度
     * @param isRecycle 是否回收原图像
     * @return Bitmap
     * @brief 缩放Bitmap
     */
    public Bitmap scaleBitmap(Bitmap src, int dstWidth, int dstHeight, boolean isRecycle) {
        if (src.getWidth() == dstWidth && src.getHeight() == dstHeight) {
            return src;
        }
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (isRecycle && dst != src) {
            src.recycle();
        }
        return dst;
    }

    //放大缩小图片，生成缩略图
    public Bitmap extractThumbnail(Bitmap src, int width, int height) {
        return ThumbnailUtils.extractThumbnail(src, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

    /**
     * @param src       源Bitmap
     * @param x         开始x坐标
     * @param y         开始y坐标
     * @param width     截取宽度
     * @param height    截取高度
     * @param isRecycle 是否回收原图像
     * @return Bitmap
     * @brief 裁剪Bitmap
     */
    public Bitmap cropBitmap(Bitmap src, int x, int y, int width, int height, boolean isRecycle) {
        if (x == 0 && y == 0 && width == src.getWidth() && height == src.getHeight()) {
            return src;
        }
        Bitmap dst = Bitmap.createBitmap(src, x, y, width, height);
        if (isRecycle && dst != src) {
            src.recycle();
        }
        return dst;
    }

    /***
     * 图片分割
     *
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param imgBit
     * ：图片
     * @param x
     * ：X轴起点坐标
     * @param y
     * ：Y轴起点坐标
     * @param w
     * ：单一图片的宽度
     * @param h
     * ：单一图片的高度
     * @param line
     * ：第几列
     * @param row
     * ：第几行
     */

    public final void cuteImage(Canvas g, Paint paint, Bitmap imgBit, int x,
                                int y, int w, int h, int line, int row) {
        g.clipRect(x, y, x + w, h + y);
        g.drawBitmap(imgBit, x - line * w, y - row * h, paint);
        g.restore();
    }

    /**
     * @param src       源Bitmap
     * @param degree    旋转角度
     * @param isRecycle 是否回收原图像
     * @return Bitmap
     * @brief 旋转Bitmap，顺时针
     */
    public Bitmap rotateBitmap(Bitmap src, int degree, boolean isRecycle) {
        if (degree % 360 == 0) {
            return src;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        if (isRecycle && dst != src) {
            src.recycle();
        }
        return dst;
    }

/*******图片效果处理*******/

    /**
     * 图片透明度处理
     *
     * @param sourceImg 原始图片
     * @param number    透明度
     * @return
     */
    public static Bitmap setAlpha(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight()); // 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00ffffff);// [/i][i]修改最高2[/i][i]位的值
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);
        return sourceImg;
    }

    public class ImgService {
        //亮
        public final float[] LIGHT_ARR = new float[]{
                1, 0, 0, 0, 100,
                0, 1, 0, 0, 100,
                0, 0, 1, 0, 100,
                0, 0, 0, 1, 0
        };
        //暗
        public final float[] DARK_ARR = new float[]{
                0.2f, 0, 0, 0, 50.8f,
                0, 0.2f, 0, 0, 50.8f,
                0, 0, 0.2f, 0, 50.8f,
                0, 0, 0, 1f, 0
        };
        //高对比
        public final float[] GDB_ARR = new float[]{
                5, 0, 0, 0, -250,
                0, 5, 0, 0, -250,
                0, 0, 5, 0, -250,
                0, 0, 0, 1, 0
        };
        //高对比
        public final float[] DDB_ARR = new float[]{
                0.2f, 0, 0, 0, 50,
                0, 0.2f, 0, 0, 50,
                0, 0, 0.2f, 0, 50,
                0, 0, 0, 1, 0
        };
        //高饱和
        public final float[] GBH_ARR = new float[]{
                3f, -1.8f, -0.25f, 0, 50,
                -0.9f, 2.1f, -0.25f, 0, 50,
                -0.9f, -1.8f, 3.8f, 0, 50,
                0, 0, 0, 1, 0
        };
        //低饱和
        public final float[] DBH_ARR = new float[]{
                0.3f, 0.6f, 0.08f, 0, 0,
                0.3f, 0.6f, 0.08f, 0, 0,
                0.3f, 0.6f, 0.08f, 0, 0,
                0, 0, 0, 1, 0
        };
        //COPY
        public final float[] COPY_ARR = new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
    }

    /**
     * 为图片加滤镜特效.array参数为ImgService定义的几个滤镜矩阵.如ImgService.LIGHT_ARR
     *
     * @param bmpOriginal
     * @param array
     * @return
     */
    public Bitmap toGrayscale(Bitmap bmpOriginal, float[] array) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(array);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        bmpOriginal.recycle();
        bmpOriginal = null;
        return bmpGrayscale;
    }


    // Bitmap加水印
    public Bitmap addWatermark(Bitmap src, Bitmap watermark) {
        if (src == null || watermark == null) {
            return src;
        }

        int sWid = src.getWidth();
        int sHei = src.getHeight();
        int wWid = watermark.getWidth();
        int wHei = watermark.getHeight();
        if (sWid == 0 || sHei == 0) {
            return null;
        }

        if (sWid < wWid || sHei < wHei) {
            return src;
        }

        Bitmap bitmap = Bitmap.createBitmap(sWid, sHei, Bitmap.Config.ARGB_8888);//Config可修改,改变内存占用
        try {
            Canvas cv = new Canvas(bitmap);
            cv.drawBitmap(src, 0, 0, null);
            cv.drawBitmap(watermark, sWid - wWid - 5, sHei - wHei - 5, null);
            cv.save(Canvas.ALL_SAVE_FLAG);
            cv.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        } finally {
            src.recycle();
            src = null;
            watermark.recycle();
            watermark = null;
        }
        return bitmap;
    }

    /**
     * 获得圆角图片
     *
     * @param bitmap
     * @return
     * @Description:
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, 10, 10, paint);// 圆角平滑度为10
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /***
     * 绘制带有边框的文字
     *
     * @param strMsg
     * ：绘制内容
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param setx
     * ：：X轴起始坐标
     * @param sety
     * ：Y轴的起始坐标
     * @param fg
     * ：前景色
     * @param bg
     * ：背景色
     */
    public void drawText(String strMsg, Canvas g, Paint paint, int setx,
                         int sety, int fg, int bg) {
        paint.setColor(bg);
        g.drawText(strMsg, setx + 1, sety, paint);
        g.drawText(strMsg, setx, sety - 1, paint);
        g.drawText(strMsg, setx, sety + 1, paint);
        g.drawText(strMsg, setx - 1, sety, paint);
        paint.setColor(fg);
        g.drawText(strMsg, setx, sety, paint);
        g.restore();

    }


    //获得带倒影的图片方法
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap,
                deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public Bitmap convertGreyImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    //压缩图片大小
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {   //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


}
