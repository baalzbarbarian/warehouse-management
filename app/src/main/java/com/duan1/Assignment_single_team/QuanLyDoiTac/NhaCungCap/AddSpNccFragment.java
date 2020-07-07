package com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mNcc;
import com.duan1.Assignment_single_team.mModel.mSpNcc;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSpNccFragment extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    View view;
    Toolbar toolbar;
    List<mNcc> listSpnNcc;
    List<String> listMaLoaiHangNcc;
    SpNccDAO spNccDAO;
    String maNcc;
    NccDAO nccDAO;
    mSpNcc mSpNcc;

    //View
    TextInputEditText txtLoaiSp;
    TextInputEditText txtMaHang;
    TextInputEditText txtTenHang;
    TextInputEditText txtGiaHang;
    ImageView imgViewLogo;

    //Load image
    Bitmap bitmapImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_sp_ncc, container, false);

        initView();
        toolbar = view.findViewById(R.id.toolbar_add_ncc);

        return view;
    }

    private void initView() {

         final Spinner spnNcc = view.findViewById(R.id.dialog_custom_product_ncc_spinner_tenncc);
         txtLoaiSp = view.findViewById(R.id.dialog_custom_product_ncc_spinner_maloaisp);
         txtMaHang = view.findViewById(R.id.dialog_custom_product_ncc_masp);
         txtTenHang = view.findViewById(R.id.dialog_custom_product_ncc_tensp);
         txtGiaHang = view.findViewById(R.id.dialog_custom_product_ncc_giasp);
         imgViewLogo = view.findViewById(R.id.dialog_custom_product_ncc_imgViewLogoSpNcc);

        nccDAO = new NccDAO(getActivity());

        listSpnNcc = nccDAO.getMaNcc();
        mNcc m = new mNcc("Mã NCC", "Tên NCC","","","",null);
        listSpnNcc.add(0, m);

        ArrayAdapter<mNcc> dataAdapter = new ArrayAdapter<mNcc>(getActivity(),
                android.R.layout.simple_spinner_item, listSpnNcc){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNcc.setAdapter(dataAdapter);

        spnNcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maNcc = listSpnNcc.get(position).getMancc();

                if (spnNcc.getSelectedItemPosition() != 0){
                    listMaLoaiHangNcc = nccDAO.getMaLoaiHangCuaNcc(maNcc);
                    txtLoaiSp.setText(listMaLoaiHangNcc.get(0));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle("Sản Phẩm Của NCC");
        toolbar.inflateMenu(R.menu.option_menu_edit_dai_ly);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (insertNcc()){
                    loadFragment(new NhaCungCapFragment());
                    dismiss();
                }
                return false;
            }
        });
    }

    private boolean insertNcc() {
        spNccDAO = new SpNccDAO(getActivity());
        mSpNcc = new mSpNcc();

        if (!validateMaSp() | !validateTenSp() | !validateGiaSp()){
            return false;
        }else {
            mSpNcc.setMasp(txtMaHang.getText().toString());
            mSpNcc.setTensp(txtTenHang.getText().toString());
            mSpNcc.setGiasp(Double.parseDouble(txtGiaHang.getText().toString()));
            mSpNcc.setMaloaisp(txtLoaiSp.getText().toString());
            mSpNcc.setMancc(maNcc);
            mSpNcc.setLogospncc(imageViewToByte(imgViewLogo));

            if (!spNccDAO.checkSpExists(txtMaHang.getText().toString())){
                Toast.makeText(getActivity(), "Mã Sản Phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                if (spNccDAO.inserSpNcc(mSpNcc) > 0){
                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
    }

    private boolean validateMaSp(){
        spNccDAO = new SpNccDAO(getActivity());

        String maSpNcc = txtMaHang.getText().toString().trim();
        if (maSpNcc.isEmpty()) {
            txtMaHang.setError("Không được trống");
            return false;
        }else if (!spNccDAO.checkSpExists(maSpNcc)){
            txtMaHang.setError("Mã Hàng đã tồn tại");
            return false;
        }else {
            txtMaHang.setError(null);
            return true;
        }
    }

    private boolean validateTenSp(){
        String tenHang = txtTenHang.getText().toString();
        if (tenHang.isEmpty()) {
            txtTenHang.setError("Không được trống");
            return false;
        } else {
            txtTenHang.setError(null);
            return true;
        }
    }

    private boolean validateGiaSp(){
        String tenHang = txtGiaHang.getText().toString();
        if (tenHang.isEmpty()) {
            txtGiaHang.setError("Không được trống");
            return false;
        } else {
            txtTenHang.setError(null);
            return true;
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_doi_tac, fragment);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgViewLogo.setImageBitmap(bitmapImage);
            }
        }
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
