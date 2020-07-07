package com.duan1.Assignment_single_team.DatabaseSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.CheckStorageDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.DaiLyDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDNHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDXHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonNHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonXHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbQuanLyKho";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NhanVienDAO.SQL_NHAN_VIEN);
        db.execSQL(DaiLyDAO.SQL_DAI_LY);
        db.execSQL(NccDAO.SQL_NCC);
        db.execSQL(SpNccDAO.SQL_SPNCC);
        db.execSQL(LoaiHangDAO.SQL_LOAIHANG);
        db.execSQL(HoaDonNHDAO.SQL_HOADON);
        db.execSQL(HDNHChiTietDAO.SQL_HOADONCHITIET);
        db.execSQL(HangHoaDAO.SQL_HANGHOACHITIET);
        db.execSQL(CheckStorageDAO.SQL_CHECKSTORAGE);
        db.execSQL(HoaDonXHDAO.SQL_HOADONXUATHANG);
        db.execSQL(HDXHChiTietDAO.SQL_HDXHCHITIET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+NhanVienDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+DaiLyDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+NccDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+SpNccDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+LoaiHangDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+ HoaDonNHDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+ HDNHChiTietDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+HangHoaDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+CheckStorageDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+HoaDonXHDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+HDXHChiTietDAO.TABLE_NAME);

        onCreate(db);
    }
}
