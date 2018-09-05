package com.youdianpinwei.textimg

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue

/**
 * @author lijunjie on 2018/9/4 0004.
 * @description
 */
object Utils {

    const val baseSize: Float = 18f;

    @JvmStatic
    fun px2sp(pxVal: Float): Float {
        return pxVal / Resources.getSystem().displayMetrics.scaledDensity
    }

    @JvmStatic
    fun sp2px(spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, Resources.getSystem().displayMetrics).toInt()
    }

    @JvmStatic
    fun getThumbBitmap(srcBitmap: Bitmap, dstWidth: Float, dstHeight: Float): Bitmap? {
        if (null == srcBitmap) {
            return null
        }
        if (srcBitmap!!.isRecycled()) {
            return null
        }
        if (dstWidth <= 0 || dstHeight <= 0) {
            return null
        }
        // 获取图片的宽和高
        val width = srcBitmap!!.getWidth()
        val height = srcBitmap!!.getHeight()
        // 创建操作图片的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = dstWidth.toFloat() / width
        val scaleHeight = dstHeight.toFloat() / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(srcBitmap, 0, 0, width.toInt(), height.toInt(), matrix, true)
    }

    @JvmStatic
    fun getImageBitmap(context: Context, data: Uri): Bitmap? {
        val bitmap: Bitmap
        try {
            val resolver = context.getContentResolver()
            val originalUri = data
            Log.e("imgUri", originalUri!!.path + ";" + originalUri.toString())
            bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri)
        } catch (e: Exception) {
            return null
        }

        return bitmap
    }
}