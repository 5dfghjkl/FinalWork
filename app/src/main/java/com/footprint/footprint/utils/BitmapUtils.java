package com.footprint.footprint.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类名：BitmapUtils 说明: 图片处理的工具类
 */
@SuppressLint("NewApi")
public class BitmapUtils {

    /**
     * 添加文字到图片，类似水印文字。
     *
     * @param gContext
     * @param gResId
     * @param gText
     * @return
     */
    public static Bitmap drawTextToBitmap(Context gContext, int gResId, String gText, String strColor) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.RED);
        // text size in pixels
        paint.setTextSize((int) (3 * scale * 5));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        //      int x = (bitmap.getWidth() - bounds.width()) / 2;
        //      int y = (bitmap.getHeight() + bounds.height()) / 2;
        //draw  text  to the bottom
        int x = (bitmap.getWidth() - bounds.width()) / 10 * 9;
        int y = (bitmap.getHeight() + bounds.height()) / 10 * 9;
        canvas.drawText(gText, x, y - 20, paint);

        return bitmap;
    }

    /**
     * 功能:根据图片的宽度来缩小图片
     *
     * @param defaultBitmap 原图片
     * @param targetWidth   目标宽度
     * @return
     */
    public static Bitmap compressByWidth(Bitmap defaultBitmap, int targetWidth) {
        int rawWidth = defaultBitmap.getWidth();
        int rawHeight = defaultBitmap.getHeight();
        float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
        float scaleWidth = targetWidth / (float) rawWidth;
        float scaleHeight = targetHeight / (float) rawHeight;
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(scaleHeight, scaleWidth);
        return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,
                localMatrix, true);
    }

    /**
     * 功能: 根
     * 据图片大小来压缩图片
     *
     * @param bm     原图片
     * @param length 压缩后图片大小 kb
     * @return 压缩后的图片
     */
    public static Bitmap compressImageByLength(Bitmap bm, long length) {

        ByteArrayOutputStream ots = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, ots);
        int options = 100;
        while (ots.toByteArray().length / 1024 > length) {
            ots.reset();
            bm.compress(CompressFormat.JPEG, options, ots);
            options -= 10;
        }
        ByteArrayInputStream its = new ByteArrayInputStream(ots.toByteArray());
        Bitmap mBitmap = BitmapFactory.decodeStream(its, null, null);
        return mBitmap;
    }

    /**
     * 功能:根据图片路径来压缩图片
     *
     * @param path      图片保存路径
     * @param scaleSize 压缩的倍数
     * @return 压缩后的图片
     */
    public static Bitmap compressImageByPathAndScaleSize(String path,
                                                         int scaleSize) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeFile(path, opts);
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scaleSize;
        mBitmap = BitmapFactory.decodeFile(path, opts);
        return mBitmap;
    }

    /**
     * 功能:将图片保存到sd卡
     *
     * @param bitmap    图片
     * @param path      图片文件路径，含文件名
     * @param scaleSize 压缩的百分比 0~100
     */
    public static void saveBitmapToSD(Bitmap bitmap, String path, int scaleSize) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            CompressFormat t = getCompressFormat(path);
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(t, scaleSize, out);
            out.flush();
            out.close();
        } catch (Exception e) {

        }
    }

    /**
     * 功能:将图片保存到sd卡
     *
     * @param is       图片流
     * @param path     图片保存路径
     * @param filename 图片名称 目前只支持png 与jpg图片，请对号入座
     */
    public static void saveBitmapToSD(InputStream is, String path,
                                      String filename) {

        try {
            File f = new File(path + filename);
            if (f.exists()) {
                f.delete();
            }
            CompressFormat t = getCompressFormat(filename);
            Bitmap bm = BitmapFactory.decodeStream(is);
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(t, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {

        }
    }

    /**
     * 功能:获取图片压缩格式
     *
     * @param str 文件名
     * @return
     */
    private static CompressFormat getCompressFormat(String str) {
        String strType = str.substring(str.lastIndexOf(".") + 1);
        CompressFormat t = null;
        if (strType.equals("png")) {
            t = CompressFormat.PNG;
        } else if (strType.equals("jpg")) {
            t = CompressFormat.JPEG;
        }
        return t;
    }

    /**
     * 功能:获取适合大小的图片
     *
     * @param file 目标文件
     * @return 根据图片的尺寸来自动控制文件的长度大小
     */
    public static Bitmap autoFitSizePic(File file) {
        Bitmap resizeBmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        if (file.length() < 20480) { // 0-20k
            opts.inSampleSize = 1;
        } else if (file.length() < 51200) { // 20-50k
            opts.inSampleSize = 2;
        } else if (file.length() < 307200) { // 50-300k
            opts.inSampleSize = 4;
        } else if (file.length() < 819200) { // 300-800k
            opts.inSampleSize = 6;
        } else if (file.length() < 1048576) { // 800-1024k
            opts.inSampleSize = 8;
        } else {
            opts.inSampleSize = 20;
        }
        opts.inSampleSize = 100;
        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
        return resizeBmp;
    }

    /**
     * 取得指定区域的图形
     *
     * @param source
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmap(Bitmap source, int x, int y, int width,
                                   int height) {
        Bitmap bitmap = Bitmap.createBitmap(source, x, y, width, height);
        return bitmap;
    }

    /**
     * 从大图中截取小图
     *
     * @param context
     * @param source
     * @param row
     * @param col
     * @param rowTotal
     * @param colTotal
     * @return
     */
    public static Bitmap getImage(Context context, Bitmap source, int row,
                                  int col, int rowTotal, int colTotal, float multiple,
                                  boolean isRecycle) {
        Bitmap temp = getBitmap(source, (col - 1) * source.getWidth()
                        / colTotal, (row - 1) * source.getHeight() / rowTotal,
                source.getWidth() / colTotal, source.getHeight() / rowTotal);

        if (isRecycle) {
            recycleBitmap(source);
        }
        if (multiple != 1.0) {
            Matrix matrix = new Matrix();
            matrix.postScale(multiple, multiple);
            temp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(),
                    temp.getHeight(), matrix, true);
        }
        return temp;
    }

    /**
     * 从大图中截取小图
     *
     * @param context
     * @param source
     * @param row
     * @param col
     * @param rowTotal
     * @param colTotal
     * @return
     */
    public static Drawable getDrawableImage(Context context, Bitmap source,
                                            int row, int col, int rowTotal, int colTotal, float multiple) {

        Bitmap temp = getBitmap(source, (col - 1) * source.getWidth()
                        / colTotal, (row - 1) * source.getHeight() / rowTotal,
                source.getWidth() / colTotal, source.getHeight() / rowTotal);
        if (multiple != 1.0) {
            Matrix matrix = new Matrix();
            matrix.postScale(multiple, multiple);
            temp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(),
                    temp.getHeight(), matrix, true);
        }
        Drawable d = new BitmapDrawable(context.getResources(), temp);
        return d;
    }

    public static Drawable[] getDrawables(Context context, int resourseId,
                                          int row, int col, float multiple) {
        Drawable drawables[] = new Drawable[row * col];
        Bitmap source = decodeResource(context, resourseId);
        int temp = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                drawables[temp] = getDrawableImage(context, source, i, j, row,
                        col, multiple);
                temp++;
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return drawables;
    }

    public static Drawable[] getDrawables(Context context, String resName,
                                          int row, int col, float multiple) {
        Drawable drawables[] = new Drawable[row * col];
        Bitmap source = decodeBitmapFromAssets(context, resName);
        int temp = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                drawables[temp] = getDrawableImage(context, source, i, j, row,
                        col, multiple);
                temp++;
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return drawables;
    }

    /**
     * 根据一张大图，返回切割后的图元数组
     *
     * @param resourseId :资源id
     * @param row        ：总行数
     * @param col        ：总列数 multiple:图片缩放的倍数1:表示不变，2表示放大为原来的2倍
     * @return
     */
    public static Bitmap[] getBitmaps(Context context, int resourseId, int row,
                                      int col, float multiple) {
        Bitmap bitmaps[] = new Bitmap[row * col];
        Bitmap source = decodeResource(context, resourseId);
        int temp = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                bitmaps[temp] = getImage(context, source, i, j, row, col,
                        multiple, false);
                temp++;
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return bitmaps;
    }

    public static Bitmap[] getBitmaps(Context context, String resName, int row,
                                      int col, float multiple) {
        Bitmap bitmaps[] = new Bitmap[row * col];
        Bitmap source = decodeBitmapFromAssets(context, resName);
        int temp = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                bitmaps[temp] = getImage(context, source, i, j, row, col,
                        multiple, false);
                temp++;
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return bitmaps;
    }

    public static Bitmap[] getBitmapsByBitmap(Context context, Bitmap source,
                                              int row, int col, float multiple) {
        Bitmap bitmaps[] = new Bitmap[row * col];
        int temp = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                bitmaps[temp] = getImage(context, source, i, j, row, col,
                        multiple, false);
                temp++;
            }
        }
        return bitmaps;
    }

    public static Bitmap decodeResource(Context context, int resourseId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true; // 需把 inPurgeable设置为true，否则被忽略
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt); // decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，无需再使用java层的createBitmap，从而节省了java层的空间
    }

    /**
     * 从assets文件下解析图片
     *
     * @param resName
     * @return
     */
    public static Bitmap decodeBitmapFromAssets(Context context, String resName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream in = null;
        try {
            in = context.getAssets().open(resName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(in, null, options);
    }

    /**
     * 回收不用的bitmap
     *
     * @param b
     */
    public static void recycleBitmap(Bitmap b) {
        if (b != null && !b.isRecycled()) {
            b.recycle();
            b = null;
        }
    }

    /**
     * 获取某些连在一起的图片的某一个画面（图片为横着排的情况）
     *
     * @param source
     * @param frameIndex 从1开始
     * @param totalCount
     * @return
     */
    public static Bitmap getOneFrameImg(Bitmap source, int frameIndex,
                                        int totalCount) {
        int singleW = source.getWidth() / totalCount;
        return Bitmap.createBitmap(source, (frameIndex - 1) * singleW, 0,
                singleW, source.getHeight());
    }

    public static void recycleBitmaps(Bitmap bitmaps[]) {
        if (bitmaps != null) {
            for (Bitmap b : bitmaps) {
                recycleBitmap(b);
            }
            bitmaps = null;
        }
    }

    /**
     * drawable转换成bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                    : Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());

            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new IllegalArgumentException(
                    "can not support this drawable to bitmap now!!!");
        }
    }

    /**
     * 将byte数组转化为bitmap
     *
     * @param data
     * @return
     */

    public static Bitmap byte2bitmap(byte[] data) {

        if (null == data) {

            return null;

        }

        return BitmapFactory.decodeByteArray(data, 0, data.length);

    }

    /**
     * 将bitmap转化为drawable
     *
     * @param bitmap
     * @return
     */

    public static Drawable bitmap2Drawable(Bitmap bitmap) {

        if (bitmap == null) {

            return null;

        }

        return new BitmapDrawable(bitmap);

    }

    /**
     * 按指定宽度和高度缩放图片,不保证宽高比例
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */

    public static Bitmap zoomBitmap(Bitmap bitmap, float w, float h) {

        if (bitmap == null) {

            return null;

        }

        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidht = (w / width);

        float scaleHeight = (h / height);

        matrix.postScale(scaleWidht, scaleHeight);

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,

                matrix, true);

        return newbmp;

    }

    /**
     * 将bitmap位图保存到path路径下，图片格式为Bitmap.CompressFormat.PNG，质量为100
     *
     * @param bitmap
     * @param path
     */

    public static boolean saveBitmap(Bitmap bitmap, String path) {

        try {

            File file = new File(path);

            File parent = file.getParentFile();

            if (!parent.exists()) {

                parent.mkdirs();

            }

            FileOutputStream fos = new FileOutputStream(file);

            boolean b = bitmap.compress(CompressFormat.PNG, 100, fos);

            fos.flush();

            fos.close();

            return b;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }

    /**
     * 将bitmap位图保存到path路径下
     *
     * @param bitmap
     * @param path    保存路径-Bitmap.CompressFormat.PNG或Bitmap.CompressFormat.JPEG.PNG
     * @param format  格式
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for small
     *                <p/>
     *                size, 100 meaning compress for max quality. Some formats, like
     *                <p/>
     *                PNG which is lossless, will ignore the quality setting
     * @return
     */

    public static boolean saveBitmap(Bitmap bitmap, String path,

                                     CompressFormat format, int quality) {

        try {

            File file = new File(path);

            File parent = file.getParentFile();

            if (!parent.exists()) {

                parent.mkdirs();

            }

            FileOutputStream fos = new FileOutputStream(file);

            boolean b = bitmap.compress(format, quality, fos);

            fos.flush();

            fos.close();

            return b;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }

    /**
     * 获得圆角图片
     *
     * @param bitmap
     * @param roundPx
     * @return
     */

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        if (bitmap == null) {

            return null;

        }

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),

                bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

    /**
     * 获得带倒影的图片
     */

    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {

        if (bitmap == null) {

            return null;

        }
        final int reflectionGap = 4;

        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();

        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,

                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,

                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint deafalutPaint = new Paint();

        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();

        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,

                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,

                0x00ffffff, TileMode.CLAMP);

        paint.setShader(shader);

        // Set the Transfer mode to be porter duff and destination in

        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        // Draw a rectangle using the paint with our linear gradient

        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()

                + reflectionGap, paint);

        return bitmapWithReflection;

    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmapByFile(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 创建缩略图
     *
     * @param context
     * @param largeImagePath 原始大图路径
     * @param thumbfilePath  输出缩略图路径
     * @param square_size    输出图片宽度
     * @param quality        输出图片质量
     * @throws IOException
     */
    public static void createImageThumbnail(Context context,
                                            String largeImagePath, String thumbfilePath, int square_size,
                                            int quality) throws IOException {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        // 原始图片bitmap
        Bitmap cur_bitmap = getBitmapByPath(largeImagePath, opts);

        if (cur_bitmap == null)
            return;

        // 原始图片的高宽
        int[] cur_img_size = new int[]{cur_bitmap.getWidth(),
                cur_bitmap.getHeight()};
        // 计算原始图片缩放后的宽高
        int[] new_img_size = scaleImageSize(cur_img_size, square_size);
        // 生成缩放后的bitmap
        Bitmap thb_bitmap = zoomBitmap(cur_bitmap, new_img_size[0],
                new_img_size[1]);
        // 生成缩放后的图片文件
        saveImageToSD(null, thumbfilePath, thb_bitmap, quality);
    }

    /**
     * 计算缩放图片的宽高
     *
     * @param img_size
     * @param square_size
     * @return
     */
    public static int[] scaleImageSize(int[] img_size, int square_size) {
        if (img_size[0] <= square_size && img_size[1] <= square_size)
            return img_size;
        double ratio = square_size
                / (double) Math.max(img_size[0], img_size[1]);
        return new int[]{(int) (img_size[0] * ratio),
                (int) (img_size[1] * ratio)};
    }

    /**
     * 写图片文件到SD卡
     *
     * @throws IOException
     */
    public static void saveImageToSD(Context ctx, String filePath,
                                     Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,
                    filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            if (ctx != null) {
                scanPhoto(ctx, filePath);
            }
        }
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    private static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

}
