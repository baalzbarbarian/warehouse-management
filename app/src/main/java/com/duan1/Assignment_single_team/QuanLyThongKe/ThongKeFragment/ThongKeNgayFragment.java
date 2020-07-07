package com.duan1.Assignment_single_team.QuanLyThongKe.ThongKeFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.ThongKeDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHangHoa;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeNgayFragment extends Fragment {


    public ThongKeNgayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_ngay, container, false);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        //Thống kê ngày
        TextInputEditText txtDTngay = view.findViewById(R.id.thongkengay_txtDTngay);
        txtDTngay.setText(en.format(thongKeDAO.getDoanhThuNgay())+" vnđ");

        TextInputEditText txtTenHang = view.findViewById(R.id.thongkengay_txtTenHang);
        TextInputEditText txtSoLuong = view.findViewById(R.id.thongkengay_txtSoLuong);
        TextInputEditText txtTongTien = view.findViewById(R.id.thongkengay_txtTongTien);

        List<mHangHoa> listHangBanChayNhatNgay = thongKeDAO.getHangBanChayNhatTrongNgay();
        txtTenHang.setText(listHangBanChayNhatNgay.get(0).getTenhanghoa());
        txtSoLuong.setText(listHangBanChayNhatNgay.get(0).getSoluong()+" Vnđ");
        txtTongTien.setText(en.format(listHangBanChayNhatNgay.get(0).getGiahanghoa()));

        //Thống kê tháng
        TextInputEditText txtDTTuan = view.findViewById(R.id.thongkengay_txtDTtuan);
        txtDTTuan.setText(en.format(thongKeDAO.getDoanhThuThang())+" Vnđ");

        return view;
    }

}
