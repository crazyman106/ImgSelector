package com.fengzi.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunjie on 2018/1/10 0010.
 * @description
 */

public class CustomTabLayout extends TableLayout {
    private List<String> mTitles;
    private List<Integer> mResImgs;
    private List<String> mNetImgs;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout setTitles(ArrayList<String> mTitles) {
        this.mTitles = mTitles;
        return this;
    }

    public CustomTabLayout setResImgs(ArrayList<Integer> mResImgs) {
        this.mResImgs = mResImgs;
        return this;
    }

    public CustomTabLayout setNetmgs(ArrayList<String> mNetImgs) {
        this.mNetImgs = mNetImgs;
        return this;
    }
}
