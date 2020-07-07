package com.duan1.Assignment_single_team.QuanLyXuatHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.DaiLyDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonXHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.QuanLyXuatHang.QlyHoaDon.NgayHoaDonXHFragment;
import com.duan1.Assignment_single_team.QuanLyXuatHang.QlyHoaDon.TatCaHoaDonXHFragment;
import com.duan1.Assignment_single_team.QuanLyXuatHang.QlyHoaDon.ThangHoaDonXHFragment;
import com.duan1.Assignment_single_team.QuanLyXuatHang.QlyHoaDon.TuanHoaDonXHFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mDaiLy;
import com.duan1.Assignment_single_team.mModel.mHDXHChiTiet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class XuatHangActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private ActionBar toolbar;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_hang);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("USERNAME");

        toolbar = getSupportActionBar();

        BottomNavigationView navigationView = findViewById(R.id.navigation_xuathang);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedLestener);

        toolbar.setTitle("Hóa Đơn Xuất Hàng");
        loadFragment(new TatCaHoaDonXHFragment());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddHoaDon();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedLestener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){

                case R.id.navigation_xuathang_tatca:
                    toolbar.setTitle("Tất cả");
                    loadFragment(new TatCaHoaDonXHFragment());
                    return true;

                case R.id.navigation_xuathang_ngay:
                    toolbar.setTitle("Hôm nay");
                    loadFragment(new NgayHoaDonXHFragment());
                    return true;

                case R.id.navigation_xuathang_tuan:
                    toolbar.setTitle("Tuần");
                    loadFragment(new TuanHoaDonXHFragment());
                    return true;

                case R.id.navigation_xuathang_thang:
                    toolbar.setTitle("Tháng");
                    loadFragment(new ThangHoaDonXHFragment());
                    return true;

            }

            return false;
        }
    };

    String maDaiLy;
    private void showDialogAddHoaDon(){

        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
        String nameNv = nhanVienDAO.getNhanVienByUsername(username);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_add_hdxuathang);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextInputEditText txtMaHD = dialog.findViewById(R.id.dialog_custom_add_hoadon_mahoadon);
        final TextInputEditText txtNgayTaoHD = dialog.findViewById(R.id.dialog_custom_add_hoadon_ngaytaohoadon);
        TextInputEditText txtEmpName = dialog.findViewById(R.id.dialog_custom_add_hoadon_tennhanvien);
        final TextInputEditText txtGhiChu = dialog.findViewById(R.id.dialog_custom_add_hoadon_ghichu);

        final Spinner spnDaiLy = dialog.findViewById(R.id.dialog_custom_add_hdxh_daily);
        DaiLyDAO daiLyDAO = new DaiLyDAO(XuatHangActivity.this);
        final List<mDaiLy> mDaiLyList = daiLyDAO.getAllDaiLy();
        mDaiLy mDaiLy = new mDaiLy("Chọn đại lý nhận hàng","Không để trống","","");
        mDaiLyList.add(0, mDaiLy);
        ArrayAdapter<mDaiLy> dataAdapter = new ArrayAdapter<mDaiLy>(XuatHangActivity.this,
                android.R.layout.simple_spinner_item, mDaiLyList){
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
        spnDaiLy.setAdapter(dataAdapter);

        spnDaiLy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maDaiLy = mDaiLyList.get(spnDaiLy.getSelectedItemPosition()).getMadaily();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //=================================//

        txtEmpName.setText(nameNv);
        dialog.show();

        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        txtNgayTaoHD.setText(date);
        txtNgayTaoHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(txtNgayTaoHD);
            }
        });

        dialog.findViewById(R.id.dialog_custom_add_hoadon_btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHDXHChiTiet m = new mHDXHChiTiet();
                HoaDonXHDAO hoaDonDAO = new HoaDonXHDAO(XuatHangActivity.this);
                if (isEmpty(txtMaHD)){
                    txtMaHD.setError("Không được trống");
                }else if (isEmpty(txtNgayTaoHD)){
                    txtNgayTaoHD.setError("Không được trống");
                }else if (isEmpty(txtGhiChu)){
                    txtGhiChu.setError("Không được trống");
                }else if (!hoaDonDAO.checkHdExists(txtMaHD.getText().toString())){
                    txtMaHD.setError("Mã hoá đơn đã tồn tại");
                }else if (spnDaiLy.getSelectedItemPosition() == 0){
                    Toast.makeText(XuatHangActivity.this, "Vui lòng chọn đại lý xuất hàng", Toast.LENGTH_SHORT).show();
                }else {
                    m.setMahoadon(txtMaHD.getText().toString());
                    try {
                        m.setNgaytaohoadon(sdf.parse(txtNgayTaoHD.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    m.setGhichu(txtGhiChu.getText().toString());
                    m.setManhanvien(username);
                    m.setMadaily(maDaiLy);
                    if (hoaDonDAO.inserHoaDon(m) < 1){
                        Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadFragment(new TatCaHoaDonXHFragment());
                    }

                }
            }
        });

        dialog.findViewById(R.id.dialog_custom_add_hoadon_btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showDateDialog(final TextInputEditText date){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(calendar.DATE);
        int month = calendar.get(calendar.MONTH);
        int year = calendar.get(calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(XuatHangActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        date.setText(sdf.format(calendar.getTime()));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void loadFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_xuat_hang, fragment);
        fragment.setArguments(bundle);
        transaction.commit();
    }
}
