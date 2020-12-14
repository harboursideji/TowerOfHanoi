package com.jixin.towerofhanoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.jixin.towerofhanoi.RecordDBHelper.RecordDao;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    RecyclerView recordView;
    RecordAdapter recordAdapter;
    private List<Record> recordList;
    private RecordDao recordDao;


    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
    }

    private void initView(){
        initData();
        recordView=findViewById(R.id.view_record);
        recordView.setAdapter(recordAdapter);
        recordView.setLayoutManager(gridLayoutManager);
    }

    private void initData(){
        recordDao=new RecordDao(this);
        recordList=new ArrayList<>();
        recordList=recordDao.findAll();
        recordAdapter=new RecordAdapter(this,recordList);
        gridLayoutManager=new GridLayoutManager(this,1);
    }
}
