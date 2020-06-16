package com.example.clientapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.adapter.DataAdapter;
import com.example.clientapp.bean.DataInfo;
import com.example.clientapp.util.MyApp;

import java.util.ArrayList;
import java.util.List;

import cn.hnshangyu.testgreendao.greendao.DaoSession;
import cn.hnshangyu.testgreendao.greendao.DataInfoDao;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView mRv;
    private DataInfoDao dataInfoDao;
    /**
     * 请输入
     */
    private EditText mName;
    /**
     * 删除
     */
    private EditText mEdit;
    /**
     * 添加
     */
    private Button mBut;
    /**
     * 查询
     */
    private Button mSelect;
    /**
     * 删除
     */
    private Button mDelete;
    /**
     * 修改
     */
    private Button mUpdate;
    /**
     * 全部删除
     */
    private Button mAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DaoSession daoSession = MyApp.getDaoSession();
        dataInfoDao = daoSession.getDataInfoDao();
        initView();
        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initView() {

        mRv = (RecyclerView) findViewById(R.id.rv);
        mName = (EditText) findViewById(R.id.name);
        mEdit = (EditText) findViewById(R.id.edit);
        mBut = (Button) findViewById(R.id.but);
        mBut.setOnClickListener(this);
        mSelect = (Button) findViewById(R.id.select);
        mSelect.setOnClickListener(this);
        mDelete = (Button) findViewById(R.id.delete);
        mDelete.setOnClickListener(this);
        mUpdate = (Button) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mAll = (Button) findViewById(R.id.all);
        mAll.setOnClickListener(this);

    }


    private void showdata(List<DataInfo> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        DataAdapter dataAdapter = new DataAdapter(this, list);
        mRv.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.but:
                insert();
                break;
            case R.id.select:
                cha();
                break;
            case R.id.delete:
                shachu();
                break;
            case R.id.update:
                upda();
                break;
            case R.id.all:
                all();
                break;
        }
    }

    //修改
    private void upda() {

//根据id进行修改的
        String id = mEdit.getText().toString().trim();

        long ids = Long.parseLong(id);

        List<DataInfo> list = dataInfoDao.queryBuilder().build().list();

        for (int i = 0; i < list.size(); i++) {
            Long id1 = list.get(i).getId();
            Log.d("aa", "select: " + id1);

            if (ids == id1) {
                //重新或许一遍值
                String s = mName.getText().toString();
                if (s.isEmpty()) {
                    Toast.makeText(Main2Activity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    DataInfo dataInfo = new DataInfo(ids, s, "21", "女", "sd","1","2","3","4","5");
                    dataInfoDao.update(dataInfo);
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Main2Activity.this, "空的", Toast.LENGTH_SHORT).show();
            }
        }
        cha();

    }

    //删除
    private void shachu() {
        //获取id值
        String id = mEdit.getText().toString().trim();
        int a = Integer.parseInt(id);
        List<DataInfo> list = dataInfoDao.queryBuilder().build().list();
        for (int i = 0; i <list.size() ; i++) {
            if (a==i+1){
                dataInfoDao.delete(list.get(i));
                Toast.makeText(this, "删除成功~", Toast.LENGTH_SHORT).show();
            }
        }
        //dataInfoDao.queryBuilder().where(DataInfoDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();

        cha();
    }

    public void insert() {
        String name = mName.getText().toString();
        //判断非空
        if (name.isEmpty()) {
            Toast.makeText(Main2Activity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //添加
            DataInfo dataInfo = new DataInfo(null, name, "21", "女", "sd","1","2","3","4","5");
            dataInfoDao.insert(dataInfo);
            //查询的方法
            cha();
        }
    }

    //查询并添加到适配器当中展示
    private void cha() {
        List<DataInfo> list = dataInfoDao.queryBuilder().build().list();
        showdata(list);

    }

    //全部删除
    private void all() {
        dataInfoDao.deleteAll();
        cha();
    }

}
