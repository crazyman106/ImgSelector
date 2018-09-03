package com.example.cusexcel

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.cusexcel.view.ActionSheetDialog
import com.example.cusexcel.view.InputDialog
import com.example.cusexcel.view.TableView
import com.example.cusexcel.view.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var curClickPosition: Int? = -1;
    var actionSheetDialog: ActionSheetDialog? = null;
    var type: Int? = -1;// 0:表示内边框;1:表示外边框
    var tableView: TableView? = null;
    var imm: InputMethodManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionSheetDialog = ActionSheetDialog(this@MainActivity).builder()

        // 设置表格点击删除,按下缩放,双击文本录入,以及其他属性和函数.
        tableSetting(table)

        // 为了删除后手动添加原始表格,直接创建对象,将gravity和宽高等属性通过LayoutParams来设置.
        add_form.setOnClickListener {
            // 为了防止重复添加表格
            if (tableView != null) {
                rootview.removeViewAt(0);
                rootview.requestLayout()
                tableView = null
            }
            tableView = TableView(this@MainActivity)
            rootview.addView(tableView, 0)
            var layoutParams: RelativeLayout.LayoutParams = tableView?.layoutParams as RelativeLayout.LayoutParams;
            layoutParams.height = Util.dip2px(this@MainActivity, 200f)
            layoutParams.width = Util.dip2px(this@MainActivity, 360f)
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tableSetting(tableView!!)
        }
    }


    fun tableSetting(tableview: TableView) {
        // 给外边框和内边框设置line样式(不同粗细的直线和间隔线)
        actionSheetDialog?.setOnLineClickListener {
            when (it) { // it:分别表示四条不同的线段
                1 -> {
                    if (type == 1) {//0:表示内边框;1:表示外边框
                        tableview.setOuterFull(false)// 设置外边框直线,flag:是否加粗,false:不加粗;true:加粗
                    } else if (type == 0) {
                        tableview.setInnerFull(false)// 设置框内直线,flag:是否加粗
                    }
                }
                2 -> {
                    if (type == 1) {
                        tableview.setOuterFull(true)
                    } else if (type == 0) {
                        tableview.setInnerFull(true)
                    }
                }
                3 -> {
                    if (type == 1) {
                        tableview.setOuterDash(false)// 设置外边框间隔线,flag:是否加粗,false:不加粗;true:加粗
                    } else if (type == 0) {
                        tableview.setInnerDash(false)// 设置框内间隔线,flag:是否加粗
                    }
                }
                4 -> {
                    if (type == 1) {
                        tableview.setOuterDash(true)
                    } else if (type == 0) {
                        tableview.setInnerDash(true)
                    }
                }
            }
        }

        // 点击选择线的样式:
        outline.setOnClickListener {
            actionSheetDialog?.show();
            type = 1;
        }

        innerLine.setOnClickListener {
            actionSheetDialog?.show();
            type = 0;
        }

        // 双击,edittext获取焦点,弹出软键盘,录入文本内容
        tableview.setOnDoubleClickListener {
            InputDialog(this@MainActivity).builder().show()
          /*  input.visibility = View.VISIBLE
            Toast.makeText(this@MainActivity,"双击",Toast.LENGTH_LONG).show();*/
          /*  input.setFocusable(true)
            input.isFocusableInTouchMode = true;
            input.requestFocus()*/
          /*  if (imm == null) {
                imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            imm!!.showSoftInput(input, 0)*/
//            curClickPosition = it;


        }

        // 把Edittext的imeOptions属性设置成actionDone,对应EditorInfo.IME_ACTION_DONE
        // Edittext的右下角action点击监听
      /*  input.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false
                if (actionId === EditorInfo.IME_ACTION_DONE) {
                    handled = true
                    *//*隐藏软键盘*//*
                  *//*  val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (inputMethodManager.isActive) {
                        inputMethodManager.hideSoftInputFromWindow(this@MainActivity.currentFocus!!.windowToken, 0)
                    }*//*
                    // 根据我们点击的表格位置来输入内容.
                    if (curClickPosition != -1) {
                        tableview.setContent(curClickPosition!!, input.text.toString());
                        Log.e("OnEditorAction", input.text.toString() + "");
                    }
                    input.visibility = View.GONE
                    input.setText("")
//                    imm!!.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                }
                return handled
            }
        })*/

        // 行+1
        row_add.setOnClickListener { tableview.addRow() }
        // 行-1
        row_subtract.setOnClickListener { tableview.subtractRow() }
        // 列+1
        column_add.setOnClickListener { tableview.addColumn() }
        // 列-1
        column_subtract.setOnClickListener { tableview.subtractColumn() }

        // 给表格的外边框设置色值
        out_color.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.out_dark_black -> {
                    tableview.setOuterColor(Color.BLACK)
                }
                R.id.out_light_black -> {
                    tableview.setOuterColor(Color.parseColor("#555555"))
                }
                R.id.out_dark_gray -> {
                    tableview.setOuterColor(Color.parseColor("#888888"))
                }
            }
        }
        // 给表格的内边框设置色值
        inner_color.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.inner_dark_black -> {
                    tableview.setInnerColor(Color.BLACK)
                }
                R.id.inner_light_black -> {
                    tableview.setInnerColor(Color.parseColor("#555555"))
                }
                R.id.inner_dark_gray -> {
                    tableview.setInnerColor(Color.parseColor("#888888"))
                }
            }
        }
        // 删除表格
        tableview.setOnDelClickListener {
            rootview.removeViewAt(0);
            rootview.requestLayout()
            tableView = null
        }
    }
}
