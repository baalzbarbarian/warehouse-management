package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mHangHoa;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HangHoaDAO extends DatabaseHelper {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public HangHoaDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static final String TAG = "HangHoaDAO";
    public static final String TABLE_NAME = "HangHoa";
    public static final String SQL_HANGHOACHITIET = "CREATE TABLE HangHoa (" +
            "mahanghoa text primary key," +
            "tenhanghoa date," +
            "soluong integer," +
            "giahanghoa double," +
            "maloaihang text, " +
            "vitri text);";

    public int insertHangHoa(mHangHoa m){
        ContentValues values = new ContentValues();
        values.put("mahanghoa", m.getMahanghoa());
        values.put("tenhanghoa", m.getTenhanghoa());
        values.put("soluong", m.getSoluong());
        values.put("giahanghoa", m.getGiahanghoa());
        values.put("maloaihang", m.getMaloaihang());
        values.put("vitri", m.getVitri());

        long result = db.insert(TABLE_NAME, null, values);
        if (result < 1){
            return -1;
        }

        return 1;
    }

    public List<mHangHoa> getAllHangHoa(){
        List<mHangHoa> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            do{
                mHangHoa m = new mHangHoa();
                m.setMahanghoa(cursor.getString(0));
                m.setTenhanghoa(cursor.getString(1));
                m.setSoluong(cursor.getInt(2));
                m.setGiahanghoa(cursor.getDouble(3));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<mHangHoa> getAllHangHoaByMaLoaiHang(String maloaihang){
        List<mHangHoa> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE maloaihang LIKE '"+maloaihang+"'", null);
        if (cursor != null && cursor.moveToFirst()){
            do{
                mHangHoa m = new mHangHoa();
                m.setMahanghoa(cursor.getString(0));
                m.setTenhanghoa(cursor.getString(1));
                m.setSoluong(cursor.getInt(2));
                m.setGiahanghoa(cursor.getDouble(3));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public String getNameHangHoaByMaHang(String mahang){
        String nameHang = "";
        Cursor cursor = db.rawQuery("Select tenhanghoa FROM "+TABLE_NAME+" WHERE mahanghoa LIKE '"+mahang+"'", null);
        if (cursor != null && cursor.moveToFirst()){
            nameHang = cursor.getString(0);
        }
        return nameHang;
    }

    public boolean checkExistsHangHoa(String mahanghoa){
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+" WHERE mahanghoa LIKE '"+mahanghoa+"'", null);

        if (cursor.getCount() > 0){
            return false;
        }
        return true;
    }

    public int getSoLuongByMaHangHoa(String mahang){
        Cursor cursor = db.rawQuery("Select soluong from "+TABLE_NAME+" WHERE mahanghoa LIKE '"+mahang+"'", null);
        int sl = 0;
        if (cursor != null && cursor.moveToFirst()){
            sl = cursor.getInt(0);
        }
        return sl;
    }

    public int updateHangHoa(mHangHoa m){
        ContentValues values = new ContentValues();
        values.put("tenhanghoa", m.getTenhanghoa());
        values.put("soluong", m.getSoluong());
        values.put("giahanghoa", m.getGiahanghoa());
        values.put("maloaihang", m.getMaloaihang());
        values.put("vitri", m.getVitri());

        int result = db.update(TABLE_NAME, values, "mahanghoa =?", new String[]{m.getMahanghoa()});

        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int updateSLHangHoa(mHangHoa m){
        ContentValues values = new ContentValues();
        values.put("soluong", m.getSoluong());

        int result = db.update(TABLE_NAME, values, "mahanghoa =?", new String[]{m.getMahanghoa()});
        if (result < 1){
            return -1;
        }
        Log.e(TAG, "updateSLHangHoa: "+result);
        return 1;
    }

    public int deleteHangHoa(String m){
        int result = db.delete(TABLE_NAME, "maloaihang =?", new String[]{m});
        if (result > 0){
            return 1;
        }
        return -1;
    }

}
