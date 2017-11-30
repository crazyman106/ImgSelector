package com.ljj.imgselector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljj.imgselector.adapter.FolderAdapter;
import com.ljj.imgselector.adapter.ImagesAdapter;
import com.ljj.imgselector.bean.Folder;
import com.ljj.imgselector.bean.Image;
import com.ljj.imgselector.bean.SelectImgParams;
import com.ljj.imgselector.modle.ImageModel;
import com.ljj.imgselector.utils.Constants;
import com.ljj.imgselector.utils.GridDividerItemDecoration;
import com.ljj.imgselector.view.AutoLoadRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    AutoLoadRecyclerView recyclerImgs, recyclerFolders;
    ImageView back;
    TextView okNums, folderName, preview;
    private List<Folder> mFolders;
    ImagesAdapter imagesAdapter;
    FolderAdapter folderAdapter;
    View folderViewBg;
    private boolean folderIsOpening = false;
    FrameLayout fragmentContainer;
    PhotoFragment photoFragment;
    LinearLayout topView;
    List<Image> selectImages;
    SelectImgParams selectImgParams;
    private String imageFolderPath;

    public int curGalleryMode = Constants.MULTIPLE;
    public int curCropMode = -1, curTakeCamera = Constants.TAKE_CAMERA;


    public static void startActivityForResult(Activity activity, SelectImgParams params, int requestCode) {
        if (TextUtils.isEmpty(params.getImageFolderPath())) {
            Toast.makeText(activity, "请设置图片路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Constants.CHOOSE_MODE, params);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor();
        folderViewBg = findViewById(R.id.folders_bg);
        recyclerImgs = findViewById(R.id.imageviews);
        fragmentContainer = findViewById(R.id.photoview_container);
        recyclerFolders = findViewById(R.id.folders);
        topView = findViewById(R.id.top);
        GridLayoutManager imgsManager;
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            imgsManager = new GridLayoutManager(this, 4);
        } else {
            imgsManager = new GridLayoutManager(this, 6);
        }
        ((SimpleItemAnimator) recyclerImgs.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerImgs.setLayoutManager(imgsManager);
        recyclerImgs.addItemDecoration(new GridDividerItemDecoration(6, getResources().getColor(R.color.c_f54548)));
        recyclerFolders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        back = findViewById(R.id.back);
        okNums = findViewById(R.id.ok_nums);
        folderName = findViewById(R.id.tv_folder_name);
        preview = findViewById(R.id.preview);
        //fengzi/image
        imageFolderPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "fengzi/image";
        Log.e("imageFolderPath", imageFolderPath);
        selectImgParams = getIntent().getParcelableExtra(Constants.CHOOSE_MODE);
        if (selectImgParams != null) {
            curGalleryMode = selectImgParams.getGalleryMode();
            curCropMode = selectImgParams.getCropMode();
            curTakeCamera = selectImgParams.getCamearMode();
            imageFolderPath = selectImgParams.getImageFolderPath();
        }
        loadImageForSDCard();
        // 选择图片文件夹
        findViewById(R.id.select_folder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderIsOpening) {
                    closeFolder();
                } else {
                    openFolder();
                }
            }
        });
        folderViewBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderIsOpening) {
                    closeFolder();
                }
            }
        });
        findViewById(R.id.preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectImages == null || selectImages.size() == 0) {
                    return;
                }
                startFragment(selectImages, 0);
            }
        });
        findViewById(R.id.ok_nums).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectImages != null && selectImages.size() > 0) {
                    setIntentOk();
                }
            }
        });

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) topView.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight(this);
        topView.setLayoutParams(layoutParams);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        selectImages = new ArrayList<>();
        recyclerFolders.post(new Runnable() {
            @Override
            public void run() {
                recyclerFolders.setTranslationY(recyclerFolders.getHeight());
                recyclerFolders.setVisibility(View.GONE);
            }
        });
    }


    private void loadImageForSDCard() {
        ImageModel.loadImageForSDCard(this, new ImageModel.DataCallback() {
            @Override
            public void onSuccess(final ArrayList<Folder> folders) {
                if (folders == null) {
                    return;
                }
                if (mFolders == null) {
                    mFolders = new ArrayList<>();
                }
                mFolders.clear();
                mFolders.addAll(folders);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imagesAdapter == null) {
                            imagesAdapter = new ImagesAdapter(mFolders.get(0).getImgs());
                            imagesAdapter.setCamearMode(curTakeCamera);
                            recyclerImgs.setAdapter(imagesAdapter);
                        } else {
                            imagesAdapter.refresh(mFolders.get(0).getImgs());
                        }
                        imagesAdapter.setOnImageItemClickListener(new ImagesAdapter.OnImageItemClickListener() {
                            @Override
                            public void onClick(List<Image> images, int position) {
                                startFragment(images, position);
                            }

                            @Override
                            public void onItemCheck(Image image) {
                                switch (curGalleryMode) {
                                    case Constants.SINGLE:
                                        if (selectImages.contains(image)) {
                                            selectImages.remove(image);
                                        } else {
                                            selectImages.clear();
                                            selectImages.add(image);
                                        }
                                        if (selectImages.size() > 0) {
                                            okNums.setText("确定");
                                            okNums.setBackgroundResource(R.drawable.ok_ed);
                                        } else {
                                            okNums.setText("确定");
                                            okNums.setBackgroundResource(R.drawable.ok_cancel);
                                        }
                                        break;
                                    case Constants.MULTIPLE:
                                        if (selectImages.contains(image)) {
                                            selectImages.remove(image);
                                        } else {
                                            selectImages.add(image);
                                        }
                                        if (selectImages.size() > 0) {
                                            okNums.setText(selectImages.size() + " 确定");
                                            okNums.setBackgroundResource(R.drawable.ok_ed);
                                        } else {
                                            okNums.setText("确定");
                                            okNums.setBackgroundResource(R.drawable.ok_cancel);
                                        }
                                        break;
                                    default:
                                }
                                imagesAdapter.setmSelectImages(selectImages);
                            }

                            @Override
                            public void onTakeCamera() {
                                takeCamera();
                            }
                        });
                        if (folderAdapter == null) {
                            folderAdapter = new FolderAdapter(mFolders);
                            recyclerFolders.setAdapter(folderAdapter);
                        } else {
                            folderAdapter.notifyDataSetChanged();
                        }
                        folderAdapter.setOnFolderItemClickListener(new FolderAdapter.OnFolderItemClickListener() {
                            @Override
                            public void onFolderClick(Folder folder) {
                                imagesAdapter.refresh(folder.getImgs());
                                if (folderIsOpening) {
                                    closeFolder();
                                }
                            }
                        });
                    }
                });
            }
        });
    }


    private void startFragment(List<Image> images, int position) {
        photoFragment = PhotoFragment.newInstance(images, position);
        getSupportFragmentManager().beginTransaction().add(R.id.photoview_container, photoFragment).show(photoFragment).commit();
        fragmentContainer.setVisibility(View.VISIBLE);
        photoFragment.setmSelectImages(selectImages);
        photoFragment.setOnImageSelectListener(new PhotoFragment.OnImageSelectListener() {
            @Override
            public void select(Image image, boolean flag) {
                switch (curGalleryMode) {
                    case Constants.MULTIPLE:
                        if (selectImages.size() > 0) {
                            okNums.setText(selectImages.size() + " 确定");
                            okNums.setBackgroundResource(R.drawable.ok_ed);
                        } else {
                            okNums.setText("确定");
                            okNums.setBackgroundResource(R.drawable.ok_cancel);
                        }
                        if (imagesAdapter != null) {
                            imagesAdapter.setmSelectImages(selectImages);
                            Log.e("selectImg", selectImages.size() + "--");
                        }
                        break;
                    case Constants.SINGLE:
                        okNums.setText("确定");
                        if (selectImages.size() > 0) {
                            okNums.setBackgroundResource(R.drawable.ok_ed);
                        } else {
                            okNums.setBackgroundResource(R.drawable.ok_cancel);
                        }
                        if (imagesAdapter != null) {
                            imagesAdapter.setmSelectImages(selectImages);
                            Log.e("selectImg", selectImages.size() + "--");
                        }
                        break;
                    default:
                }
            }

            @Override
            public void confirm() {
                setIntentOk();
            }
        });
    }

    private void closeFolder() {
        folderViewBg.setVisibility(View.GONE);
        ObjectAnimator closeAnimator = ObjectAnimator.ofFloat(recyclerFolders, "translationY", 0, recyclerFolders.getHeight()).setDuration(300);
        closeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                recyclerFolders.setVisibility(View.GONE);
            }
        });
        closeAnimator.start();
        folderIsOpening = false;
    }

    private void openFolder() {
        folderViewBg.setVisibility(View.VISIBLE);
        ObjectAnimator openAnimator = ObjectAnimator.ofFloat(recyclerFolders, "translationY", recyclerFolders.getHeight(), 0).setDuration(300);
        openAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                recyclerFolders.setVisibility(View.VISIBLE);
            }
        });
        openAnimator.start();
        folderIsOpening = true;
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && photoFragment != null && !photoFragment.isHidden()) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            getSupportFragmentManager().beginTransaction().hide(photoFragment).commit();
//            fragmentContainer.setVisibility(View.GONE);
//            return true;
//        }
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && folderIsOpening) {
//            closeFolder();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        if (photoFragment != null && !photoFragment.isHidden()) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getSupportFragmentManager().beginTransaction().hide(photoFragment).commit();
            fragmentContainer.setVisibility(View.GONE);
            return;
        }
        if (folderIsOpening) {
            closeFolder();
        }
        super.onBackPressed();
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#f54548"));
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

    private void setIntentOk() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("images", (ArrayList<? extends Parcelable>) selectImages);
        setResult(RESULT_OK, intent);
        finish();
    }

    File tempFile;

    private void takeCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(imageFolderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            tempFile = new File(imageFolderPath, System.currentTimeMillis() + ".png");
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            // 在Android7.0+上和7.0以下的区别
            if (currentapiVersion < 24) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, Constants.PHOTO_REQUEST_CAMERA);
            } else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID
                        + ".imagefileprovider", tempFile));
                startActivityForResult(intent, Constants.PHOTO_REQUEST_CAMERA);
            }
        } else {
            Toast.makeText(this, "没有sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.PHOTO_REQUEST_CAMERA:
                    ImageModel.updateFolderImgs(mFolders, tempFile.getPath());
                    folderAdapter.notifyDataSetChanged();
                    imagesAdapter.notifyDataSetChanged();
                    Log.e("img_url", tempFile.getPath() + "--" + mFolders.size());
                    break;
                default:
            }
        }
    }
}
