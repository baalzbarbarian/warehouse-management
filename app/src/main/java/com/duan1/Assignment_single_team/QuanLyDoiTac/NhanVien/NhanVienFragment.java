package com.duan1.Assignment_single_team.QuanLyDoiTac.NhanVien;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_Employee;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mEmployee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NhanVienFragment extends Fragment{
    private static final String TAG = "NhanVienFragment";
    private RecyclerView recyclerView;
    List<mEmployee> mEmployeelist;
    NhanVienDAO db;
    RecyclerView_CustomAdapter_Employee adapter;

    View view;
    public NhanVienFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        String username = "";
        try {
             String getUsername = getArguments().getString("USERNAME");
             username = getUsername;
        }catch (Exception e){
            Log.e(TAG, "onCreateView: "+e);
        }

        setRecyclerView();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        final String finalUsername = username;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalUsername.equals("admin")){
                    openDialog();
                }else {
                    Toast.makeText(getActivity(), "Tài khoản không có quyền thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void setRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView_NhanVien);

        db = new NhanVienDAO(getActivity());
        mEmployeelist = new ArrayList<>();
        mEmployeelist = db.getAllEmployee();

        adapter = new RecyclerView_CustomAdapter_Employee(mEmployeelist, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void openDialog(){
        EmployeeRegistrationFragment.display(getFragmentManager());
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new NhanVienDAO(getActivity());
        mEmployeelist = new ArrayList<>();
        mEmployeelist = db.getAllEmployee();
        adapter.updateEmployeeListItems(mEmployeelist);
        adapter.notifyDataSetChanged();
    }
}
