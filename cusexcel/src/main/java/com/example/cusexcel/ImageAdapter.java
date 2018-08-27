package com.example.cusexcel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/26.
 */

public class ImageAdapter extends BaseAdapter {

    private List<Integer> imgRess = new ArrayList<>();
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return imgRess.get(position);
    }

    @Override
    public long getItemId(int position) {
        return imgRess.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spanner, parent, false); //加载布局
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView;
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(imgRess.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
    }

}
