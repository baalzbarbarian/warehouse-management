package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ImageView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mDaiLy;
import com.duan1.Assignment_single_team.mModel.mSpNcc;

import java.util.ArrayList;
import java.util.List;

public class SpNccDAO extends DatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public SpNccDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static final String TABLE_NAME = "SpNcc";
    public static final String SQL_SPNCC = "CREATE TABLE SpNcc (" +
            "masp text primary key," +
            "tensp text, " +
            "giasp double, " +
            "maloaisp text," +
            "mancc text," +
            "logospncc blob);";
    public static final String TAG = "SpNcc";

    public int inserSpNcc(mSpNcc m){
        ContentValues values = new ContentValues();
        values.put("masp", m.getMasp());
        values.put("tensp", m.getTensp());
        values.put("giasp", m.getGiasp());
        values.put("maloaisp", m.getMaloaisp());
        values.put("mancc", m.getMancc());
        values.put("logospncc", m.getLogospncc());

        try{
            long result = db.insert(TABLE_NAME, null, values);
            Log.e(TAG, "inserSpNcc: "+result);
            if (result == -1){
                return -1;
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        db.close();
        return 1;
    }

    public boolean checkSpExists(String maSp){
        Cursor cursor = null;
        try{
            cursor = db.query(TABLE_NAME, null,
                    "masp =?",
                    new String[]{maSp}, null, null, null);

        }catch (Exception e){
            Log.d(TAG, "checkExists: "+e.toString());
        }

        if (cursor.getCount() > 0){
            cursor.close();
            return false;
        }
        return true;
    }

    public List<mSpNcc> getSpByMaNcc(String maNcc){
        List<mSpNcc> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE mancc LIKE '"+maNcc+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                mSpNcc m = new mSpNcc();
                m.setMasp(cursor.getString(0));
                m.setTensp(cursor.getString(1));
                m.setGiasp(cursor.getDouble(2));
                m.setMaloaisp(cursor.getString(3));
                m.setMancc(cursor.getString(4));
                m.setLogospncc(cursor.getBlob(5));
                list.add(m);
            }while (cursor.moveToNext());
        }

        Log.e(TAG, "getSpByMaNcc: "+cursor.getCount()+maNcc);
        cursor.close();
        return list;
    }

    public double getProductPriceByMaSP(String masp){
        double giaSp;
        String sql = "SELECT giasp FROM "+TABLE_NAME+" WHERE masp LIKE '"+masp+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        giaSp = cursor.getDouble(0);
        cursor.close();
        return giaSp;
    }

    public String getNameSPByMaSp(String maSP){
        String sql = "SELECT tensp FROM "+TABLE_NAME+" WHERE masp LIKE '"+maSP+"'";
        Cursor cursor = db.rawQuery(sql, null);

        String spName = "";
        if (cursor != null && cursor.moveToFirst()){
            spName = cursor.getString(0);
        }
        cursor.close();
        return spName;
    }

    public byte[] getLogoSpByMaSp(String maSP){
        byte[] imgLogo = null;
        Cursor cursor = db.rawQuery("SELECT logospncc FROM "+TABLE_NAME+" WHERE masp LIKE '"+maSP+"'", null);
        if (cursor != null && cursor.moveToFirst()){
            imgLogo = cursor.getBlob(0);
        }
        cursor.close();
        return imgLogo;
    }

    public int updateDaily(mDaiLy m){
        ContentValues values = new ContentValues();
        values.put("madaily", m.getMadaily());
        values.put("tendaily", m.getTendaily());
        values.put("diachi", m.getDiachi());
        values.put("phone", m.getPhone());

        int result = db.update(TABLE_NAME, values, "madaily =?", new String[]{m.getMadaily()});

        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int deleteSpNccByMaSp(mSpNcc mSpNcc){
        int result = db.delete(TABLE_NAME, "masp =?", new String[]{mSpNcc.getMasp()});
        if (result == 0){
            return -1;
        }

        return 1;
    }

    public int deleteSpNccByLoaiSp(String loaiSp){
        int result = db.delete(TABLE_NAME, "maloaisp =?", new String[]{loaiSp});
        if (result == 0){
            return -1;
        }

        return 1;
    }

    public int deleteSpNccByMaNcc(String mancc){
        int result = 0;
        try {
            result = db.delete(TABLE_NAME, "mancc =?", new String[]{mancc});
        }catch (Exception e){
            Log.e(TAG, "deleteSpNcc: "+e);
        }

        if (result == 0){
            return -1;
        }

        return 1;
    }

}
