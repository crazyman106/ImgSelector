package com.youdianpinwei.textimg

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * @author lijunjie on 2018/9/4 0004.
 * @description
 */
object Utils {
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
}