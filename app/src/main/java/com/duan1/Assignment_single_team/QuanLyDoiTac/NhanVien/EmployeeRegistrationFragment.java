package com.duan1.Assignment_single_team.QuanLyDoiTac.NhanVien;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mEmployee;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeRegistrationFragment extends DialogFragment {

    private static final String TAG = "EmployeeRegistrationFra";
    private Toolbar toolbar;
    View view;

    EditText username;
    EditText password;
    EditText repassword;
    EditText fullname;
    EditText phonenumber;
    EditText address;
    NhanVienDAO nhanVienDAO;

    public static EmployeeRegistrationFragment display(FragmentManager fragmentManager) {
        EmployeeRegistrationFragment employeeRegistrationFragment = new EmployeeRegistrationFragment();
        employeeRegistrationFragment.show(fragmentManager, TAG);
        return employeeRegistrationFragment;
    }

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
        view = inflater.inflate(R.layout.fragment_employee_registration, container, false);
        initView();

        toolbar = view.findViewById(R.id.toolbar);

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
        toolbar.setTitle("Thêm Nhân Viên");
        toolbar.inflateMenu(R.menu.dialog_full_screen_save_action);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (checkDataEntered()){
                    insertNhanVien();
                    loadFragment(new NhanVienFragment());
                    dismiss();
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", "admin");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_doi_tac, fragment);
        fragment.setArguments(bundle);
        transaction.commit();
    }

    private void initView(){
        username = view.findViewById(R.id.edtUser);
        password = view.findViewById(R.id.edtPass1);
        repassword = view.findViewById(R.id.edtPass2);
        fullname = view.findViewById(R.id.edtName);
        phonenumber = view.findViewById(R.id.edtPhone);
        address = view.findViewById(R.id.edtAddress);
    }

    private void insertNhanVien() {
        nhanVienDAO = new NhanVienDAO(getActivity());

        mEmployee m = new mEmployee();
        m.setUsername(username.getText().toString());
        m.setPassword(password.getText().toString());
        m.setName(fullname.getText().toString());
        m.setPhonenumber(phonenumber.getText().toString());
        m.setAddress(address.getText().toString());

        if (nhanVienDAO.inserNhanVien(m) < 1){
            Toast.makeText(getActivity(),"Thêm nhân viên thất bại.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(),"Thêm nhân viên thành công !", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkDataEntered() {
        nhanVienDAO = new NhanVienDAO(getActivity());

        if (isEmpty(username)){
            username.setError("Tài khoản không được trống");
            return false;
        }
        else if (!nhanVienDAO.checkExists(username.getText().toString())){
            username.setError("Tài khoản đã tồn tại !");
            return false;
        }
        else if (isEmpty(password) || password.getText().toString().length() < 8){
            password.setError("Mật khẩu phải có ít nhất 8 ký tự");
            return false;
        }
        else if (isEmpty(repassword)){
            repassword.setError("Mật khẩu xác nhận không được trống");
            return false;
        }
        else if (!repassword.getText().toString().equals(password.getText().toString())){
            repassword.setError("Mật khẩu xác nhận không đúng");
            return false;
        }
        else if (isEmpty(fullname)){
            fullname.setError("Tên không được để trống");
            return false;
        }
        else if (isEmpty(phonenumber)){
            phonenumber.setError("Số điện thoại không được trống");
            return false;
        }
        else if (isEmpty(address)){
            address.setError("Địa chỉ không được trống");
            return false;
        }
        return true;
    }



    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}
