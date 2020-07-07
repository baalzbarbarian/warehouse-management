package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mHDNHChiTiet;

public class HDNHChiTietDAO extends DatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public HDNHChiTietDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static final String TAG = "HDChiTiet";
    public static final String TABLE_NAME = "HDChiTiet";
    public static final String SQL_HOADONCHITIET = "CREATE TABLE HDChiTiet (" +
            "mahdct text primary key," +
            "maloaihang text, " +
            "masp text, " +
            "soluong integer, " +
            "giasp double, " +
            "mancc);";

    public int inserHDChiTiet(mHDNHChiTiet m){
        ContentValues values = new ContentValues();
        values.put("mahdct", m.getMahdct());
        values.put("maloaihang", m.getMaloaihang());
        values.put("masp", m.getMasp());
        values.put("soluong", m.getSoluong());
        values.put("giasp", m.getGiasp());
        values.put("mancc", m.getMancc());

        try{
            long result = db.insert(TABLE_NAME, null, values);
            if (result == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public boolean checkSizeHDCT(String maHDCT){
        try{
            String sql = "SELECT * FROM "+TABLE_NAME+" WHERE mahdct LIKE '"+maHDCT+"'";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0){
                return true;
            }
        }catch (Exception e){
            Log.e(TAG, "checkSizeHDCT: "+e);
        }
        return false;
    }

    public mHDNHChiTiet getAllHDCT(String maHDCT) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE mahdct LIKE '" + maHDCT + "'", null);
        mHDNHChiTiet m = new mHDNHChiTiet();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                m = new mHDNHChiTiet();
                m.setMahdct(cursor.getString(0));
                m.setMaloaihang(cursor.getString(1));
                m.setMasp(cursor.getString(2));
                m.setSoluong(cursor.getInt(3));
                m.setGiasp(cursor.getDouble(4));
                m.setMancc(cursor.getString(5));

            } while (cursor.moveToNext());
        }

        return m;
    }

    public int deleteHDCT(String mahdct){
        int result = db.delete(TABLE_NAME, "mahdct =?", new String[]{mahdct});
        if (result == 0){
            return -1;
        }

        return 1;
    }

}
