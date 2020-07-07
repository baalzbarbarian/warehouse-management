package com.duan1.Assignment_single_team.QuanLyThongKe.ThongKeFragment;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonXHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.ThongKeDAO;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TKDTThangTrongNam extends Fragment implements OnChartValueSelectedListener {


    public TKDTThangTrongNam() {
        // Required empty public constructor
    }

    private CombinedChart mChart;
    private List<String> monthList = new ArrayList<>();
    private List<Long> dthuList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tkdtthang_trong_nam, container, false);

        mChart = view.findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        ThongKeDAO hoaDonXHDAO = new ThongKeDAO(getActivity());
        List<mTKDTTheoThang> listDTThang = hoaDonXHDAO.tkdtThang();

        for (int i = 0; i < listDTThang.size(); i++){
            monthList.add(listDTThang.get(i).getThangtrongnam());
            dthuList.add(listDTThang.get(i).getDoanhthuthang());
        }

//        final List<String> xLabel = new ArrayList<>();
//        xLabel.add("Jan");
//        xLabel.add("Feb");
//        xLabel.add("Mar");
//        xLabel.add("Apr");
//        xLabel.add("May");
//        xLabel.add("Jun");
//        xLabel.add("Jul");
//        xLabel.add("Aug");
//        xLabel.add("Sep");
//        xLabel.add("Oct");
//        xLabel.add("Nov");
//        xLabel.add("Dec");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(monthList.get((int) value % monthList.size()));
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();

        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        long doanhThu = (long) e.getY();
        String doanhThuStr = en.format(doanhThu);

        Toast.makeText(getActivity(), "Doanh thu tháng "+ monthList.get((int) h.getX())
                + " : "+doanhThuStr, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private DataSet dataChart() {

        LineData d = new LineData();
        int[] data = new int[dthuList.size()];
        for (int i = 0; i < dthuList.size(); i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                data[i] = Math.toIntExact(dthuList.get(i));
            }
        }

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < dthuList.size(); index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Thông kê doanh thu các tháng trong năm");
        set.setColor(Color.RED);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.RED);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

}
