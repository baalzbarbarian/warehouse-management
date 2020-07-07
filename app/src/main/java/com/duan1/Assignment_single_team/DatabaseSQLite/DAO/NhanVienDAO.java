package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mEmployee;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "NhanVien";
    public static final String SQL_NHAN_VIEN = "CREATE TABLE NhanVien (" +
            "username text primary key," +
            "password text, " +
            "hoten text, " +
            "phone text, " +
            "diachi text);";
    public static final String TAG = "NhanVienDAO";

    public NhanVienDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inserNhanVien(mEmployee mEmployee){
        ContentValues values = new ContentValues();
        values.put("username", mEmployee.getUsername());
        values.put("password", mEmployee.getPassword());
        values.put("hoten", mEmployee.getName());
        values.put("phone", mEmployee.getPhonenumber());
        values.put("diachi", mEmployee.getAddress());

        try{
            if (db.insert(TABLE_NAME, null, values) == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public boolean checkExists(String username){
        Cursor cursor = null;
        try{
            cursor = db.query(TABLE_NAME, null,
                    "username =?",
                    new String[]{username}, null, null, null);

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

    public List<mEmployee> getAllEmployee(){
        List<mEmployee> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            mEmployee m = new mEmployee();
            m.setUsername(cursor.getString(0));
            m.setPassword(cursor.getString(1));
            m.setName(cursor.getString(2));
            m.setPhonenumber(cursor.getString(3));
            m.setAddress(cursor.getString(4));

            list.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean checkAccountLogin(String username, String password){
        String sqlUser = "SELECT * FROM "
                +TABLE_NAME+ " WHERE username LIKE " +"'"+username+"'" + " AND password LIKE " +"'"+password+"'";
        Cursor cursor = db.rawQuery(sqlUser, null);
        if (cursor.getCount() < 1){
            cursor.close();
            return false;
        }
        return true;
    }

    public int updateEmployee(mEmployee m){
        ContentValues values = new ContentValues();
        values.put("username", m.getUsername());
        values.put("password", m.getPassword());
        values.put("hoten", m.getName());
        values.put("phone", m.getPhonenumber());
        values.put("diachi", m.getAddress());

        int result = db.update(TABLE_NAME, values, "username =?", new String[]{m.getUsername()});
        Log.e(TAG, "updateEmployee: "+m.getUsername());
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int updateNewPassWhenFogotPass(String user, String pass){
        ContentValues values = new ContentValues();
        values.put("password", pass);

        int result = db.update(TABLE_NAME, values, "username =?", new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int deleteAccount(mEmployee mEmployee){
        int result = db.delete(TABLE_NAME, "username =?", new String[]{mEmployee.getUsername()});
        if (result == 0){
            return -1;
        }

        return 1;
    }

    public String getNhanVienByUsername(String username){
        String name = null;
        String sql = "SELECT hoten FROM "+TABLE_NAME+" WHERE username LIKE "+"'"+username+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        name = cursor.getString(0);

        return name;
    }

}
