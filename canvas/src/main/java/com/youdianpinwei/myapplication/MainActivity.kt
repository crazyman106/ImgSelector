package com.youdianpinwei.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var cubeBezierCurve: CubeBezierView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cubeBezierCurve = findViewById(R.id.cubeBezierView)


        control1.setOnClickListener {
            cubeBezierCurve.setModel(true)
        }
        control2.setOnClickListener {
            cubeBezierCurve.setModel(false)
        }
    }


}
