package com.example.clientapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clientapp.R;
import com.example.clientapp.bean.DataInfo;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<DataInfo> list;
    public DataAdapter(Context context, List<DataInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_item, parent,false);
        DataViewHolder dataViewHolder = new DataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataViewHolder dataViewHolder= (DataViewHolder) holder;
        dataViewHolder.tv_name.setText(list.get(position).getName());
        dataViewHolder.tv_age.setText(list.get(position).getAge());
        dataViewHolder.tv_sex.setText(list.get(position).getSex());
        dataViewHolder.tv_content.setText(list.get(position).getContent());
        dataViewHolder.tv_fu1.setText(list.get(position).getFuo());
        dataViewHolder.tv_fu2.setText(list.get(position).getFut());
        dataViewHolder.tv_fu3.setText(list.get(position).getFus());
        dataViewHolder.tv_fu4.setText(list.get(position).getFuf());
        dataViewHolder.tv_fu5.setText(list.get(position).getFufi());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class DataViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_name;
        private final TextView tv_age;
        private final TextView tv_sex;
        private final TextView tv_content;
        private final TextView tv_fu1;
        private final TextView tv_fu2;
        private final TextView tv_fu3;
        private final TextView tv_fu4;
        private final TextView tv_fu5;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_sex = itemView.findViewById(R.id.tv_sex);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_fu1 = itemView.findViewById(R.id.tv_fu1);
            tv_fu2 = itemView.findViewById(R.id.tv_fu2);
            tv_fu3 = itemView.findViewById(R.id.tv_fu3);
            tv_fu4 = itemView.findViewById(R.id.tv_fu4);
            tv_fu5 = itemView.findViewById(R.id.tv_fu5);
        }
    }
}
