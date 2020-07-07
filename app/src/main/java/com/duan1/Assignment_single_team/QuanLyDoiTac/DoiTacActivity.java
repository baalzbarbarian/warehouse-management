package com.duan1.Assignment_single_team.QuanLyDoiTac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.duan1.Assignment_single_team.QuanLyDoiTac.DaiLy.DaiLyFragment;
import com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap.NhaCungCapFragment;
import com.duan1.Assignment_single_team.QuanLyDoiTac.NhanVien.NhanVienFragment;
import com.duan1.Assignment_single_team.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoiTacActivity extends AppCompatActivity {

    private ActionBar toolbar;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_tac);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("USERNAME");

        toolbar = getSupportActionBar();

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedLestener);

        toolbar.setTitle("Nhà Cung Cấp");
        loadFragment(new NhaCungCapFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedLestener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.navigation_ncc:
                    toolbar.setTitle("Nhà Cung Cấp");
                    loadFragment(new NhaCungCapFragment());
                    return true;
                case R.id.navigation_daiLy:
                    toolbar.setTitle("Đại Lý");
                    loadFragment(new DaiLyFragment());
                    return true;
                case R.id.navigation_nv:
                    toolbar.setTitle("Nhân Viên");
                    loadFragment(new NhanVienFragment());
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment){

        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_doi_tac, fragment);
        fragment.setArguments(bundle);
        transaction.commit();
    }



}
