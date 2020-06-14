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
    private final LayoutInflater inflater;
    private ViewGroup.LayoutParams layoutParams;
    private LinearLayout.LayoutParams layoutParams1;
    private View view;

    public FirstAdapter(Context context, List<FirstInfo> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.conduct_item, parent,false);
        FirstViewHolder firstViewHolder = new FirstViewHolder(view);
        return firstViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
        FirstInfo firstInfo = data.get(position);
        firstViewHolder.tv_title.setText(firstInfo.getTitle());
        firstViewHolder.tv_content.setText(firstInfo.getContent());
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int heightPixels = (metrics.heightPixels)*2/3;
        layoutParams1 = (LinearLayout.LayoutParams) firstViewHolder.ll_item.getLayoutParams();
        layoutParams1.height=heightPixels/3;
        firstViewHolder.ll_item.setLayoutParams(layoutParams1);
       // int adapterPosition = firstViewHolder.getAdapterPosition();
       /* if (position==0) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int widthPixels = metrics.widthPixels;
            layoutParams1 = (LinearLayout.LayoutParams) firstViewHolder.ll_item.getLayoutParams();
            layoutParams1.width=widthPixels/3;
            firstViewHolder.ll_item.setLayoutParams(layoutParams1);
        }else if (position==1){
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int widthPixels = metrics.widthPixels;
            layoutParams1 = (LinearLayout.LayoutParams) firstViewHolder.ll_item.getLayoutParams();
            layoutParams1.width=widthPixels*2/3;
            firstViewHolder.ll_item.setLayoutParams(layoutParams1);
        }*/
        firstViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListActivity.class);
                context.startActivity(intent);
            }
        });
        //firstViewHolder.ll_item.setLayoutParams(layoutParams1);
        if (position==0){
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback1));
        }else if (position==1){
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback2));
        }else if (position==2){
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback3));
        }else if (position==3){
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback4));
        }else if (position==4){
            firstViewHolder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.fback5));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    class FirstViewHolder extends RecyclerView.ViewHolder{
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
