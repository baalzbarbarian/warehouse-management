package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mNcc;

import java.util.ArrayList;
import java.util.List;

public class NccDAO extends DatabaseHelper{
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "NhaCungCap";
    public static final String SQL_NCC = "CREATE TABLE NhaCungCap (" +
            "mancc text primary key," +
            "tenncc text, " +
            "diachincc text, " +
            "phonencc text, " +
            "mahangncc text, " +
            "logoncc blob);";
    public static final String TAG = "NccDAO";

    public NccDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public int inserNcc(mNcc m){
        ContentValues values = new ContentValues();
        values.put("mancc", m.getMancc());
        values.put("tenncc", m.getTenncc());
        values.put("diachincc", m.getDiachi());
        values.put("phonencc", m.getPhonencc());
        values.put("mahangncc", m.getMahangncc());
        values.put("logoncc", m.getLogoncc());

        try{
            if (db.insert(TABLE_NAME, null, values) == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public boolean checkNccExists(String maNcc){
        Cursor cursor = null;
        try{
            cursor = db.query(TABLE_NAME, null,
                    "mancc =?",
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

    public List<mNcc> getAllNcc(){
        List<mNcc> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        cursor.moveToFirst();

        if(cursor != null && cursor.moveToFirst()) {
            do {
                mNcc m = new mNcc();
                m.setMancc(cursor.getString(0));
                m.setTenncc(cursor.getString(1));
                m.setDiachi(cursor.getString(2));
                m.setPhonencc(cursor.getString(3));
                m.setMahangncc(cursor.getString(4));
                m.setLogoncc(cursor.getBlob(5));
                list.add(m);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<mNcc> getMaNcc(){
        List<mNcc> list = new ArrayList<>();
        String sql = "SELECT mancc, tenncc FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mNcc m = new mNcc();
            m.setMancc(cursor.getString(0));
            m.setTenncc(cursor.getString(1));
            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getNameNccByMaNcc(String maNcc) {
        String sql = "SELECT tenncc FROM " + TABLE_NAME + " WHERE mancc LIKE '" + maNcc + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
           String nccname = cursor.getString(0);
           return nccname;
        }else {
            return null;
        }

    }

    public List<mNcc> getMaNccByLoaiHang(String maLoaiHang){
        List<mNcc> list = new ArrayList<>();
        String sql = "SELECT mancc, tenncc FROM "+TABLE_NAME+" WHERE mahangncc LIKE '"+maLoaiHang+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mNcc m = new mNcc();
            m.setMancc(cursor.getString(0));
            m.setTenncc(cursor.getString(1));
            list.add(m);
            cursor.moveToNext();
        }
        Log.e(TAG, "getMaNccByLoaiHang: "+cursor.getCount());
        cursor.close();
        return list;
    }

    public List<String> getMaLoaiHangCuaNcc(String maNcc){
        List<String> list = new ArrayList<>();
        String sql = "SELECT mahangncc FROM "+TABLE_NAME+" WHERE mancc LIKE "+"'"+maNcc+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int updateNcc(mNcc m){
        ContentValues values = new ContentValues();
        values.put("mancc", m.getMancc());
        values.put("tenncc", m.getTenncc());
        values.put("diachincc", m.getDiachi());
        values.put("phonencc", m.getPhonencc());
        values.put("mahangncc", m.getMahangncc());
        values.put("logoncc", m.getLogoncc());

        int result = db.update(TABLE_NAME, values, "mancc =?", new String[]{m.getMancc()});

        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int deleteNcc(mNcc m){
        int result = db.delete(TABLE_NAME, "mancc =?", new String[]{m.getMancc()});
        if (result == 0){
            return -1;
        }

        return 1;
    }

}
