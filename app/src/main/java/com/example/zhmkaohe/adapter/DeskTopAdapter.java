package com.example.zhmkaohe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhmkaohe.R;
import com.example.zhmkaohe.bean.AppInfo;
import com.example.zhmkaohe.bean.FirstInfo;

import java.util.ArrayList;
import java.util.List;

public class DeskTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AppInfo> appInfos;
    private RelativeLayout.LayoutParams layoutParams1;

    public DeskTopAdapter(List<AppInfo> appInfos, Context context) {
        this.appInfos = appInfos;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.desktop_gridview_item, parent, false);
        DeskTopViewHolder deskTopViewHolder = new DeskTopViewHolder(view);
        return deskTopViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        DeskTopViewHolder deskTopViewHolder = (DeskTopViewHolder) holder;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int heightPixels = (metrics.heightPixels) * 2 / 3;
        layoutParams1 = (RelativeLayout.LayoutParams) deskTopViewHolder.ll_item.getLayoutParams();
        layoutParams1.height = heightPixels / 3;
        deskTopViewHolder.ll_item.setLayoutParams(layoutParams1);
        deskTopViewHolder.iv.setImageDrawable(appInfos.get(position).getIco());
        deskTopViewHolder.tv.setText(appInfos.get(position).getName());
        if (appInfos.get(position).getUpdate_flag()) {
            deskTopViewHolder.tv_geng.setVisibility(View.VISIBLE);
        } else {
            deskTopViewHolder.tv_geng.setVisibility(View.GONE);
        }
        deskTopViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(appInfos.get(position).getPackageName());
                if (intent != null) {
                    intent.putExtra("type", "110");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    class DeskTopViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv;
        private final TextView tv;
        private final TextView tv_geng;
        private final LinearLayout ll_item;

        public DeskTopViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_geng = itemView.findViewById(R.id.tv_geng);
        }
    }
}
