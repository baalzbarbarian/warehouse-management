package com.duan1.Assignment_single_team.QuanLyKhoHang;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_LoaiHang;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoaiHangFragment extends Fragment {


    public LoaiHangFragment() {
        // Required empty public constructor
    }

    private View view;
    RecyclerView recyclerView;
    LoaiHangDAO db;
    List<mLoaiHang> list;
    RecyclerView_CustomAdapter_LoaiHang adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loai_hang, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Loại Hàng");

        setUpRecyclerView();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddLoaiSp();
            }
        });

        return view;
    }

    private void setUpRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView_loaihang);

        db = new LoaiHangDAO(getActivity());
        list = db.getAllLoaiHang();
        adapter = new RecyclerView_CustomAdapter_LoaiHang(list, getActivity(), communication);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showDialogAddLoaiSp(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom_add_khohang_loai_hang);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextInputEditText txtMaLoaiSp  = dialog.findViewById(R.id.dialog_custom_add_khohang_maloaisp);
        final TextInputEditText txtTenLoaiSp = dialog.findViewById(R.id.dialog_custom_add_khohang_tenloaisp);
        final TextInputEditText txtViTri = dialog.findViewById(R.id.dialog_custom_add_khohang_vitri);

        Button btnChange = dialog.findViewById(R.id.dialog_custom_add_khohang_btnsave);
        Button btnCancel = dialog.findViewById(R.id.dialog_custom_add_khohang_btncancel);

        dialog.show();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(txtMaLoaiSp)){
                    txtMaLoaiSp.setError("Không được trống");
                }else if (isEmpty(txtTenLoaiSp)){
                    txtTenLoaiSp.setError("Không được trống");
                }else if (isEmpty(txtViTri)){
                    txtViTri.setError("Không được trống");
                }else {
                    mLoaiHang m = new mLoaiHang(txtMaLoaiSp.getText().toString(),
                            txtTenLoaiSp.getText().toString(),
                            txtViTri.getText().toString());
                    LoaiHangDAO db = new LoaiHangDAO(getActivity());
                    if (db.inserLoaiHang(m) > 0){
                        list = db.getAllLoaiHang();
                        Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        onResume();
                    }else {
                        Toast.makeText(getActivity(), "Thêm Thất bại\n Vui lòng liên hệ bên sở hữu phần mềm.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateDate(list);
        adapter.notifyDataSetChanged();
    }

    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, String code) {
            HangHoaChiTietFragment fragment = new HangHoaChiTietFragment();
            Bundle bundle = new Bundle();
            bundle.putString("MALOAIHANG", code);
            fragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container_khohang, fragment).addToBackStack(null).commit();
        }
    };

}
