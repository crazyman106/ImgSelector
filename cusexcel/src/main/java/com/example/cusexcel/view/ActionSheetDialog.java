package com.example.cusexcel.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cusexcel.R;

public class ActionSheetDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private ImageView img1, img2, img3, img4;
    private TextView daterMine;
    private int curClickIndex = -1;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.bottom_dialog, null);
        view.setMinimumWidth(display.getWidth());
        // 获取自定义Dialog布局中的控件
        img1 = view.findViewById(R.id.line1);
        img2 = view.findViewById(R.id.line2);
        img3 = view.findViewById(R.id.line3);
        img4 = view.findViewById(R.id.line4);

        daterMine = view.findViewById(R.id.datermine);
        daterMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.setLine(curClickIndex);
                    img4.setBackgroundColor(Color.parseColor("#00000000"));
                    img1.setBackgroundColor(Color.parseColor("#00000000"));
                    img2.setBackgroundColor(Color.parseColor("#00000000"));
                    img3.setBackgroundColor(Color.parseColor("#00000000"));
                }
                dialog.dismiss();
            }
        });

        /**
         * 因为没有选中和未选中的图片,所以暂时用背景来替代.
         */
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setBackgroundColor(Color.parseColor("#cccccc"));
                img2.setBackgroundColor(Color.parseColor("#00000000"));
                img3.setBackgroundColor(Color.parseColor("#00000000"));
                img4.setBackgroundColor(Color.parseColor("#00000000"));
                curClickIndex = 1;
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img2.setBackgroundColor(Color.parseColor("#cccccc"));
                img1.setBackgroundColor(Color.parseColor("#00000000"));
                img3.setBackgroundColor(Color.parseColor("#00000000"));
                img4.setBackgroundColor(Color.parseColor("#00000000"));
                curClickIndex = 2;
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img3.setBackgroundColor(Color.parseColor("#cccccc"));
                img1.setBackgroundColor(Color.parseColor("#00000000"));
                img2.setBackgroundColor(Color.parseColor("#00000000"));
                img4.setBackgroundColor(Color.parseColor("#00000000"));
                curClickIndex = 3;
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img4.setBackgroundColor(Color.parseColor("#cccccc"));
                img1.setBackgroundColor(Color.parseColor("#00000000"));
                img2.setBackgroundColor(Color.parseColor("#00000000"));
                img3.setBackgroundColor(Color.parseColor("#00000000"));
                curClickIndex = 4;
            }
        });


        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public void show() {
        curClickIndex = -1;
        dialog.show();
    }

    public interface OnLineClickListener {
        void setLine(int lineType);
    }

    private OnLineClickListener listener;

    public void setOnLineClickListener(OnLineClickListener listener) {
        this.listener = listener;
    }
}
