package com.jixin.towerofhanoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LevelChooseActivity extends AppCompatActivity {

    private RecyclerView chooseLevelView;
    private LevelChooseAdapter levelChooseAdapter;
    private Context mContext;
    private List<String> levelList;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choose);
        mContext=LevelChooseActivity.this;
        initData();
        initView();
    }

    private void initView(){
        chooseLevelView=findViewById(R.id.rv_chooseLevel);
        gridLayoutManager=new GridLayoutManager(mContext,Constants.Level_Choose_Span);
        levelChooseAdapter=new LevelChooseAdapter(mContext,levelList);
        chooseLevelView.setAdapter(levelChooseAdapter);
        chooseLevelView.setLayoutManager(gridLayoutManager);
    }

    private void initData(){
        StringBuilder levelBuilder;
        if(levelList==null){
            levelList=new ArrayList<>();
        }
        for(int i=1;i<16;i++){
            levelBuilder=new StringBuilder();
            levelBuilder.append(getResources().getString(R.string.level)).append(" "+i);
            levelList.add(levelBuilder.toString());
        }
    }
}
