package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;

import com.duan1.Assignment_single_team.mModel.mHDXHChiTiet;
import com.duan1.Assignment_single_team.mModel.mTKDTTheoThang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonXHDAO extends DatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TAG = "HoaDonXH";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final String TABLE_NAME = "HoaDonXH";
    public static final String SQL_HOADONXUATHANG = "CREATE TABLE HoaDonXH (" +
            "mahoadon text primary key," +
            "ngaytaohoadon date," +
            "manv text," +
            "ghichu text," +
            "madaily text);";

    public HoaDonXHDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inserHoaDon(mHDXHChiTiet m){
        ContentValues values = new ContentValues();
        values.put("mahoadon", m.getMahoadon());
        values.put("ngaytaohoadon", sdf.format(m.getNgaytaohoadon()));
        values.put("manv", m.getManhanvien());
        values.put("ghichu", m.getGhichu());
        values.put("madaily", m.getMadaily());

        long result = 0;
        try{
            result = db.insert(TABLE_NAME, null, values);
            if (result == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        Log.e(TAG, "inserHoaDon: "+result);
        return 1;
    }

    public List<mHDXHChiTiet> getAllHoaDonXH(){
        List<mHDXHChiTiet> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" ORDER BY ngaytaohoadon DESC",null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                mHDXHChiTiet m = new mHDXHChiTiet();
                m.setMahoadon(cursor.getString(0));
                try {
                    m.setNgaytaohoadon(sdf.parse(cursor.getString(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                m.setManhanvien(cursor.getString(2));
                m.setGhichu(cursor.getString(3));
                m.setMadaily(cursor.getString(4));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean checkHdExists(String maNcc){
        Cursor cursor;
        try{
            cursor = db.query(TABLE_NAME, null,
                    "mahoadon =?",
                    new String[]{maNcc}, null, null, null);
            if (cursor.getCount() > 0){
                cursor.close();
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "checkExists: "+e.toString());
        }

        return true;
    }

    public List<mHDXHChiTiet> getAllHD(String date) {
        List<mHDXHChiTiet> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE ngaytaohoadon = "+"'"+date+"'";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mHDXHChiTiet m = new mHDXHChiTiet();
            m.setMahoadon(cursor.getString(0));
            try {
                m.setNgaytaohoadon(sdf.parse(cursor.getString(1)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            m.setManhanvien(cursor.getString(2));
            m.setGhichu(cursor.getString(3));
            m.setMadaily(cursor.getString(4));
            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<mHDXHChiTiet> getAllHDBeforeSvenDay(String dateCurrent, String dateBeforeSevenDay) {
        List<mHDXHChiTiet> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE ngaytaohoadon BETWEEN "+"'"+dateBeforeSevenDay+"' AND "+"'"+dateCurrent+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mHDXHChiTiet m = new mHDXHChiTiet();
            m.setMahoadon(cursor.getString(0));
            try {
                m.setNgaytaohoadon(sdf.parse(cursor.getString(1)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            m.setManhanvien(cursor.getString(2));
            m.setGhichu(cursor.getString(3));
            m.setMadaily(cursor.getString(4));
            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<mHDXHChiTiet> getAllHDInMonth(String dateCurrent) {
        List<mHDXHChiTiet> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE strftime('%m', ngaytaohoadon) = strftime('%m',"+"'"+dateCurrent+"')";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mHDXHChiTiet m = new mHDXHChiTiet();
            m.setMahoadon(cursor.getString(0));
            try {
                m.setNgaytaohoadon(sdf.parse(cursor.getString(1)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            m.setManhanvien(cursor.getString(2));
            m.setGhichu(cursor.getString(3));
            m.setMadaily(cursor.getString(4));
            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int deleteHD(mHDXHChiTiet m){
        int result = db.delete(TABLE_NAME, "mahoadon =?", new String[]{m.getMahoadon()});
        if (result == 0){
            return -1;
        }

        return 1;
    }



}
