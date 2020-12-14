package com.jixin.towerofhanoi.Application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        LeakCanary.install(this);
    }

}
