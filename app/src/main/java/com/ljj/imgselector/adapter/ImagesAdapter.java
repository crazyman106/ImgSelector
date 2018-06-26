package com.ljj.imgselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ljj.imgselector.R;
import com.ljj.imgselector.bean.Image;
import com.ljj.imgselector.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunjie on 2017/11/23 0023.
 * @description
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<Image> images;
    private Context context;
    //保存选中的图片
    private List<Image> mSelectImages = new ArrayList<>();
    private int camearMode = -1;

    public void setCamearMode(int camearMode) {
        this.camearMode = camearMode;
    }

    public ImagesAdapter(List<Image> images) {
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_view_item_img, parent, false));
    }

    public void refresh(List<Image> data) {
        images = data;
        notifyDataSetChanged();
    }

    public void setmSelectImages(List<Image> images) {
        this.mSelectImages = images;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (camearMode == Constants.TAKE_CAMERA) {
            if (position == 0) {
                Glide.with(context).load(R.mipmap.take_photo).into(holder.imageView);
                holder.imgSelect.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onTakeCamera();
                    }
                });
            } else {
                fillData2View(holder, position - 1);
            }
        } else {
            fillData2View(holder, position);
        }
    }

    private void fillData2View(final ViewHolder holder, final int position) {
        Log.e("imagePath", images.get(position).getPath());
        Glide.with(context).load(new File(images.get(position).getPath())).into(holder.imageView);
        holder.imgSelect.setVisibility(View.VISIBLE);
        setSelect(holder, position);
        holder.imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemCheck(images.get(position));
                }
                setSelect(holder, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(images, position);
                }
            }
        });
    }

    private void setSelect(ViewHolder holder, int position) {
        if (mSelectImages.contains(images.get(position))) {
            holder.imgSelect.setImageResource(R.drawable.select_ok);
        } else {
            holder.imgSelect.setImageResource(R.drawable.select_cancel);
        }
    }

    @Override
    public int getItemCount() {
        if (camearMode == Constants.TAKE_CAMERA) {
            return images != null ? images.size() + 1 : 1;
        } else {
            return images != null ? images.size() : 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSelect;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
            imgSelect = itemView.findViewById(R.id.item_img_selector);
        }
    }

    private OnImageItemClickListener listener;

    public void setOnImageItemClickListener(OnImageItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnImageItemClickListener {
        void onClick(List<Image> images, int position);

        void onItemCheck(Image image);

        void onTakeCamera();
    }
}
