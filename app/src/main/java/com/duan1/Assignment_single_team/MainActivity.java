package com.duan1.Assignment_single_team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.duan1.Assignment_single_team.LoginActivity.LoginActivity;
import com.duan1.Assignment_single_team.QuanLyDoiTac.DoiTacActivity;
import com.duan1.Assignment_single_team.QuanLyKhoHang.KhoHangActivity;
import com.duan1.Assignment_single_team.QuanLyNhapHang.NhapHangActivity;
import com.duan1.Assignment_single_team.QuanLyThongKe.ThongKeActivity;
import com.duan1.Assignment_single_team.QuanLyXuatHang.XuatHangActivity;

public class MainActivity extends AppCompatActivity{

    Intent i;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("USERNAME");

    }

    private void relogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("USERNAME");
        editor.remove("PASSWORD");
        editor.remove("CHKREMEMBER");
        editor.commit();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView(){

        findViewById(R.id.doiTacLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, DoiTacActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", username);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        findViewById(R.id.nhapHangLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, NhapHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", username);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        findViewById(R.id.khohangLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, KhoHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", username);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        findViewById(R.id.xuatHangLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, XuatHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", username);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        findViewById(R.id.thongKeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, ThongKeActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_setting_logout:
                relogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
