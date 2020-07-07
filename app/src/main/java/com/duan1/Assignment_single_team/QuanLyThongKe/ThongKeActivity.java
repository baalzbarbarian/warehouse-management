package com.duan1.Assignment_single_team.QuanLyThongKe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonXHDAO;
import com.duan1.Assignment_single_team.QuanLyThongKe.ThongKeFragment.TKDTThangTrongNam;
import com.duan1.Assignment_single_team.QuanLyThongKe.ThongKeFragment.ThongKeNgayFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mTKDTTheoThang;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThongKeActivity extends AppCompatActivity{

   ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Biểu đồ doanh thu các tháng");
        loadFragment(new TKDTThangTrongNam());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_thongke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings_tkdtthangtrongnam){
            actionBar.setTitle("Biểu đồ doanh thu các tháng");
            loadFragment(new TKDTThangTrongNam());
        }
        if (item.getItemId() == R.id.action_setting_tkdtngay){
            actionBar.setTitle("Thống kê trong tháng");
            loadFragment(new ThongKeNgayFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_thongke, fragment);
        transaction.commit();
    }

}
