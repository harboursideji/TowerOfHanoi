package com.jixin.towerofhanoi.RecordDBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jixin.towerofhanoi.Record;

public class RecordDBHelper extends SQLiteOpenHelper {
    public final static String DBName="Record.db";

    public RecordDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public RecordDBHelper(Context context){
        super(context,DBName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists UserRecord(id integer primary key autoincrement ,name text,grade text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
