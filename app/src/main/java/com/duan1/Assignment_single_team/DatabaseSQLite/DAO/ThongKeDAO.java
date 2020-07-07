package com.duan1.Assignment_single_team.DatabaseSQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan1.Assignment_single_team.DatabaseSQLite.DatabaseHelper;
import com.duan1.Assignment_single_team.mModel.mHangHoa;
import com.duan1.Assignment_single_team.mModel.mTKDTTheoThang;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO extends DatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public ThongKeDAO(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<mTKDTTheoThang> tkdtThang(){
        List<mTKDTTheoThang> list = new ArrayList<>();
        String sql = "SELECT strftime('%m', HoaDonXH.ngaytaohoadon) AS 'Month', sum(HDXHChiTiet.giahang * HDXHChiTiet.soluonghang) AS 'DoanhThu'" +
                "FROM HoaDonXH INNER JOIN HDXHChiTiet ON HoaDonXH.mahoadon = HDXHChiTiet.mahdxh GROUP BY Month";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                mTKDTTheoThang m = new mTKDTTheoThang();
                m.setThangtrongnam(cursor.getString(0));
                m.setDoanhthuthang(cursor.getInt(1));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long getDoanhThuNgay(){
        long dt = 0;
        Cursor cursor = db.rawQuery("SELECT sum(HDXHChiTiet.giahang * HDXHChiTiet.soluonghang)\n" +
                "FROM HoaDonXH INNER JOIN HDXHChiTiet ON HoaDonXH.mahoadon = HDXHChiTiet.mahdxh\n" +
                "WHERE HoaDonXH.ngaytaohoadon = date('now')", null);
        if (cursor != null && cursor.moveToFirst()){
            dt = cursor.getLong(0);
        }

        cursor.close();
        return dt;
    }

    public long getDoanhThuThang(){
        long dt = 0;
        Cursor cursor = db.rawQuery("SELECT sum(HDXHChiTiet.giahang * HDXHChiTiet.soluonghang)\n" +
                "FROM HoaDonXH INNER JOIN HDXHChiTiet ON HoaDonXH.mahoadon = HDXHChiTiet.mahdxh\n" +
                "WHERE strftime('%m', HoaDonXH.ngaytaohoadon) = strftime('%m', 'now')", null);
        if (cursor != null && cursor.moveToFirst()){
            dt = cursor.getLong(0);
        }

        cursor.close();
        return dt;
    }

    public List<mHangHoa> getHangBanChayNhatTrongNgay(){
        List<mHangHoa> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT HangHoa.tenhanghoa, sum(HDXHChiTiet.soluonghang) AS 'SoLuong', sum(HDXHChiTiet.giahang*HDXHChiTiet.soluonghang) AS 'TongTien'\n" +
                "FROM HDXHChiTiet INNER JOIN HoaDonXH ON HoaDonXH.mahoadon = HDXHChiTiet.mahdxh \n" +
                "\t\t\t\tINNER JOIN HangHoa ON HDXHChiTiet.mahang LIKE HangHoa.mahanghoa\n" +
                "WHERE HoaDonXH.ngaytaohoadon = date('now')\n" +
                "GROUP BY HDXHChiTiet.mahang\n" +
                "ORDER BY SoLuong DESC", null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                mHangHoa m = new mHangHoa();
                m.setTenhanghoa(cursor.getString(0));
                m.setSoluong(cursor.getInt(1));
                m.setGiahanghoa(cursor.getDouble(2));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

}
