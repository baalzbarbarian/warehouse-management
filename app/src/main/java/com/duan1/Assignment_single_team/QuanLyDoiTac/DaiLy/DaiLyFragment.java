package com.duan1.Assignment_single_team.QuanLyDoiTac.DaiLy;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_DaiLy;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.DaiLyDAO;
import com.duan1.Assignment_single_team.R;

import com.duan1.Assignment_single_team.mModel.mDaiLy;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaiLyFragment extends Fragment{
    private static final String TAG = "DaiLyFragment";
    RecyclerView recyclerView;
    List<mDaiLy> mDaiLyList;
    DaiLyDAO daiLyDAO;
    RecyclerView_CustomAdapter_DaiLy adapter;

    public DaiLyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dai_ly, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_DaiLy);
        daiLyDAO = new DaiLyDAO(getActivity());
        mDaiLyList = daiLyDAO.getAllDaiLy();

        adapter = new RecyclerView_CustomAdapter_DaiLy(mDaiLyList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_daily, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_menu_add_daily){
            AddDaiLyFragment addDaiLyFragment = new AddDaiLyFragment();
            FragmentManager fragmentManager = getFragmentManager();
            addDaiLyFragment.show(fragmentManager, TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_doi_tac, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
