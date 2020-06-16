package com.example.zhmkaohe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhmkaohe.R;
import com.example.zhmkaohe.bean.RequestData;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<RequestData.ResultBean.DataBean> data;

    public ListAdapter(Context context, List<RequestData.ResultBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        listViewHolder.tv_title.setText(data.get(position).getTitle());
        if (data.get(position).getThumbnail_pic_s02() != null) {
            Glide.with(context).load(data.get(position).getThumbnail_pic_s02()).into(listViewHolder.img2);
        }
        if (data.get(position).getThumbnail_pic_s() != null) {
            Glide.with(context).load(data.get(position).getThumbnail_pic_s()).into(listViewHolder.img1);
        }
        if (data.get(position).getThumbnail_pic_s03() != null) {
            Glide.with(context).load(data.get(position).getThumbnail_pic_s03()).into(listViewHolder.img3);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_title;
        private final ImageView img1;
        private final ImageView img2;
        private final ImageView img3;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);

        }
    }
}
