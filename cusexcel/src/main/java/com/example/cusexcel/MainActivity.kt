package com.example.cusexcel

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var curClickPosition: Int? = -1;
    private var img1Adapter: ImageAdapter? = null;
    private var img2Adapter: ImageAdapter? = null;


    var imm: InputMethodManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        img1Adapter = ImageAdapter(this@MainActivity)
        img2Adapter = ImageAdapter(this@MainActivity)






        table.setOnDoubleClickListener {
            input.visibility = View.VISIBLE
            input.setFocusable(true)
            input.isFocusableInTouchMode = true;
            input.requestFocus()
            if (imm == null) {
                imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            imm!!.showSoftInput(input, 0)
            curClickPosition = it;
            table.setContent(it, "double")
        }

        input.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false
                if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                    handled = true
                    /*隐藏软键盘*/
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (inputMethodManager.isActive) {
                        inputMethodManager.hideSoftInputFromWindow(this@MainActivity.currentFocus!!.windowToken, 0)
                    }
                    if (curClickPosition != -1)
                        table.setContent(curClickPosition!!, input.text.toString());
                    input.visibility = View.GONE
                }
                return handled
            }
        })

        row_add.setOnClickListener { table.addRow() }

        row_subtract.setOnClickListener { table.subtractRow() }

        column_add.setOnClickListener { table.addColumn() }

        column_subtract.setOnClickListener { table.subtractColumn() }

        add_form.setOnClickListener { }

        out_color.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.out_dark_black -> {
                    table.setOuterColor(Color.BLACK)
                }
                R.id.out_light_black -> {
                    table.setOuterColor(Color.parseColor("#555555"))
                }
                R.id.out_light_black -> {
                    table.setOuterColor(Color.parseColor("#888888"))
                }
            }
        }

        inner_color.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.inner_dark_black -> {
                    table.setInnerColor(Color.BLACK)
                }
                R.id.inner_light_black -> {
                    table.setInnerColor(Color.parseColor("#555555"))
                }
                R.id.inner_dark_gray -> {
                    table.setInnerColor(Color.parseColor("#888888"))
                }
            }
        }
    }
}
