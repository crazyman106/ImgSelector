package com.ljj.imgselector.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljj.imgselector.R;
import com.ljj.imgselector.bean.Folder;
import com.ljj.imgselector.view.SquareImageView;

import java.io.File;
import java.util.List;

/**
 * @author lijunjie on 2017/11/23 0023.
 * @description
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private List<Folder> folders;
    private Context context;
    private int curSelectedFolder;

    public FolderAdapter(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(new File(folders.get(position).getImgs().get(0).getPath())).into(holder.imageView);
        holder.folderName.setText(folders.get(position).getName());
        holder.folderNums.setText(folders.get(position).getImgs().size() + "");
        if (position == curSelectedFolder) {
            holder.folderStatus.setImageResource(R.drawable.select_ok);
        } else {
            holder.folderStatus.setImageResource(R.drawable.select_cancel);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curSelectedFolder = holder.getAdapterPosition();
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onFolderClick(folders.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return folders != null ? folders.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SquareImageView imageView;
        TextView folderName, folderNums;
        ImageView folderStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_folder_img);
            folderName = itemView.findViewById(R.id.item_folder_name);
            folderNums = itemView.findViewById(R.id.item_folder_nums);
            folderStatus = itemView.findViewById(R.id.item_folder_status);
        }
    }

    OnFolderItemClickListener listener;

    public void setOnFolderItemClickListener(OnFolderItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFolderItemClickListener {
        void onFolderClick(Folder folder);
    }
}
