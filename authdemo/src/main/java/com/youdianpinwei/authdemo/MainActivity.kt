package com.youdianpinwei.authdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private var mBt: Button? = null
    private var mUser_package_name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBt = findViewById(R.id.bt_confirm_login)
        mBt?.setOnClickListener({
            val intent1 = Intent()
            intent1.setAction("kx.com.kx.bapp.sign")
            intent1.putExtra("token", "xxxx-xxxx-xxxxx")
            intent1.putExtra("app_pkg", "com.kx.aapp")
            sendBroadcast(intent1)
            finish()
        })
        mBt?.isEnabled = false

        //获取传递的数据
        val intent = intent
        val uri = intent.data

        //获取参数值
        val type = uri.getQueryParameter("type")
        mUser_package_name = uri.getQueryParameter("user_package_name")

        //类型type 检验
        if (TextUtils.equals(type, "1")) {
            //Todo 未登录 处理
            request5037()
        }
    }

    /**
     * 外部 app 拉起授权 (code:5037)
     * 服务器校验 是否授权
     * 授权 页面A状态
     */

    private fun request5037() {
        mBt?.setEnabled(true)
    }

}
