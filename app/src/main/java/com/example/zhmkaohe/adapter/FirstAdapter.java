package com.example.zhmkaohe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/*import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/

import com.example.zhmkaohe.ListActivity;
import com.example.zhmkaohe.R;
import com.example.zhmkaohe.bean.FirstInfo;

import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class FirstAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FirstInfo> data;
    private LinearLayout.LayoutParams layoutParams1;

    public FirstAdapter(Context context, List<FirstInfo> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.conduct_item, parent, false);
        FirstViewHolder firstViewHolder = new FirstViewHolder(view);
        return firstViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
        FirstInfo firstInfo = data.get(position);
        firstViewHolder.tv_title.setText(firstInfo.getTitle());
        firstViewHolder.tv_content.setText(firstInfo.getContent());
        //每个条目显示1/3高
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int heightPixels = (metrics.heightPixels) * 2 / 3;
        layoutParams1 = (LinearLayout.LayoutParams) firstViewHolder.ll_item.getLayoutParams();
        layoutParams1.height = heightPixels / 3;
        firstViewHolder.ll_item.setLayoutParams(layoutParams1);
        //点击条目跳转到其他页面获取网络数据
        firstViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListActivity.class);
                context.startActivity(intent);
            }
        });
        //firstViewHolder.ll_item.setLayoutParams(layoutParams1);
        //根据type 展示不同的背景颜色
        if (firstInfo.getType() == 1) {
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback1));
        } else if (firstInfo.getType() == 2) {
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback2));
        } else if (firstInfo.getType() == 3) {
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback3));
        } else if (firstInfo.getType() == 4) {
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback4));
        } else if (firstInfo.getType() == 5) {
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback5));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class FirstViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_title;
        private final TextView tv_content;
        private final LinearLayout ll_item;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
}
