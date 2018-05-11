package com.ljj.imgselector.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * @author lijunjie on 2018/1/16 0016.
 * @description
 */

public class CHScroolView extends HorizontalScrollView {
    public CHScroolView(Context context) {
        this(context, null);
    }

    public CHScroolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }
}
