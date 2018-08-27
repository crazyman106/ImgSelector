package com.example.cusexcel.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.cusexcel.R;


/**
 * Created by Administrator on 2017/4/10 0010.
 */
public class MoreMenuPop {

    View viewParent;
    Context context;
    int[] location = new int[2];

    private PopupWindow popupWindow;

    public MoreMenuPop(Context context) {
        viewParent = LayoutInflater.from(context).inflate(R.layout.line_category, null);
        popupWindow = new PopupWindow(viewParent, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewParent.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }

    public MoreMenuPop show(View view, final OnLinePickListener listener) {
        viewParent.findViewById(R.id.line1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Line1();
                    popupWindow.dismiss();
                }
            }
        });

        viewParent.findViewById(R.id.line2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Line2();
                    popupWindow.dismiss();
                }
            }
        });

        viewParent.findViewById(R.id.line3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Line3();
                    popupWindow.dismiss();
                }
            }
        });

        viewParent.findViewById(R.id.line4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Line4();
                    popupWindow.dismiss();
                }
            }
        });

        view.getLocationOnScreen(location);
        popupWindow.showAsDropDown(view, 0, 0);
        return this;
    }

    public void setOnLinePickListener(OnLinePickListener pickListener) {
        this.listener = pickListener;
    }

    private OnLinePickListener listener;

    public interface OnLinePickListener {
        void Line1();

        void Line2();

        void Line3();

        void Line4();
    }

}
