package com.duan1.Assignment_single_team.QuanLyNhapHang.QLyHoaDon;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_HoaDon;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonNHDAO;
import com.duan1.Assignment_single_team.QuanLyNhapHang.ThemHoaDonNhapHangFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHoaDon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoaDonThangFragment extends Fragment {

    private static final String TAG = "HoaDonThangFragment";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private View view;
    private HoaDonNHDAO hoaDonNHDAO;
    private List<mHoaDon> list;
    private RecyclerView_CustomAdapter_HoaDon adapter;
    RecyclerView recyclerView;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hoa_don_thang, container, false);
        username = getArguments().getString("USERNAME");
        initView();

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView_hoadonnhap_thang);
        hoaDonNHDAO = new HoaDonNHDAO(getActivity());
        list = hoaDonNHDAO.getAllHDInMonth(getCurrentDate());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerView_CustomAdapter_HoaDon(getActivity(), list, communication);
        recyclerView.setAdapter(adapter);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        return date;
    }

    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, String code) {
            ThemHoaDonNhapHangFragment fragment = new ThemHoaDonNhapHangFragment();
            Bundle bundle = new Bundle();
            bundle.putString("MAHD", code);
            fragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
            fragment.show(manager, TAG);
        }
    };

}
