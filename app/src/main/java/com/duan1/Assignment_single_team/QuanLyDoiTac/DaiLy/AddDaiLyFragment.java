package com.duan1.Assignment_single_team.QuanLyDoiTac.DaiLy;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.DaiLyDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mDaiLy;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDaiLyFragment extends DialogFragment {

    private static final String TAG = "AddDaiLyFragment";
    private TextInputEditText txtMaDaiLy;
    private TextInputEditText txtTenDaiLy;
    private TextInputEditText txtDiaChi;
    private TextInputEditText txtPhone;

    View view;
    public AddDaiLyFragment() {
        // Required empty public constructor
    }

    List<mDaiLy> mDaiLyList;
    mDaiLy m;
    DaiLyDAO daiLyDAO;
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        view = inflater.inflate(R.layout.fragment_add_dai_ly, container, false);
        initView();
        toolbar = view.findViewById(R.id.toolbar_edit_daily);

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
        toolbar.setTitle("Thêm Đại Lý");
        toolbar.inflateMenu(R.menu.option_menu_edit_dai_ly);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (insertDaiLy()){
                    loadFragment(new DaiLyFragment());
                    dismiss();
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_doi_tac, fragment);
        transaction.commit();
    }

    private void initView(){
        txtMaDaiLy = view.findViewById(R.id.edtMaDaily_add_daily);
        txtTenDaiLy = view.findViewById(R.id.edtTenDaiLy_add_daily);
        txtDiaChi = view.findViewById(R.id.edtDiaChiDaiLy_add_daily);
        txtPhone = view.findViewById(R.id.edtPhoneDaiLy_add_daily);
    }

    private boolean insertDaiLy(){
        daiLyDAO = new DaiLyDAO(getActivity());
        if (txtMaDaiLy.getText().toString().trim().length() < 6){
            txtMaDaiLy.setError("Mã Đại Lý phải có ít nhất 6 ký tự");
            return false;
        }else if (!daiLyDAO.checkDailyExists(txtMaDaiLy.getText().toString().trim())){
            txtMaDaiLy.setError("Mã đại lý đã tồn tại");
            return false;
        }else if (txtTenDaiLy.getText().toString().trim().isEmpty()){
            txtTenDaiLy.setError("Tên không được trống");
            return false;
        }else if (txtDiaChi.getText().toString().trim().isEmpty()){
            txtDiaChi.setError("Địa chỉ không được trống");
            return false;
        }else if (txtPhone.getText().toString().trim().isEmpty()){
            txtPhone.setError("Số điện thoại không được trống");
            return false;
        }else {
            String maDaiLy = txtMaDaiLy.getText().toString().trim();
            String tenDaiLy = txtTenDaiLy.getText().toString().trim();
            String diaChi = txtDiaChi.getText().toString().trim();
            String sodienthoai = txtPhone.getText().toString().trim();

            m = new mDaiLy();
                m.setMadaily(maDaiLy);
                m.setTendaily(tenDaiLy);
                m.setDiachi(diaChi);
                m.setPhone(sodienthoai);

                if (daiLyDAO.inserDaiLy(m) < 1){
                    Toast.makeText(getActivity(), "Thêm Đại Lý Thất Bại", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Thêm Đại Lý Thành Công", Toast.LENGTH_SHORT).show();
                }
            return true;
        }

    }

}
