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

    private final static String ADD_SQL = "insert into UserRecord(name,grade) values(?,?)";
    private final static String DELETE_SQL = "delete from UserRecord where name=?";
    private final static String FIND_SQL = "select * from UserRecord where name=?";
    private final static String FIND_ALL_SQL = "select * from UserRecord";
    private final static String UPDATE_SQL = "update UserRecord set grade=? where name=?";

    public RecordDao(Context context){
        recordDBHelper=new RecordDBHelper(context);
        database=recordDBHelper.getWritableDatabase();
    }

    public void add(String name,String grade){
        if(!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        database.execSQL(ADD_SQL,new Object[]{name,grade});
        database.close();
    }

    public void delete(String name){
        if(!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        database.execSQL(DELETE_SQL,new Object[]{name});
        database.close();
    }

    public boolean find(String name){
        boolean result=false;
        if (!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        Cursor cursor=database.rawQuery(FIND_SQL,new String[]{name});
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
        Cursor cursor=database.rawQuery(FIND_ALL_SQL,null);
        while (cursor.moveToNext()){
            Record record=new Record();
            record.setId(cursor.getString(cursor.getColumnIndex("name")));
            record.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            list.add(record);
        }
        cursor.close();
        database.close();
        return list;
    }

    public void update(String name,String grade){
        if (!database.isOpen()){
            database=recordDBHelper.getWritableDatabase();
        }
        database.execSQL(UPDATE_SQL,new Object[]{grade,name});
        database.close();
    }

}
