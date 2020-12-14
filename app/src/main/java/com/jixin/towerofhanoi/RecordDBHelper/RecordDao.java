package com.jixin.towerofhanoi.RecordDBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jixin.towerofhanoi.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    private final RecordDBHelper recordDBHelper;
    private SQLiteDatabase database;

    public RecordDao(Context context){
        recordDBHelper=new RecordDBHelper(context);
        database=recordDBHelper.getWritableDatabase();
    }

    public void add(String name,String grade){
        if(!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        String sql="insert into UserRecord(name,grade) values(?,?)";
        database.execSQL(sql,new Object[]{name,grade});
        database.close();
    }

    public void delete(String name){
        if(!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        String sql="delete from UserRecord where name=?";
        database.execSQL(sql,new Object[]{name});
        database.close();
    }

    public boolean find(String name){
        boolean result=false;
        if (!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        String sql="select * from UserRecord where name=?";
        Cursor cursor=database.rawQuery(sql,new String[]{name});
        if(cursor.moveToFirst()){
            cursor.close();
            result=true;
        }
        database.close();
        return result;
    }

    public List<Record> findAll(){
        List<Record> list=new ArrayList<>();
        if(!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        Cursor cursor=database.rawQuery("select * from UserRecord",null);
        while (cursor.moveToNext()){
            Record record=new Record();
            record.setId(cursor.getString(cursor.getColumnIndex("name")));
            record.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            list.add(record);
        }
        database.close();
        return list;
    }

    public void update(String name,String grade){
        if (!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        String sql="update UserRecord set grade=? where name=?";
        database.execSQL(sql,new Object[]{grade,name});
        database.close();
    }

}
