package com.duan1.Assignment_single_team.QuanLyKhoHang;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_HangHoaChiTiet;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHangHoa;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HangHoaChiTietFragment extends Fragment {


    public HangHoaChiTietFragment() {
        // Required empty public constructor
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hang_hoa_chi_tiet, container, false);

        String maLoaihang = getArguments().getString("MALOAIHANG");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Hàng tồn kho");

        HangHoaDAO hangHoaDAO = new HangHoaDAO(getActivity());
        List<mHangHoa> list = hangHoaDAO.getAllHangHoaByMaLoaiHang(maLoaihang);
        RecyclerView_CustomAdapter_HangHoaChiTiet adapter = new RecyclerView_CustomAdapter_HangHoaChiTiet(getActivity(), list);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_hanghoachitiet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
