package com.duan1.Assignment_single_team.QuanLyNhapHang;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonNHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.QuanLyNhapHang.QLyHoaDon.HoaDonNgayFragment;
import com.duan1.Assignment_single_team.QuanLyNhapHang.QLyHoaDon.HoaDonTatCaFragment;
import com.duan1.Assignment_single_team.QuanLyNhapHang.QLyHoaDon.HoaDonThangFragment;
import com.duan1.Assignment_single_team.QuanLyNhapHang.QLyHoaDon.HoaDonTuanFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHoaDon;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NhapHangActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private ActionBar toolbar;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_hang);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("USERNAME");

        toolbar = getSupportActionBar();

        BottomNavigationView navigationView = findViewById(R.id.navigation_nhapHang);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedLestener);

        toolbar.setTitle("Hóa Đơn Nhập Hàng");
        loadFragment(new HoaDonTatCaFragment());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddHoaDon();
            }
        });

    }

    private void showDialogAddHoaDon(){

        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
        String nameNv = nhanVienDAO.getNhanVienByUsername(username);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_add_hoadon);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextInputEditText txtMaHD = dialog.findViewById(R.id.dialog_custom_add_hoadon_mahoadon);
        final TextInputEditText txtNgayTaoHD = dialog.findViewById(R.id.dialog_custom_add_hoadon_ngaytaohoadon);
        TextInputEditText txtEmpName = dialog.findViewById(R.id.dialog_custom_add_hoadon_tennhanvien);
        final TextInputEditText txtGhiChu = dialog.findViewById(R.id.dialog_custom_add_hoadon_ghichu);

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

                mHoaDon m = new mHoaDon();
                HoaDonNHDAO hoaDonNHDAO = new HoaDonNHDAO(NhapHangActivity.this);

                if (isEmpty(txtMaHD)){
                    txtMaHD.setError("Không được trống");
                }else if (isEmpty(txtNgayTaoHD)){
                    txtNgayTaoHD.setError("Không được trống");
                }else if (isEmpty(txtGhiChu)){
                    txtGhiChu.setError("Không được trống");
                }else if (!hoaDonNHDAO.checkHdExists(txtMaHD.getText().toString())){
                    txtMaHD.setError("Mã hoá đơn đã tồn tại");
                }else {
                    m.setMahoadon(txtMaHD.getText().toString());
                    try {
                        m.setNgaytaohoadon(sdf.parse(txtNgayTaoHD.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    m.setGhichu(txtGhiChu.getText().toString());
                    m.setManhanvien(username);

                    if (hoaDonNHDAO.inserHoaDon(m) < 1){
                        Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadFragment(new HoaDonTatCaFragment());
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(NhapHangActivity.this,
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedLestener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){

                case R.id.navigation_nhaphang_tatca:
                    toolbar.setTitle("Tất cả");
                    loadFragment(new HoaDonTatCaFragment());
                    return true;

                case R.id.navigation_nhaphang_ngay:
                    toolbar.setTitle("Hôm nay");
                    loadFragment(new HoaDonNgayFragment());
                    return true;

                case R.id.navigation_nhaphang_tuan:
                    toolbar.setTitle("Tuần");
                    loadFragment(new HoaDonTuanFragment());
                    return true;

                case R.id.navigation_nhaphang_thang:
                    toolbar.setTitle("Tháng");
                    loadFragment(new HoaDonThangFragment());
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_nhap_hang, fragment);
        fragment.setArguments(bundle);
        transaction.commit();
    }

}
