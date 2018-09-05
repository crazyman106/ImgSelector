package com.youdianpinwei.textimg

import android.content.Context
import android.widget.Toast

/**
 * @author lijunjie on 2018/9/5 0005.
 * @description
 */

fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}