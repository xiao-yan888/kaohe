package com.example.zhmkaohe.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhmkaohe.R;
import com.example.zhmkaohe.adapter.FirstAdapter;
import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.bean.FirstInfo;
import com.example.zhmkaohe.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cn.hnshangyu.testgreendao.greendao.DaoSession;
import cn.hnshangyu.testgreendao.greendao.FirstInfoDao;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class FirstFragment extends Fragment {

    private RecyclerView mRv;
    private List<FirstInfo> list = new ArrayList<>();
    private FirstAdapter firstAdapter;
    private List<FirstInfo> firstInfos;
    private List<FirstInfo> Infos = new ArrayList<>();
    private FirstInfoDao firstInfoDao;
    private DaoSession daoSession;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment1, container, false);
        mRv = inflate.findViewById(R.id.rv);
        daoSession = MyApp.getDaoSession();
        firstInfoDao = daoSession.getFirstInfoDao();
        //初始化数据
        initview();
        // initdata();
        return inflate;
    }

    //展示数据列表
    private void initdata(final List<FirstInfo> date) {
        //SharedPreferencesUtils.setParam(getContext(),"clientlist",true);
        //展示列表adapter
        firstAdapter = new FirstAdapter(getContext(), date);
        mRv.setAdapter(firstAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 6);
        //显示首页页面布局显示 第一行是2:1 第二行独占一行 第三行1:1
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //计算在哪个position时要显示1列数据，即columnCount / 1列 = 4格，即1列数据占满4格
                if (position == 0) {
                    return 4;
                } else if (position == 1) {
                    return 2;
                } else if (position == date.size() - 3) {
                    return 6;
                } else if (position == 3) {
                    return 3;
                } else if (position == 4) {
                    return 3;
                }
                return 1;


            }
        });
        mRv.setLayoutManager(gridLayoutManager);
        //拖拽排序刷新列表
        new ItemTouchHelper(mCallback).attachToRecyclerView(mRv);


    }

    //拖拽排序 刷新数据库
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
        //drag方向，长按振动后可以拖动0//swip方向，实现滑动删除

        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            //firstInfos.clear();
            //排序之后删除数据库重新添加
            firstInfoDao.deleteAll();
            Infos.clear();
            for (int i = 0; i < list.size(); i++) {
                FirstInfo info = new FirstInfo();
                info.setTitle(list.get(i).getTitle());
                info.setContent(list.get(i).getContent());
                info.setType(list.get(i).getType());
                Infos.add(info);
            }
            //添加数据库
            firstInfoDao.insertInTx(Infos);
            //刷新列表
            firstAdapter.notifyItemMoved(fromPosition, toPosition);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            list.remove(position);
            firstAdapter.notifyItemRemoved(position);
        }

    };


    //初始化数据 添加数据库
    private void initview() {
        //firstInfoDao.deleteAll();
        //查询数据库数据
        firstInfos = firstInfoDao.queryBuilder().build().list();
        if (firstInfos.size() == 0) {
            //数据为0 初始化数据
            FirstInfo firstInfo = new FirstInfo();
            firstInfo.setTitle("优惠券");
            firstInfo.setContent("精彩享不停");
            firstInfo.setType(1);
            FirstInfo firstInfo1 = new FirstInfo();
            firstInfo1.setTitle("积分交易");
            firstInfo1.setContent("享乐无限");
            firstInfo1.setType(2);
            FirstInfo firstInfo2 = new FirstInfo();
            firstInfo2.setTitle("收银台");
            firstInfo2.setContent("安全便捷");
            firstInfo2.setType(3);
            FirstInfo firstInfo3 = new FirstInfo();
            firstInfo3.setTitle("分期交易");
            firstInfo3.setContent("轻松生活");
            firstInfo3.setType(4);
            FirstInfo firstInfo4 = new FirstInfo();
            firstInfo4.setTitle("商户服务");
            firstInfo4.setContent("无微不至");
            firstInfo4.setType(5);
            list.add(firstInfo);
            list.add(firstInfo1);
            list.add(firstInfo2);
            list.add(firstInfo3);
            list.add(firstInfo4);
            // daoUtil.insertMultInfo(this.list);
            //添加数据库
            for (int i = 0; i < list.size(); i++) {
                firstInfoDao.insert(list.get(i));
            }
            //展示列表
            initdata(list);
        } else {
            // 不等于0 直接显示数据库列表
            list = firstInfos;
            initdata(list);
        }
    }
}
