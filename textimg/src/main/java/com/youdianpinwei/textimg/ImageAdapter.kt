package com.youdianpinwei.textimg

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * @author lijunjie on 2018/9/5 0005.
 * @description
 */
class ImageAdapter constructor(val imgList: MutableList<Uri>, val context: Context) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var imageView = ImageView(context)
        imageView.setImageURI(imgList[position])
        return imageView
    }

    override fun getItem(position: Int): Any {
        return imgList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return imgList.size
    }
}