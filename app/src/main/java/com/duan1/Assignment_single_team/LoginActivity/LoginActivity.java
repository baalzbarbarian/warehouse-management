package com.duan1.Assignment_single_team.LoginActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.MainActivity;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mEmployee;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnCancel;
    TextView txtChangePass, txtForgotPassword;
    SharedPreferences sharedPreferences;
    Intent intent;
    CheckBox chkRemember;
    public boolean checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        getShare();


        //Login and remember
        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        edtUsername.setText(sharedPreferences.getString("USERNAME", ""));
        edtPassword.setText(sharedPreferences.getString("PASSWORD", ""));
        chkRemember.setChecked(sharedPreferences.getBoolean("CHKREMEMBER", false));

    }

    private void getShare(){
        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");
        boolean checkStatus = sharedPreferences.getBoolean("CHKREMEMBER", false);
        checkLogin = checkStatus;

        if (checkLogin == true){
            intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("USERNAME", username);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }

    private void initView() {
        edtUsername = findViewById(R.id.login_edtUsername);
        edtPassword = findViewById(R.id.login_edtPassword);
        btnCancel = findViewById(R.id.btnCancel);
        btnLogin = findViewById(R.id.btnLogin);

        chkRemember = findViewById(R.id.checkBox);

        txtChangePass = findViewById(R.id.login_txt_changePassword);
        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        txtForgotPassword = findViewById(R.id.login_txt_forgotPassword);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForgotPassWord();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginMethod();
            }
        });

    }

    private void openDialogForgotPassWord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom_forot_password, null);
        builder.setView(view);
        final Dialog dialog = builder.create();

        final EditText edtPass1 = view.findViewById(R.id.dialog_forgotpassword_edtPassword);
        final EditText edtPass2 = view.findViewById(R.id.dialog_forgotpassword_reedtPassword);
        final EditText edtCode = view.findViewById(R.id.dialog_forgotpassword_code);
        Button btnGetpass = view.findViewById(R.id.dialog_forgotpassword_btnChange);

        btnGetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVienDAO nhanVienDAO = new NhanVienDAO(getApplicationContext());
                if (isEmpty(edtPass1)){
                    edtPass1.setError("Không được trống");
                }else if (isEmpty(edtPass2)){
                    edtPass2.setError("Không được trống");
                }else if (isEmpty(edtCode)){
                    edtCode.setError("Liên hệ bên sở hữu ứng dụng để mua code");
                }else {
                    if (edtCode.getText().toString().equalsIgnoreCase("ABCDEF")){
                        if (!edtPass1.getText().toString().equals(edtPass2.getText().toString())){

                            edtPass2.setError("Mật khẩu không trùng khớp");
                            edtPass1.setError("Mật khẩu không trùng khớp");

                        }else {

                            mEmployee m = new mEmployee();
                            m.setUsername("admin");
                            m.setPassword(edtPass1.getText().toString());

                            if (nhanVienDAO.updateNewPassWhenFogotPass(m.getUsername(), m.getPassword()) < 1){
                                Toast.makeText(LoginActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this, "Đổi mật khẩu mới thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Code không tồn tại.\nVui lòng liên hệ bên sở hữu ứng dụng", Toast.LENGTH_SHORT).show();
                        edtCode.getText().clear();
                    }

                }
            }
        });

        view.findViewById(R.id.dialog_forgotpassword_btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom_change_password_adminitrator, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();

        final EditText edtUser = view.findViewById(R.id.dialog_changeInfo_edtUsername);
        final EditText edtPass1 = view.findViewById(R.id.dialog_changeInfo_edtPassword);
        final EditText edtPass2 = view.findViewById(R.id.dialog_changeInfo_edtRePassword);
        final EditText edtCode = view.findViewById(R.id.dialog_changeInfo_edtNewPassword);

        edtUser.setText("admin");

        view.findViewById(R.id.dialog_changeInfo_btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NhanVienDAO nhanVienDAO = new NhanVienDAO(getApplicationContext());
                
                if (isEmpty(edtPass1)){
                    edtPass1.setError("Không được trống");
                }else if (isEmpty(edtPass2)){
                    edtPass2.setError("Không được trống");
                }else if (isEmpty(edtCode)){
                    edtCode.setError("Liên hệ bên sở hữu ứng dụng để mua code");
                }else {
                    if (!nhanVienDAO.checkExists("admin")){
                        Toast.makeText(LoginActivity.this, "Chỉ được tạo 1 tài khoản quản trị", Toast.LENGTH_SHORT).show();
                    }else {
                        if (edtCode.getText().toString().equalsIgnoreCase("ABCDEF")){
                            if (!edtPass1.getText().toString().equals(edtPass2.getText().toString())){

                                edtPass2.setError("Mật khẩu không trùng khớp");
                                edtPass1.setError("Mật khẩu không trùng khớp");

                            }else {

                                mEmployee m = new mEmployee();
                                m.setUsername("admin");
                                m.setPassword(edtPass1.getText().toString());
                                m.setName("admin");
                                m.setPhonenumber("admin");
                                m.setAddress("admin");

                                if (nhanVienDAO.inserNhanVien(m) < 1){
                                    Toast.makeText(LoginActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Chúc mừng bạn đã trở thành quản trị viên", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Code không tồn tại.\nVui lòng liên hệ bên sở hữu ứng dụng", Toast.LENGTH_SHORT).show();
                            edtCode.getText().clear();
                        }
                    }
                }
            }
        });

        view.findViewById(R.id.dialog_changeInfo_btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void loginMethod() {

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        NhanVienDAO db = new NhanVienDAO(LoginActivity.this);

        if (isEmpty(edtUsername)){
            edtUsername.setError("Không để trống");
        }else if (isEmpty(edtPassword)){
            edtPassword.setError("Không được trống");
        }else if (!db.checkAccountLogin(username, password)){
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else {
                if (chkRemember.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USERNAME",username);
                    editor.putString("PASSWORD",password);
                    editor.putBoolean("CHKREMEMBER",true);
                    editor.commit();
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("USERNAME");
                    editor.remove("PASSWORD");
                    editor.remove("CHKREMEMBER");
                    editor.commit();
                }

                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", username);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
        }

    }


}
