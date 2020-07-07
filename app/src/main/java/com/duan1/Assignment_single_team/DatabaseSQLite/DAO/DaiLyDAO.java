package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mDaiLy;

import java.util.ArrayList;
import java.util.List;

public class DaiLyDAO extends DatabaseHelper {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "DaiLy";
    public static final String SQL_DAI_LY = "CREATE TABLE DaiLy (" +
            "madaily text primary key," +
            "tendaily text, " +
            "diachi text, " +
            "phone text);";
    public static final String TAG = "DaiLyDAO";

    public DaiLyDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inserDaiLy(mDaiLy m){
        ContentValues values = new ContentValues();
        values.put("madaily", m.getMadaily());
        values.put("tendaily", m.getTendaily());
        values.put("diachi", m.getDiachi());
        values.put("phone", m.getPhone());

        try{
            if (db.insert(TABLE_NAME, null, values) == -1){
                Log.e(TAG, "inserDaiLy: "+db.insert(TABLE_NAME, null, values));
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public boolean checkDailyExists(String madaily){
        Cursor cursor = null;
        try{
            cursor = db.query(TABLE_NAME, null,
                    "madaily =?",
                    new String[]{madaily}, null, null, null);

        }catch (Exception e){
            Log.d(TAG, "checkExists: "+e.toString());
        }

        if (cursor.getCount() > 0){
            cursor.close();
            return false;
        }
        Log.e(TAG, "checkExists: "+cursor.getCount());
        return true;
    }

    public List<mDaiLy> getAllDaiLy(){
        List<mDaiLy> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mDaiLy m = new mDaiLy();
            m.setMadaily(cursor.getString(0));
            m.setTendaily(cursor.getString(1));
            m.setDiachi(cursor.getString(2));
            m.setPhone(cursor.getString(3));

            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int updateDaily(mDaiLy m){
        ContentValues values = new ContentValues();
        values.put("madaily", m.getMadaily());
        values.put("tendaily", m.getTendaily());
        values.put("diachi", m.getDiachi());
        values.put("phone", m.getPhone());

        int result = db.update(TABLE_NAME, values, "madaily =?", new String[]{m.getMadaily()});
        Log.e(TAG, "updateEmployee: "+m.getMadaily());
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int deleteDaily(mDaiLy mDaiLy){
        int result = db.delete(TABLE_NAME, "madaily =?", new String[]{mDaiLy.getMadaily()});
        if (result == 0){
            return -1;
        }

        return 1;
    }
}
