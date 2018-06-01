package com.ljj.imgselector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljj.imgselector.adapter.ImageViewAdapter;
import com.ljj.imgselector.bean.Image;
import com.ljj.imgselector.utils.Constants;
import com.ljj.imgselector.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunjie on 2017/11/24 0024.
 * @description
 */

public class PhotoFragment extends Fragment {
    private View rootView;
    private LinearLayout topView, bottomView;
    private TextView curPage, selector, okNums;
    private MyViewPager viewPager;
    private ImageViewAdapter imageViewAdapter;
    private List<Image> images;
    private int position = -1;
    private List<Image> mSelectImages;
    private Image curImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.photo_view_fragment_select_img, container, false);
        topView = rootView.findViewById(R.id.top);
        bottomView = rootView.findViewById(R.id.bottom);
        curPage = rootView.findViewById(R.id.photoview_curpage);
        selector = rootView.findViewById(R.id.photoview_selector);
        okNums = rootView.findViewById(R.id.photoview_ok);
        viewPager = rootView.findViewById(R.id.photoview_viewpager);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) topView.getLayoutParams();
        lp.topMargin = getStatusBarHeight(getContext());
        topView.setLayoutParams(lp);
        setStatusBarVisible(true);
        return rootView;
    }

    public static PhotoFragment newInstance(List<Image> images, int position) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("images", (ArrayList<? extends Parcelable>) images);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public void setmSelectImages(List<Image> images) {
        this.mSelectImages = images;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        topView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        bottomView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if (getArguments() != null) {
            if (images == null) {
                images = new ArrayList<>();
            }
            this.images.clear();
            ArrayList<Image> images = getArguments().getParcelableArrayList("images");
            this.images.addAll(images);
            position = getArguments().getInt("position");
        }
        imageViewAdapter = new ImageViewAdapter(images, getContext());
        viewPager.setAdapter(imageViewAdapter);
        if (position != -1) {
            viewPager.setCurrentItem(position);
            curImage = images.get(position);
            curPage.setText((position + 1) + "/" + (images.size()));
        }
        final Drawable selectedDrawable = ContextCompat.getDrawable(getContext(), R.drawable.photoview_select_ok);
        final Drawable selectnoDrawable = ContextCompat.getDrawable(getContext(), R.drawable.photoview_select_cancel);
        /// 这一步必须要做,否则不会显示.
        selectedDrawable.setBounds(0, 0, selectedDrawable.getMinimumWidth(), selectedDrawable.getMinimumHeight());
        selectnoDrawable.setBounds(0, 0, selectnoDrawable.getMinimumWidth(), selectnoDrawable.getMinimumHeight());
        imageViewAdapter.setOnPhotoViewClickListener(new ImageViewAdapter.OnPhotoViewClickListener() {
            @Override
            public void onClick() {
                updateBarStatus();
            }
        });

        setOkTxt(((ImgSelectorActivity) getActivity()).curGalleryMode);
        if (mSelectImages.contains(images.get(position))) {
            selector.setCompoundDrawables(selectedDrawable, null, null, null);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPage.setText((position + 1) + "/" + (images.size()));
                if (mSelectImages.contains(images.get(position))) {
                    selector.setCompoundDrawables(selectedDrawable, null, null, null);
                } else {
                    selector.setCompoundDrawables(selectnoDrawable, null, null, null);
                }
                curImage = images.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (((ImgSelectorActivity) getActivity()).curGalleryMode) {
                    case Constants.MULTIPLE:
                        if (mSelectImages.contains(curImage)) {
                            mSelectImages.remove(curImage);
                            onImageSelectListener.select(curImage, false);
                            selector.setCompoundDrawables(selectnoDrawable, null, null, null);
                        } else {
                            mSelectImages.add(curImage);
                            onImageSelectListener.select(curImage, true);
                            selector.setCompoundDrawables(selectedDrawable, null, null, null);
                        }
                        break;
                    case Constants.SINGLE:
                        if (mSelectImages.contains(curImage)) {
                            mSelectImages.remove(curImage);
                            onImageSelectListener.select(curImage, false);
                            selector.setCompoundDrawables(selectnoDrawable, null, null, null);
                        } else {
                            mSelectImages.clear();
                            mSelectImages.add(curImage);
                            onImageSelectListener.select(curImage, true);
                            selector.setCompoundDrawables(selectedDrawable, null, null, null);
                        }
                        break;
                    default:
                }

                setOkTxt(((ImgSelectorActivity) getActivity()).curGalleryMode);
            }
        });
        okNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectImages != null && mSelectImages.size() > 0) {
                    onImageSelectListener.confirm();
                }
            }
        });
        rootView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImgSelectorActivity)getActivity()).onBackPressed();
            }
        });
    }


    private void setOkTxt(int curMode) {
        if (curMode == Constants.MULTIPLE) {
            if (mSelectImages != null && mSelectImages.size() > 0) {
                okNums.setText(mSelectImages.size() + " 确定");
                okNums.setBackgroundResource(R.drawable.ok_ed);
            } else {
                okNums.setText("确定");
                okNums.setBackgroundResource(R.drawable.ok_cancel);
            }
        } else {
            okNums.setText("确定");
            if (mSelectImages != null && mSelectImages.size() > 0) {
                okNums.setBackgroundResource(R.drawable.ok_ed);
            } else {
                okNums.setBackgroundResource(R.drawable.ok_cancel);
            }
        }
    }

    private boolean barIsShowing = true;

    private void updateBarStatus() {
        if (barIsShowing) {
            ObjectAnimator topAnimator = ObjectAnimator.ofFloat(topView, "translationY", 0, -topView.getHeight()).setDuration(300);
            topAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    topView.setVisibility(View.GONE);
                    setStatusBarVisible(false);
                }
            });
            topAnimator.start();
            ObjectAnimator.ofFloat(bottomView, "translationY", 0, bottomView.getHeight()).setDuration(300).start();
            barIsShowing = false;
        } else {
            setStatusBarVisible(true);
            topView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator topAnimator = ObjectAnimator.ofFloat(topView, "translationY", topView.getTranslationY(), 0).setDuration(300);
                    topAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            topView.setVisibility(View.VISIBLE);
                        }
                    });
                    topAnimator.start();
                    ObjectAnimator.ofFloat(bottomView, "translationY", bottomView.getTranslationY(), 0).setDuration(300).start();
                    barIsShowing = true;
                }
            }, 100);
        }
    }

    /**
     * 显示和隐藏状态栏
     * setSystemUiVisibility(int visibility)方法可传入的实参为：
     * 1. View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)。
     * 2. View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
     * 3. View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
     * 4. View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
     * 5. View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * 6. View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * 7. View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
     * 8. View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
     *
     * @param show
     */
    private void setStatusBarVisible(boolean show) {
        if (show) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private OnImageSelectListener onImageSelectListener;

    public void setOnImageSelectListener(OnImageSelectListener onImageSelectListener) {
        this.onImageSelectListener = onImageSelectListener;
    }

    public interface OnImageSelectListener {
        void select(Image image, boolean flag);

        void confirm();
    }
}
