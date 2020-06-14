package com.example.zhmkaohe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhmkaohe.ListActivity;
import com.example.zhmkaohe.R;
import com.example.zhmkaohe.bean.FirstInfo;

import java.util.List;

/*import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/

public class EquipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> data;
    private List<String> list;
    private final LayoutInflater inflater;
    private ViewGroup.LayoutParams layoutParams;
    private LinearLayout.LayoutParams layoutParams1;

    public EquipAdapter(Context context, List<String> data,List<String> list) {
        this.context = context;
        this.data = data;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.equip_item, parent,false);
        EquipViewHolder equipViewHolder = new EquipViewHolder(view);
        return equipViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EquipViewHolder equipViewHolder = (EquipViewHolder) holder;

        equipViewHolder.tv_title.setText(list.get(position));
        equipViewHolder.tv_content.setText(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class EquipViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_title;
        private final TextView tv_content;
        private final RelativeLayout ll_item;
        public EquipViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
}
