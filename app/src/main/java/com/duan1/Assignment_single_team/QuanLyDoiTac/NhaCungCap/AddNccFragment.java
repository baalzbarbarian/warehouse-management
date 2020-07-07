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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.provider.MediaStore;
import android.util.Log;
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

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.duan1.Assignment_single_team.mModel.mNcc;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNccFragment extends DialogFragment {


    private static final String TAG = "AddDaiLyFragment";
    private TextInputLayout txtMaNcc;
    private TextInputLayout txtTenNcc;
    private TextInputLayout txtDiaChiNcc;
    private TextInputLayout txtPhoneNcc;
    private Spinner txtMaHangNcc;
    private ImageView imgViewLogo;
    private ImageView imgViewPickLogo;
    private AppCompatButton btnClear;
    private List<mLoaiHang> listLoaiHang;
    private String maHangNcc;
    private mLoaiHang mLoaiHang;
    View view;
    public AddNccFragment() {
        // Required empty public constructor
    }

    NccDAO nccDAO;
    mNcc m;
    Toolbar toolbar;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_ncc, container, false);
        initView();
        toolbar = view.findViewById(R.id.toolbar_add_ncc);

        imgViewPickLogo.setOnClickListener(new View.OnClickListener() {
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

        return view;
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
        toolbar.setTitle("Thêm Nhà Cung Cấp");
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

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_doi_tac, fragment);
        transaction.commit();
    }

    private void initView(){
        txtMaNcc      = view.findViewById(R.id.txtLayout_maNcc);
        txtTenNcc     = view.findViewById(R.id.txtLayout_tenNcc);
        txtDiaChiNcc  = view.findViewById(R.id.txtLayout_diaChiNcc);
        txtPhoneNcc   = view.findViewById(R.id.txtLayout_phoneNcc);
        txtMaHangNcc  = view.findViewById(R.id.txtLayout_maHangNcc);
        imgViewLogo   = view.findViewById(R.id.img_view_logo_ncc);
        imgViewPickLogo   = view.findViewById(R.id.img_view_pick_logo_ncc);
//        btnClear      = view.findViewById(R.id.btn_clear_add_ncc);

        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getActivity());
        listLoaiHang = loaiHangDAO.getAllLoaiHang();
        mLoaiHang = new mLoaiHang("Chọn loại hàng", "Không bỏ trống","");
        listLoaiHang.add(0, mLoaiHang);
        ArrayAdapter<mLoaiHang> dataAdapter = new ArrayAdapter<mLoaiHang>(getActivity(),
                android.R.layout.simple_spinner_item, listLoaiHang){
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
        txtMaHangNcc.setAdapter(dataAdapter);

        txtMaHangNcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maHangNcc = listLoaiHang.get(txtMaHangNcc.getSelectedItemPosition()).getMaloaihang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public int checkPositionTheLoai(String strTheLoai){
        for (int i = 0; i <listLoaiHang.size(); i++){
            if (strTheLoai.equals(listLoaiHang.get(i).getMaloaihang())){
                return i;
            }
        }
        return 0;
    }

    private boolean insertNcc(){
        nccDAO = new NccDAO(getActivity());

        if (!validateDiaChiNcc() | !validateMaNcc() | !validatePhoneNcc() | !validateTenNcc()){
            return false;
        }else if (txtMaHangNcc.getSelectedItem().equals(txtMaHangNcc.getItemAtPosition(0))){
            Toast.makeText(getActivity(), "Vui lòng chọn loại hàng", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String maNcc = txtMaNcc.getEditText().getText().toString().trim();
            String tenNcc = txtTenNcc.getEditText().getText().toString().trim();
            String diaChiNcc = txtDiaChiNcc.getEditText().getText().toString().trim();
            String phoneNcc = txtPhoneNcc.getEditText().getText().toString().trim();

            if (imgViewLogo.getDrawable() == null){
                imgViewLogo.setImageResource(R.drawable.ic_image);
            }

            m = new mNcc(maNcc, tenNcc, diaChiNcc, phoneNcc, maHangNcc, imageViewToByte(imgViewLogo));
            if (nccDAO.inserNcc(m) < 1){
                Toast.makeText(getActivity(), "Thêm NCC Thất Bại", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                Toast.makeText(getActivity(), "Thêm NCC Lý Thành Công", Toast.LENGTH_SHORT).show();
                return true;
            }

        }

    }

    private boolean validateMaNcc() {
        nccDAO = new NccDAO(getActivity());

        String maNcc = txtMaNcc.getEditText().getText().toString().trim();

        if (maNcc.isEmpty()) {
            txtMaNcc.setError("Không được trống");
            return false;
        }else if (!nccDAO.checkNccExists(maNcc)){
            txtMaNcc.setError("Mã NCC đã tồn tại");
            return false;
        }else {
            txtMaNcc.setError(null);
            return true;
        }
    }

    private boolean validateTenNcc() {
        String maNcc = txtTenNcc.getEditText().getText().toString().trim();

        if (maNcc.isEmpty()) {
            txtTenNcc.setError("Không được trống");
            return false;
        } else {
            txtTenNcc.setError(null);
            return true;
        }
    }

    private boolean validateDiaChiNcc() {
        String maNcc = txtDiaChiNcc.getEditText().getText().toString().trim();

        if (maNcc.isEmpty()) {
            txtDiaChiNcc.setError("Không được trống");
            return false;
        } else {
            txtDiaChiNcc.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNcc() {
        String maNcc = txtPhoneNcc.getEditText().getText().toString().trim();

        if (maNcc.isEmpty()) {
            txtPhoneNcc.setError("Không được trống");
            return false;
        } else {
            txtPhoneNcc.setError(null);
            return true;
        }
    }

//    private boolean validateMaHangNcc() {
//        String maNcc = txtMaHangNcc.getEditText().getText().toString().trim();
//
//        if (maNcc.isEmpty()) {
//            txtMaHangNcc.setError("Không được trống");
//            return false;
//        } else {
//            txtMaHangNcc.setError(null);
//            return true;
//        }
//    }


        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
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
