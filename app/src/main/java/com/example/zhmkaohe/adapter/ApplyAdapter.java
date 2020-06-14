package com.example.zhmkaohe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhmkaohe.R;
import com.example.zhmkaohe.bean.AppInfo;

import java.util.List;

/*import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/

public class ApplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<AppInfo> list;
    private final LayoutInflater inflater;
    private ViewGroup.LayoutParams layoutParams;
    private LinearLayout.LayoutParams layoutParams1;

    public ApplyAdapter(Context context,  List<AppInfo> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.equip_item, parent,false);
        ApplyViewHolder applyViewHolder = new ApplyViewHolder(view);
        return applyViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ApplyViewHolder applyViewHolder = (ApplyViewHolder) holder;

        applyViewHolder.tv_title.setText(list.get(position).getName());
        applyViewHolder.tv_content.setText(list.get(position).getVersionName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ApplyViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_title;
        private final TextView tv_content;
        private final RelativeLayout ll_item;
        public ApplyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
}
