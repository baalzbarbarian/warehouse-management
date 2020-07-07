package com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.CustomAdapter.RecyclerView_CustomAdapter_Ncc;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.duan1.Assignment_single_team.mModel.mNcc;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class NhaCungCapFragment extends Fragment {

    private static final String TAG = "NhaCungCapFragment";
    View view;
    RecyclerView recyclerView;
    RecyclerView_CustomAdapter_Ncc adapter;
    NccDAO db;
    List<mNcc> list;

    int pos;

    private Dialog dialog;
    private Bitmap bitmapImage;
    private ImageView imgPickLogo;
    private ProgressDialog progressDialog;
    private Handler handle;
    private String maHangNcc = "";
    private List<mLoaiHang> listLoaiHang;

    //    private RecyclerView_CustomAdapter_Ncc.OnItemClick onItemClick;
    private View.OnClickListener onClickListener;
    public NhaCungCapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nha_cung_cap, container, false);
        initView();

        return view;
    }

    private void showDialog(final int position){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom_edit_ncc);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextInputEditText txtMaNcc      = dialog.findViewById(R.id.dialog_custom_change_ncc_maNcc);
        final TextInputEditText txtTenNcc     = dialog.findViewById(R.id.dialog_custom_change_ncc_tenNcc);
        final TextInputEditText txtDiaChi     = dialog.findViewById(R.id.dialog_custom_change_ncc_diaChiNcc);
        final TextInputEditText txtPhone      = dialog.findViewById(R.id.dialog_custom_change_ncc_phoneNcc);
        final Spinner txtMaHangNcc  = dialog.findViewById(R.id.dialog_custom_change_ncc_maHangNcc);
        imgPickLogo = dialog.findViewById(R.id.dialog_custom_change_ncc_imgPickLogoView);

        //spinner
        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getActivity());
        listLoaiHang = loaiHangDAO.getAllLoaiHang();
        ArrayAdapter<mLoaiHang> dataAdapter = new ArrayAdapter<mLoaiHang>(getActivity(),
                android.R.layout.simple_spinner_item, listLoaiHang);
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

        Button btnChange = dialog.findViewById(R.id.dialog_custom_change_ncc_btnSave);
        Button btnCancel = dialog.findViewById(R.id.dialog_custom_change_ncc_btnCancel);

        txtMaNcc.setText(list.get(position).getMancc());
        txtTenNcc.setText(list.get(position).getTenncc());
        txtDiaChi.setText(list.get(position).getDiachi());
        txtPhone.setText(list.get(position).getPhonencc());

        //Lấy ảnh dạng byte trong SQL rồi chuyển thành bitmap để set vào ImageView
        final byte[] imgLogo = list.get(position).getLogoncc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgLogo, 0, imgLogo.length);
        imgPickLogo.setImageBitmap(bitmap);

        dialog.show();

        imgPickLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGallery();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(txtTenNcc)){
                    txtTenNcc.setError("Không được trống");
                }else if (isEmpty(txtPhone)){
                    txtPhone.setError("Không được trống");
                }else if (isEmpty(txtDiaChi)){
                    txtDiaChi.setError("Không được trống");
                }else {
                    mNcc m = new mNcc();
                    m.setMancc(list.get(position).getMancc());
                    m.setTenncc(txtTenNcc.getText().toString());
                    m.setDiachi(txtDiaChi.getText().toString());
                    m.setPhonencc(txtPhone.getText().toString());
                    m.setLogoncc(imageViewToByte(imgPickLogo));
                    m.setMahangncc(maHangNcc);
                    db = new NccDAO(getActivity());

                    if (db.updateNcc(m) < 0){
                        Toast.makeText(getActivity(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        showProgressDialog();
                        onResume();
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

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Đang lưu ảnh...");
        progressDialog.setTitle("Đang lưu dữ liệu. Vui lòng đợi !");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog
                            .getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Sửa dữ liệu thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(9);
            }
        };
    }

    private void initView(){

        recyclerView = view.findViewById(R.id.recyclerView_Ncc);
        db = new NccDAO(getActivity());
        list = db.getAllNcc();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerView_CustomAdapter_Ncc(list, getActivity(), onClickListener, communication);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = recyclerView.indexOfChild(v);
                showDialog(pos);
            }
        });

    }

    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, String code) {
            ShowSPNccFragment fragment = new ShowSPNccFragment();
            Bundle bundle = new Bundle();
            bundle.putString("CODE", code);
            fragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container_doi_tac_ncc_loai_hang, fragment).commit();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_ncc, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_menu_add_daily){
            LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getActivity());
            List<mLoaiHang> loaiHangs = loaiHangDAO.getAllLoaiHang();
            if (loaiHangs.size() < 1){
                Toast.makeText(getActivity(), "Vui lòng thêm Loại Hàng trong Kho trước khi thêm nhà NCC", Toast.LENGTH_LONG).show();
            }else {
                AddNccFragment addNccFragment = new AddNccFragment();
                FragmentManager fragmentManager = getFragmentManager();
                addNccFragment.show(fragmentManager, TAG);
            }

        }else if (item.getItemId() == R.id.option_menu_add_hang_ncc){
            NccDAO nccDAO = new NccDAO(getActivity());
            List<mNcc> listNccs = nccDAO.getAllNcc();
            if (listNccs.size()<1){
                Toast.makeText(getActivity(), "Vui lòng tạo thông tin NCC trước khi thêm sản phẩm cho NCC", Toast.LENGTH_LONG).show();
            }else {
//                showDialogAddSpNcc();

                AddSpNccFragment fragment = new AddSpNccFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragment.show(fragmentManager, TAG);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new NccDAO(getActivity());
        list = db.getAllNcc();
        adapter.updateDate(list);
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
                imgPickLogo.setImageBitmap(bitmapImage);
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

    //Dialog thêm sản phẩm của nhà cung cấp.
//    List<mNcc> listSpnNcc;
//    List<String> listMaLoaiHangNcc;
//    SpNccDAO spNccDAO;
//    String maNcc;
//    NccDAO nccDAO;
//    mSpNcc mSpNcc;
//    private void showDialogAddSpNcc(){
////        final Dialog dialog = new Dialog(getActivity());
////        dialog.setContentView(R.layout.dialog_custom_add_hang_ncc);
////        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        final Spinner spnNcc = dialog.findViewById(R.id.dialog_custom_product_ncc_spinner_tenncc);
//        final TextInputEditText txtLoaiSp = dialog.findViewById(R.id.dialog_custom_product_ncc_spinner_maloaisp);
//        final TextInputEditText txtMaHang = dialog.findViewById(R.id.dialog_custom_product_ncc_masp);
//        final TextInputEditText txtTenHang = dialog.findViewById(R.id.dialog_custom_product_ncc_tensp);
//        final TextInputEditText txtGiaHang = dialog.findViewById(R.id.dialog_custom_product_ncc_giasp);
//         nccDAO = new NccDAO(getActivity());
//
//        //Set adapter cho Spinner Nhà cung cấp
//        listSpnNcc = nccDAO.getMaNcc();
//        ArrayAdapter<mNcc> dataAdapter = new ArrayAdapter<mNcc>(getActivity(),
//                android.R.layout.simple_spinner_item, listSpnNcc);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnNcc.setAdapter(dataAdapter);
//
//        dialog.show();
//
//        spnNcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                maNcc = listSpnNcc.get(position).getMancc();
//
//                listMaLoaiHangNcc = nccDAO.getMaLoaiHangCuaNcc(maNcc);
//                txtLoaiSp.setText(listMaLoaiHangNcc.get(0));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        dialog.findViewById(R.id.dialog_custom_product_ncc_btnsave).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                spNccDAO = new SpNccDAO(getActivity());
//                mSpNcc = new mSpNcc();
//                mSpNcc.setMasp(txtMaHang.getText().toString());
//                mSpNcc.setTensp(txtTenHang.getText().toString());
//                mSpNcc.setGiasp(Double.parseDouble(txtGiaHang.getText().toString()));
//                mSpNcc.setMaloaisp(txtLoaiSp.getText().toString());
//                mSpNcc.setMancc(maNcc);
//
//                if (!spNccDAO.checkSpExists(txtMaHang.getText().toString())){
//                    Toast.makeText(getActivity(), "Mã Sản Phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
//                }else {
//                    if (spNccDAO.inserSpNcc(mSpNcc) > 1){
//                        Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }else {
//                        Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//        dialog.findViewById(R.id.dialog_custom_product_ncc_btncancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//    }

}
