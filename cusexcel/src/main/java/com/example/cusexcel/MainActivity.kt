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
import com.example.cusexcel.view.ActionSheetDialog
import com.example.cusexcel.view.MoreMenuPop
import com.example.cusexcel.view.TableView
import com.example.cusexcel.view.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var curClickPosition: Int? = -1;
    var outPou: MoreMenuPop? = null;
    var innerPou: MoreMenuPop? = null;
    var actionSheetDialog: ActionSheetDialog? = null;
    var type: Int? = -1;// 0:表示内边框;1:表示外边框
    var tableView: TableView? = null;
    var imm: InputMethodManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        outPou = MoreMenuPop(this@MainActivity)
        innerPou = MoreMenuPop(this@MainActivity)

        actionSheetDialog = ActionSheetDialog(this@MainActivity).builder()

        tableSetting(table)

        add_form.setOnClickListener {
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
            tableSetting(tableView!!)
        }
    }


    fun tableSetting(tableview: TableView) {
        actionSheetDialog?.setOnLineClickListener {
            when (it) {
                1 -> {
                    if (type == 1) {
                        tableview.setOuterFull(false)
                    } else if (type == 0) {
                        tableview.setInnerFull(false)
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
                        tableview.setOuterDash(false)
                    } else if (type == 0) {
                        tableview.setInnerDash(false)
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
        outline.setOnClickListener {
            actionSheetDialog?.show();
            type = 1;
        }

        innerLine.setOnClickListener {
            actionSheetDialog?.show();
            type = 0;
        }

        tableview.setOnDoubleClickListener {
            input.visibility = View.VISIBLE
            input.setFocusable(true)
            input.isFocusableInTouchMode = true;
            input.requestFocus()
            if (imm == null) {
                imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            imm!!.showSoftInput(input, 0)
            curClickPosition = it;
        }

        input.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false
                if (actionId === EditorInfo.IME_ACTION_DONE) {
                    handled = true
                    /*隐藏软键盘*/
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (inputMethodManager.isActive) {
                        inputMethodManager.hideSoftInputFromWindow(this@MainActivity.currentFocus!!.windowToken, 0)
                    }
                    if (curClickPosition != -1) {
                        tableview.setContent(curClickPosition!!, input.text.toString());
                        Log.e("OnEditorAction", input.text.toString() + "");
                    }
                    input.visibility = View.GONE
                    input.setText("")
                    imm!!.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                }
                return handled
            }
        })

        row_add.setOnClickListener { tableview.addRow() }

        row_subtract.setOnClickListener { tableview.subtractRow() }

        column_add.setOnClickListener { tableview.addColumn() }

        column_subtract.setOnClickListener { tableview.subtractColumn() }

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
        tableview.setOnDelClickListener {
            rootview.removeViewAt(0);
            rootview.requestLayout()
            tableView = null
        }
    }
}
