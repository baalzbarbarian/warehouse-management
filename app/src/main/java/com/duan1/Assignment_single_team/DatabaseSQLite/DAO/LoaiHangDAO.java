package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.duan1.Assignment_single_team.mModel.mNcc;

import java.util.ArrayList;
import java.util.List;

public class LoaiHangDAO extends DatabaseHelper {


    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "LoaiHang";
    public static final String SQL_LOAIHANG = "CREATE TABLE LoaiHang (" +
            "maloaihang text primary key," +
            "tenloaihang text," +
            "vitri text);";
    public static final String TAG = "LoaiHang";

    public LoaiHangDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inserLoaiHang(mLoaiHang m){
        ContentValues values = new ContentValues();
        values.put("maloaihang", m.getMaloaihang());
        values.put("tenloaihang", m.getTenloaihang());
        values.put("vitri", m.getVitri());

        try{
            if (db.insert(TABLE_NAME, null, values) == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public List<mLoaiHang> getAllLoaiHang(){
        List<mLoaiHang> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mLoaiHang m = new mLoaiHang();
            m.setMaloaihang(cursor.getString(0));
            m.setTenloaihang(cursor.getString(1));
            m.setVitri(cursor.getString(2));
            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getViTriByMaLoaiHang(String maLoaiHang){
        String sql = "Select vitri from "+TABLE_NAME+" where maloaihang like '"+maLoaiHang+"'";
        Cursor cursor = db.rawQuery(sql, null);
        String vitri = "";
        if (cursor != null && cursor.moveToFirst()){
            vitri = cursor.getString(0);
        }
        return vitri;
    }

    public int deleteLoaiHang(mLoaiHang m){
        int result = db.delete(TABLE_NAME, "maloaihang =?", new String[]{m.getMaloaihang()});
        if (result == 0){
            return -1;
        }

        return 1;
    }

}
