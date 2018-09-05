package com.youdianpinwei.textimg

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.SeekBar
import com.youdianpinwei.textimg.Utils.baseSize
import com.youdianpinwei.textimg.Utils.getImageBitmap
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private val PHOTO_REQUEST_GALLERY = 105

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt1.textSize = Utils.baseSize
        txt2.textSize = Utils.baseSize
        input.textSize = Utils.baseSize
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }
        input_scroll.post(object : Runnable {
            override fun run() {
                input_scroll.fullScroll(ScrollView.FOCUS_RIGHT)
                portrait.fullScroll(ScrollView.FOCUS_DOWN)
            }

        })

        switch_mode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // 纵向
                portrait.visibility = View.VISIBLE
                txt2.visibility = View.GONE
            } else {
                // 横向
                portrait.visibility = View.GONE
                txt2.visibility = View.VISIBLE
            }
            portrait.fullScroll(ScrollView.FOCUS_DOWN)
        }
        //.+

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.e("afterText", s.toString());
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("beforeTextChanged", s.toString());
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("onTextChanged", s.toString());
                if (!txt1.text.equals(s.toString()))
                    txt1.text = s;
                txt2.text = s;
            }
        })

        selectimg.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            } else {
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY)
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txt1.textSize = baseSize + progress.toFloat()
                txt2.textSize = baseSize + progress.toFloat()
                input.textSize = baseSize + progress.toFloat()
                Log.e("img_size2", progress.toString())
                var inputMsg = input.editableText.toString()
                val sinaPatten = Pattern.compile("<img=[^>]*\\>", Pattern.CASE_INSENSITIVE)
                val matcher = sinaPatten.matcher(inputMsg)
                while (matcher.find()) {
                    var uri = matcher.group()
                    val bitmap = getImageBitmap(this@MainActivity, Uri.parse(uri.substring(5, uri.length - 2)))?.let {
                        Utils.getThumbBitmap(it, input.textSize.toFloat(), input.textSize.toFloat())
                    }
                    val imageSpan = ImageSpan(this@MainActivity, bitmap)
                    //创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
                    val spannableString = SpannableString("<img=${uri.substring(5, uri.length - 2)}/>")
                    //  用ImageSpan对象替换face
                    spannableString.setSpan(imageSpan, 0, "<img=${uri.substring(5, uri.length - 2)}/>".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    //将选择的图片追加到EditText中光标所在位置
                    val edit_text = input.getEditableText()
                    edit_text.replace(/*0, uri.length, */ edit_text.indexOf(uri), edit_text.indexOf(uri) + uri.length, spannableString)

                    txt1.setText(edit_text)
                    txt2.setText(edit_text)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            data?.let {
                val bitmap = getImageBitmap(this, data.data)?.let {
                    Utils.getThumbBitmap(it, input.textSize.toFloat(), input.textSize.toFloat())
                }
                Log.e("img_size1", input.textSize.toString())
                val imageSpan = ImageSpan(this@MainActivity, bitmap)
                //创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
                val spannableString = SpannableString("<img=${data.data}/>")
                //  用ImageSpan对象替换face
                spannableString.setSpan(imageSpan, 0, "<img=${data.data}/>".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                //将选择的图片追加到EditText中光标所在位置
                val index = input.getSelectionStart() //获取光标所在位置
                val edit_text = input.getEditableText()
                if (index < 0 || index >= edit_text.length) {
                    edit_text.append(spannableString)
                } else {
                    edit_text.insert(index, spannableString)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 获得授权
                showToast("获取SDCard读写权限成功")
            } else {
                // 被禁止授权
                showToast("获取SDCard读写权限失败")
            }
        }
    }

}
