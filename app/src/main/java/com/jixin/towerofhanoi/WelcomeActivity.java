package com.jixin.towerofhanoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.logging.Level;

public class WelcomeActivity extends AppCompatActivity {
    private Button btn_startGame;
    private EditText et_userId;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeactivity);
        initView();
    }

    public void initView(){
        sp=this.getSharedPreferences(Constants.SharedPreferenceName,MODE_PRIVATE);
        editor=sp.edit();
        btn_startGame=findViewById(R.id.btn_enter);
        et_userId=findViewById(R.id.et_userId);
        btn_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et_userId.getText().toString())){
                    String userId=et_userId.getText().toString();
                    editor.putString(Constants.UserId,userId);
                    editor.apply();
                }else{
                    editor.putString(Constants.UserId,Constants.Default_Id);
                    editor.apply();
                }
                Intent chooseLevel=new Intent(WelcomeActivity.this,LevelChooseActivity.class);
                startActivity(chooseLevel);
            }
        });

    }
}
