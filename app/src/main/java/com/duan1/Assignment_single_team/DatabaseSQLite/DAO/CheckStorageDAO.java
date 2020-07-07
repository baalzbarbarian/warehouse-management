package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mCheckStorage;
import com.duan1.Assignment_single_team.mModel.mHangHoa;

public class CheckStorageDAO extends DatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public CheckStorageDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static final String TAG = "CheckStorage";
    public static final String TABLE_NAME = "CheckStorage";
    public static final String SQL_CHECKSTORAGE = "CREATE TABLE CheckStorage (" +
            "mahd text primary key, " +
            "checkstorage integer);";

    public int insertCheckStorage(mCheckStorage m){
        ContentValues values = new ContentValues();
        values.put("mahd", m.getMaHD());
        values.put("checkstorage", m.getCheck());

        long result = db.insert(TABLE_NAME, null, values);
        if (result < 1){
            return -1;
        }
        return 1;
    }

    public boolean checkStorage(String maHD){
        String sql = "Select checkstorage from CheckStorage where mahd like '"+maHD+"'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0){
            return false;
        }
        return true;
    }
}
