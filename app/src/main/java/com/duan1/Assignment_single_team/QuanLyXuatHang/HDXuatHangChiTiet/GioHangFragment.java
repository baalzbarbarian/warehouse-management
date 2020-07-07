package com.duan1.Assignment_single_team.QuanLyXuatHang.HDXuatHangChiTiet;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_GioHang;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDXHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mGioHang;
import com.duan1.Assignment_single_team.mModel.mHangHoa;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class GioHangFragment extends DialogFragment {

    Toolbar toolbar;
    String mahd;
    View view;

    Spinner spnTenSP;
    TextInputEditText txtGiaSP, txtSoLuong, txtTongTien;
    Button btnThemVaoGio, btnThanhToan;
    RecyclerView recyclerView;

    List<mGioHang> listGioHang = new ArrayList<>();
    RecyclerView_CustomAdapter_GioHang adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tao_hoa_don_xuat_hang, container, false);
        mahd = getArguments().getString("MAHD");
        initView();


        toolbar = view.findViewById(R.id.toolbar_giohang);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle("Giỏ hàng đại lý");
//        toolbar.inflateMenu(R.menu.option_menu_edit_dai_ly);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (insertHangDaiLy()){
//                    dismiss();
//                }
//                return false;
//            }
//        });
    }

    private boolean insertHangDaiLy() {

        return true;
    }


    private void initView(){
        spnTenSP = view.findViewById(R.id.giohang_spinner_tensp);
        txtGiaSP = view.findViewById(R.id.giohang_giasp);
        txtSoLuong = view.findViewById(R.id.giohang_soluongxuat);
        txtTongTien = view.findViewById(R.id.giohang_tongtien);
        btnThemVaoGio = view.findViewById(R.id.giohang_btnthemvaogio);
        btnThanhToan = view.findViewById(R.id.giohang_btnthanhtoan);
        recyclerView = view.findViewById(R.id.recyclerView_giohang);
        txtSoLuong.addTextChangedListener(new TextValidator(txtSoLuong) {
            @Override
            public void validate(TextView textView, String text) {
                if (txtSoLuong.getText().toString().equals("")){
                    txtTongTien.setText(0.0 +" vnd");
                }else {
                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    long tong = (long) (Integer.valueOf(txtSoLuong.getText().toString()) * Double.parseDouble(txtGiaSP.getText().toString()));
                    String tongtien = en.format(tong);
                    txtTongTien.setText(tongtien);
                }
            }
        });
        setSpinnerHangHoa();

        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertHHToGioHang();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhToanGioHang();
            }
        });
    }

    private void insertHHToGioHang(){

        mGioHang mGioHang = new mGioHang();
        mGioHang.setMahdxh(mahd);
        mGioHang.setMahang(maHang);
        mGioHang.setGiahang(Double.parseDouble(txtGiaSP.getText().toString()));
        mGioHang.setSoluong(Integer.parseInt(txtSoLuong.getText().toString()));

        int pos = checkMaHangFromGioHang(listGioHang, maHang);
        if (pos >= 0){

            listGioHang.get(pos).setSoluong(listGioHang.get(pos).getSoluong() + mGioHang.getSoluong());
            listGioHang.set(pos, mGioHang);
        }else {
            listGioHang.add(mGioHang);
        }

        adapter = new RecyclerView_CustomAdapter_GioHang(getActivity(), listGioHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void thanhToanGioHang(){
        HDXHChiTietDAO hdxhChiTietDAO = new HDXHChiTietDAO(getActivity());
        HangHoaDAO hangHoaDAO = new HangHoaDAO(getActivity());
        for (int i = 0; i < listGioHang.size(); i++) {

            mGioHang m = listGioHang.get(i);
            //Tính toán số lượng hàng còn lại trong kho sau khi xuất
            int soluongKhoCu = hangHoaDAO.getSoLuongByMaHangHoa(maHang);
            int soLuongXuat = listGioHang.get(i).getSoluong();
            if (soluongKhoCu < soLuongXuat){
                txtSoLuong.setError("Số lượng hàng tồn kho còn lại: "+soluongKhoCu);
            }else {
                //Add vào database
                int soluongKhoMoi = soluongKhoCu - soLuongXuat;
                int result1 = hdxhChiTietDAO.inserHDXHChiTiet(m);

                //add vào model
                mHangHoa mHangHoa = new mHangHoa();
                mHangHoa.setMahanghoa(maHang);
                mHangHoa.setSoluong(soluongKhoMoi);

                //update số lượng hàng còn lại trong kho
                int result = hangHoaDAO.updateSLHangHoa(mHangHoa);
                if (result > 0 && result1 > 0){
                    Toast.makeText(getActivity(), "Thanh Toán thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }
        }

        listGioHang.clear();
        dismiss();

    }

    public int checkMaHangFromGioHang(List<mGioHang> list, String maHang){
        int pos = -1;
        for (int i = 0; i < list.size(); i++){
            mGioHang m = list.get(i);
            if (m.getMahang().equalsIgnoreCase(maHang)){
                pos = i;
                break;
            }
        }
        return pos;
    }

    String maHang;
    private void setSpinnerHangHoa(){
        HangHoaDAO hangHoaDAO = new HangHoaDAO(getActivity());
        final List<mHangHoa> list = hangHoaDAO.getAllHangHoa();
        mHangHoa m = new mHangHoa("Chọn hàng xuất","Không để trống", 0, 0, "", "");
        list.add(0, m);
        ArrayAdapter<mHangHoa> dataAdapter = new ArrayAdapter<mHangHoa>(getActivity(),
                android.R.layout.simple_spinner_item, list){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenSP.setAdapter(dataAdapter);

        spnTenSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maHang = list.get(spnTenSP.getSelectedItemPosition()).getMahanghoa();
                txtGiaSP.setText(String.valueOf(list.get(spnTenSP.getSelectedItemPosition()).getGiahanghoa()));
                txtSoLuong.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //=================================//
    }

    public abstract class TextValidator implements TextWatcher {
        private final TextView textView;

        public TextValidator(TextView textView) {
            this.textView = textView;
        }

        public abstract void validate(TextView textView, String text);

        @Override
        final public void afterTextChanged(Editable s) {
            String text = textView.getText().toString();
            validate(textView, text);
        }

        @Override
        final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

        @Override
        final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
    }

}
