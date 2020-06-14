package com.example.clientapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clientapp.R;
import com.example.clientapp.bean.DataInfo;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<DataInfo> list;
    private final LayoutInflater inflater;
    public DataAdapter(Context context, List<DataInfo> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.data_item, parent,false);
        DataViewHolder dataViewHolder = new DataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataViewHolder dataViewHolder= (DataViewHolder) holder;
        dataViewHolder.tv_title.setText(list.get(position).getName()+"  "+list.get(position).getAge()+"  "+list.get(position).getContent()+"  "+list.get(position).getSex());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class DataViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_title;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
