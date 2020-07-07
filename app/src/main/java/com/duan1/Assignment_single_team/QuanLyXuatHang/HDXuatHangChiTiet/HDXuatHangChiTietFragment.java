package com.duan1.Assignment_single_team.QuanLyXuatHang.HDXuatHangChiTiet;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_HDXHChiTiet;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDXHChiTietDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mGioHang;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HDXuatHangChiTietFragment extends Fragment {


    public HDXuatHangChiTietFragment() {
        // Required empty public constructor
    }

    String mahdxh;
    RecyclerView recyclerView;
    RecyclerView_CustomAdapter_HDXHChiTiet adapter;
    HDXHChiTietDAO hdxhChiTietDAO;
    List<mGioHang> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hdxuat_hang_chi_tiet, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_hdxhchitiet);
        mahdxh = getArguments().getString("MAHD");
        hdxhChiTietDAO = new HDXHChiTietDAO(getActivity());
        list = hdxhChiTietDAO.getHDXHChiTietByMaHD(mahdxh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerView_CustomAdapter_HDXHChiTiet(getActivity(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
