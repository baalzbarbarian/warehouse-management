package com.duan1.Assignment_single_team.QuanLyKhoHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_LoaiHang;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class KhoHangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho_hang);

        loadFragment(new LoaiHangFragment());

    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_khohang, fragment);
        transaction.commit();
    }

}
