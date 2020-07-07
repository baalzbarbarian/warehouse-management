package com.duan1.Assignment_single_team.QuanLyNhapHang;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.CheckStorageDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDNHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mCheckStorage;
import com.duan1.Assignment_single_team.mModel.mHDNHChiTiet;
import com.duan1.Assignment_single_team.mModel.mHangHoa;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class XacNhanHDNhapHangFragment extends Fragment {
    private static final String TAG = "XacNhanHDNhapHang";
    private View view;
    String mahdct;
    String spName;
    HDNHChiTietDAO HDNHChiTietDAO;
    TextInputEditText txtMaHDCT, txtTenSP, txtSoLuong, txtMaNcc, txtTongTien;
    Button btnStorage;
    mHDNHChiTiet m;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hoa_don_chi_tiet, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Hoá đơn chi tiết");

        txtMaHDCT = view.findViewById(R.id.hdct_mahdct);
        txtTenSP = view.findViewById(R.id.hdct_tensp);
        txtSoLuong = view.findViewById(R.id.hdct_soluong);
        txtMaNcc = view.findViewById(R.id.hdct_mancc);
        txtTongTien = view.findViewById(R.id.hdct_tonggiatri);
        btnStorage = view.findViewById(R.id.hdct_btnStorage);

        mahdct = getArguments().getString("MAHD");

        checkStorage(mahdct);

        HDNHChiTietDAO = new HDNHChiTietDAO(getActivity());
        m = HDNHChiTietDAO.getAllHDCT(mahdct);

        txtMaHDCT.setText(mahdct);

        SpNccDAO spNccDAO = new SpNccDAO(getActivity());
        spName = spNccDAO.getNameSPByMaSp(m.getMasp());
        txtTenSP.setText(spName);

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        txtSoLuong.setText(String.valueOf(m.getSoluong()));
        long tong = (long) (m.getGiasp() * m.getSoluong());
        String tongtien = en.format(tong);
        txtTongTien.setText(tongtien);

        NccDAO nccDAO = new NccDAO(getActivity());
        String nameNcc = nccDAO.getNameNccByMaNcc(m.getMancc());
        txtMaNcc.setText(nameNcc);

        btnStorage = view.findViewById(R.id.hdct_btnStorage);
        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertHangVaoKho(m.getMasp());
                setValueToCheckStorage(mahdct, -1);
            }
        });

        return view;
    }

    mCheckStorage mCheckStorage;
    CheckStorageDAO checkStorageDAO;
    private void setValueToCheckStorage(String mahd, int i){
        btnStorage.setText("Hoá đơn đã xử lý");
        btnStorage.setEnabled(false);
        mCheckStorage = new mCheckStorage();
        mCheckStorage.setMaHD(mahd);
        mCheckStorage.setCheck(i);
        checkStorageDAO = new CheckStorageDAO(getActivity());
        if (checkStorageDAO.insertCheckStorage(mCheckStorage) < 1){
            Log.e(TAG, "setValueToCheckStorage: ");
        }
    }

    private void checkStorage(String mahd){
        checkStorageDAO = new CheckStorageDAO(getActivity());
        if (!checkStorageDAO.checkStorage(mahd)){
            btnStorage.setText("Hoá đơn đã xử lý");
            btnStorage.setEnabled(false);
        }
    }

    private void insertHangVaoKho(String masp){
        mHangHoa mHH = new mHangHoa();
        HangHoaDAO hangHoaDAO = new HangHoaDAO(getActivity());

        mHH.setMahanghoa(m.getMasp());
        mHH.setGiahanghoa(m.getGiasp());
        mHH.setTenhanghoa(spName);
        mHH.setMaloaihang(m.getMaloaihang());
        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getActivity());
        mHH.setVitri(loaiHangDAO.getViTriByMaLoaiHang(m.getMaloaihang()));

        if (!hangHoaDAO.checkExistsHangHoa(masp)){
            int sl = hangHoaDAO.getSoLuongByMaHangHoa(masp);
            mHH.setSoluong(sl + m.getSoluong());
            Log.e(TAG, "insertHangVaoKho: "+mHH.getSoluong());
            int result = hangHoaDAO.updateHangHoa(mHH);
            if (result < 1){
                Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Đã lưu vào kho", Toast.LENGTH_SHORT).show();
            }
        }else {
            mHH.setSoluong(m.getSoluong());
            int result = hangHoaDAO.insertHangHoa(mHH);
            if (result < 1){
                Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Đã lưu vào kho", Toast.LENGTH_SHORT).show();
            }
        }
        
    }

}
