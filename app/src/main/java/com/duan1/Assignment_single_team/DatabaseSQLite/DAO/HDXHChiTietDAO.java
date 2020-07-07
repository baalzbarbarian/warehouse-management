package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mGioHang;

import java.util.ArrayList;
import java.util.List;

public class HDXHChiTietDAO extends DatabaseHelper {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public HDXHChiTietDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static final String TABLE_NAME = "HDXHChiTiet";
    public static final String SQL_HDXHCHITIET = "CREATE TABLE HDXHChiTiet (" +
            "magiohang integer primary key autoincrement," +
            "mahdxh text, " +
            "mahang text, " +
            "giahang double," +
            "soluonghang integer);";
    public static final String TAG = "HDXHChiTiet";


    public int inserHDXHChiTiet(mGioHang m){
        ContentValues values = new ContentValues();
        values.put("mahdxh", m.getMahdxh());
        values.put("mahang", m.getMahang());
        values.put("giahang", m.getGiahang());
        values.put("soluonghang", m.getSoluong());

        long result = 0;
        try{
            result = db.insert(TABLE_NAME, null, values);
            if (result == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        Log.e(TAG, "inserHDXHChiTiet: "+result);
        return 1;
    }

    public List<mGioHang> getHDXHChiTietByMaHD(String mahdxh){
        List<mGioHang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE mahdxh LIKE '"+mahdxh+"'", null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                mGioHang m = new mGioHang();
                m.setMahang(cursor.getString(2));
                m.setGiahang(cursor.getDouble(3));
                m.setSoluong(cursor.getInt(4));
                list.add(m);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return list;
    }

    public boolean checkHdExists(String mahdxh){
        Cursor cursor;
        try{
            cursor = db.query(TABLE_NAME, null,
                    "mahdxh =?",
                    new String[]{mahdxh}, null, null, null);
            if (cursor.getCount() > 0){
                cursor.close();
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "checkExists: "+e.toString());
        }

        return true;
    }

    public int deleteHDXHCTByMaHDXH(String mahdxh){
        int result = db.delete(TABLE_NAME, "mahdxh =?", new String[]{mahdxh});
        if(result < 1){
            return -1;
        }
        return 1;
    }

}
