package com.example.administrator.paint.funny.view;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.paint.funny.AppConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 2016/4/13.
 * // 每行文字:使用多个text,每个text中包含一个字符组成
 */
public class AddWordInsideLinearlayout extends LinearLayout {
    private String text;
    private Context context;
    private int color;
    private int size;
    private List<TextView> textViews;
    private AddWordTextView myText;

    public AddWordInsideLinearlayout(Context context) {
        super(context);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textViews = new ArrayList<TextView>();
    }

    public AddWordInsideLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextViewOrientation(VERTICAL);
        this.context = context;
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textViews = new ArrayList<TextView>();
    }

    public void setTextViewOrientation(int orientation) {
        setOrientation(orientation);
    }

    public void setText(String text) {
        this.text = text;
        addText();
    }

    private void addText() {
        removeAllViews();
        textViews.clear();

        if (!TextUtils.isEmpty(text)) {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                myText = new AddWordTextView(context);
                myText.setTextColor(color);
                if (size > 0) {
                    myText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                }
                myText.setText(text.substring(i, i + 1));
                textViews.add(myText);
                myText.setGravity(Gravity.CENTER);
                addView(myText);
            }

            myText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        myText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    if (myText.getHeight() != 0) {
                        AppConst.textHeight = myText.getHeight();
                    }
                    AppConst.textHeight = myText.getHeight();
                }
            });
        }
    }

    public List<TextView> getTextViews() {
        return textViews;
    }

    public void setTextViews(List<TextView> textViews) {
        this.textViews = textViews;
    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }
}
