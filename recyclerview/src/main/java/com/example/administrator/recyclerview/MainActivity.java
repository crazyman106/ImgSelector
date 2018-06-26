package com.example.administrator.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Integer> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MySnapHelper helper = new MySnapHelper(3);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new RecyclerViewAdapter());
    }

    private void initDatas() {
        datas = new ArrayList<>();
        datas.add(R.mipmap.a);
        datas.add(R.mipmap.b);
        datas.add(R.mipmap.c);
        datas.add(R.mipmap.d);
        datas.add(R.mipmap.e);
        datas.add(R.mipmap.f);
        datas.add(R.mipmap.g);
        datas.add(R.mipmap.h);
        datas.add(R.mipmap.a);
        datas.add(R.mipmap.b);
        datas.add(R.mipmap.c);
        datas.add(R.mipmap.d);
        datas.add(R.mipmap.e);
        datas.add(R.mipmap.f);
        datas.add(R.mipmap.g);
        datas.add(R.mipmap.h);
    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.imageView.setImageResource(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }
}
