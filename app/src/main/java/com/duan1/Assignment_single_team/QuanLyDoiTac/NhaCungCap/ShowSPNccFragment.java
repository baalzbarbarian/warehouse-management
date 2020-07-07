package com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_ShowSpNcc;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mSpNcc;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSPNccFragment extends Fragment {

    private static final String TAG = "ShowSPNccFragment";
    private String code;
    private SpNccDAO db;
    private List<mSpNcc> list;
    private RecyclerView recyclerView;
    private RecyclerView_CustomAdapter_ShowSpNcc adapter;

    public ShowSPNccFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_spncc, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_SpNcc);
        recyclerView.setHasFixedSize(true);

        code = getArguments().getString("CODE");

        db = new SpNccDAO(getActivity());
        list = db.getSpByMaNcc(code);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerView_CustomAdapter_ShowSpNcc(list, getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
