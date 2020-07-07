package com.duan1.Assignment_single_team.QuanLyNhapHang;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDNHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHDNHChiTiet;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.duan1.Assignment_single_team.mModel.mNcc;
import com.duan1.Assignment_single_team.mModel.mSpNcc;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ThemHoaDonNhapHangFragment extends DialogFragment {

    private static final String TAG = "ThemHoaDonNhapHangFragm";
    Toolbar toolbar;
    String mahd;
    View view;

    TextInputEditText txtMaHDCT;
    TextInputEditText txtGiaSp;
    TextInputEditText txtSoLuong;
    TextInputEditText txtTongTien;
    Spinner spnLoaiHang;
    Spinner spnNcc;
    Spinner spnSanPham;

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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_them_hoa_don_nhap_hang, container, false);
        mahd = getArguments().getString("MAHD");
        initView();

        txtMaHDCT.setText(mahd);

        toolbar = view.findViewById(R.id.toolbar_nhaphang);

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
        toolbar.setTitle("Hoá Đơn Chi Tiết");
        toolbar.inflateMenu(R.menu.option_menu_edit_dai_ly);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (insertHDChiTiet()){
                    dismiss();
                }
                return false;
            }
        });
    }

    private void initView() {
        txtMaHDCT = view.findViewById(R.id.nhaphang_mahdct);
        txtGiaSp = view.findViewById(R.id.nhaphang_giasp);
        txtSoLuong = view.findViewById(R.id.nhaphang_soluongnhap);
        txtTongTien = view.findViewById(R.id.nhaphang_tongtien);
        spnLoaiHang = view.findViewById(R.id.nhaphang_loaihang);
        spnNcc = view.findViewById(R.id.nhaphang_ncc);
        spnSanPham = view.findViewById(R.id.nhaphang_tensp);

        txtSoLuong.addTextChangedListener(new TextValidator(txtSoLuong) {
            @Override
            public void validate(TextView textView, String text) {
                if (txtSoLuong.getText().toString().equals("")){
                    txtTongTien.setText("");
                }else {
                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    long tong = (long) (Integer.parseInt(txtSoLuong.getText().toString()) * Double.parseDouble(txtGiaSp.getText().toString()));
                    String tongtien = en.format(tong);
                    txtTongTien.setText(tongtien);
                }
            }
        });

        setSpinnerLoaiHang();
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

    private boolean insertHDChiTiet() {
        mHDNHChiTiet m = new mHDNHChiTiet();
        m.setMahdct(txtMaHDCT.getText().toString());
        m.setMaloaihang(maLoaiHang);
        m.setMasp(maSp);
        m.setGiasp(giasp);
        m.setSoluong(Integer.parseInt(txtSoLuong.getText().toString()));
        m.setMancc(maNcc);
        HDNHChiTietDAO HDNHChiTietDAO = new HDNHChiTietDAO(getActivity());
        int result = HDNHChiTietDAO.inserHDChiTiet(m);
        if (result < 1){
            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(getActivity(), "Đã thêm hoá đơn", Toast.LENGTH_SHORT).show();
            return true;
        }


    }

    //Spinner
    mLoaiHang m;
    mNcc mncc;
    mSpNcc mSpNcc;
    String maLoaiHang;
    String maNcc;
    String maLoaiSp;
    String maSp;
    double giasp;
    List<mLoaiHang> loaiHangList;
    List<mNcc> nccList;
    List<mSpNcc> mSpNccList;
    private void setSpinnerLoaiHang(){
        //Set spinner Loại hàng
        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getActivity());
        loaiHangList = loaiHangDAO.getAllLoaiHang();
        m = new mLoaiHang("Chọn loại hàng", "Không bỏ trống","");
        loaiHangList.add(0, m);
        ArrayAdapter<mLoaiHang> dataAdapter = new ArrayAdapter<mLoaiHang>(getActivity(),
                android.R.layout.simple_spinner_item, loaiHangList){
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
        spnLoaiHang.setAdapter(dataAdapter);

        spnLoaiHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiHang = loaiHangList.get(spnLoaiHang.getSelectedItemPosition()).getMaloaihang();

                setSpinnerNcc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //=================================//
    }

    private void setSpinnerNcc(){
        //Spinner nha cung cấp
        NccDAO nccDAO = new NccDAO(getActivity());
        nccList = nccDAO.getMaNccByLoaiHang(maLoaiHang);
        mncc = new mNcc("Chọn NCC", "Không bỏ trống","", "","",null);
        nccList.add(0, mncc);
        ArrayAdapter<mNcc> dataAdapterNCC = new ArrayAdapter<mNcc>(getActivity(),
                android.R.layout.simple_spinner_item, nccList){
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
        dataAdapterNCC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNcc.setAdapter(dataAdapterNCC);

        spnNcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maNcc = nccList.get(spnNcc.getSelectedItemPosition()).getMancc();

                setSpinnerSP();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //===============================//
    }

    private void setSpinnerSP() {
        final SpNccDAO spNccDAO = new SpNccDAO(getActivity());
        mSpNccList = spNccDAO.getSpByMaNcc(maNcc);
        ArrayAdapter<mSpNcc> dataAdapterNCC = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, mSpNccList);
        dataAdapterNCC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSanPham.setAdapter(dataAdapterNCC);

        spnSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSp = mSpNccList.get(spnSanPham.getSelectedItemPosition()).getMasp();
                maLoaiSp = mSpNccList.get(spnSanPham.getSelectedItemPosition()).getMaloaisp();

                giasp = spNccDAO.getProductPriceByMaSP(maSp);
                txtGiaSp.setText(String.valueOf(giasp));
                txtSoLuong.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_nhap_hang, fragment);
        transaction.commit();
    }

}
