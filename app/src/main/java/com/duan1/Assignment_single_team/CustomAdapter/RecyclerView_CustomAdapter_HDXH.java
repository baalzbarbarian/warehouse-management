package com.duan1.Assignment_single_team.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDXHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonXHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.QuanLyXuatHang.HDXuatHangChiTiet.GioHangFragment;
import com.duan1.Assignment_single_team.QuanLyXuatHang.HDXuatHangChiTiet.HDXuatHangChiTietFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHDXHChiTiet;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerView_CustomAdapter_HDXH extends RecyclerView.Adapter<RecyclerView_CustomAdapter_HDXH.ViewHolder>{

    private static final String TAG = "HDXH_Adapter";
    private MaterialDialog mAnimatedDialog;
    private Context context;
    private List<mHDXHChiTiet> list;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private FragmentCommunication fragmentCommunicator;
    private View view;

    public RecyclerView_CustomAdapter_HDXH(Context context, List<mHDXHChiTiet> list, FragmentCommunication fragmentCommunication) {
        this.context = context;
        this.list = list;
        this.fragmentCommunicator = fragmentCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_hdxuathang, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        String name = nhanVienDAO.getNhanVienByUsername(list.get(position).getManhanvien());
        holder.nhanvien.setText(name);
        holder.mahoadon.setText(list.get(position).getMahoadon());
        holder.ngaytao.setText(sdf.format(list.get(position).getNgaytaohoadon()));
        holder.ghichu.setText(list.get(position).getGhichu());
        holder.daily.setText(list.get(position).getMadaily());
        holder.imgRemove.setImageResource(R.drawable.ic_remove);
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HoaDonXHDAO hoaDonDAO = new HoaDonXHDAO(context);
                final mHDXHChiTiet m = list.get(position);
                mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Xóa Hoá Đơn?")
                        .setMessage("Bạn chắc chắn muốn xóa ?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (hoaDonDAO.deleteHD(m) < 1){
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }else {

                                    HDXHChiTietDAO hdxhChiTietDAO = new HDXHChiTietDAO(context);
                                    int result = hdxhChiTietDAO.deleteHDXHCTByMaHDXH(m.getMahoadon());
                                    Log.e(TAG, "onClick: "+result);

                                    list.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();

                                }
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Bỏ qua", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setAnimation("delete_anim.json")
                        .build();

                mAnimatedDialog.show();
            }
        });




        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HDXHChiTietDAO hdChiTietDAO = new HDXHChiTietDAO(context);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                if(!hdChiTietDAO.checkHdExists(list.get(position).getMahoadon())){
                    HDXuatHangChiTietFragment myFragment = new HDXuatHangChiTietFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_xuat_hang, myFragment).addToBackStack(null).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("MAHD",list.get(position).getMahoadon());
                    myFragment.setArguments(bundle);
                }else {
                    GioHangFragment fragmentB = new GioHangFragment();
                    activity.getSupportFragmentManager().beginTransaction().commit();
                    fragmentCommunicator.respond(holder.getAdapterPosition(),list.get(holder.getAdapterPosition()).getMahoadon());
                    Bundle bundle = new Bundle();
                    bundle.putString("MAHD",list.get(position).getMahoadon());
                    fragmentB.setArguments(bundle);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nhanvien;
        private TextView mahoadon;
        private TextView ngaytao;
        private TextView ghichu;
        private TextView daily;
        private ImageView imgRemove;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nhanvien = itemView.findViewById(R.id.custom_layout_adapter_hdxh_nhanvien);
            mahoadon = itemView.findViewById(R.id.custom_layout_adapter_hdxh_mahoadon);
            ngaytao = itemView.findViewById(R.id.custom_layout_adapter_hdxh_ngaytao);
            ghichu = itemView.findViewById(R.id.custom_layout_adapter_hdxh_ghichu);
            daily = itemView.findViewById(R.id.custom_layout_adapter_hdxh_daily);
            imgRemove = itemView.findViewById(R.id.custom_layout_adapter_hdxh_imgremove);
            cardView = itemView.findViewById(R.id.hdxh_cardview);
        }
    }

    public void updateData(List<mHDXHChiTiet> newList){
        this.list = newList;
        notifyDataSetChanged();
    }
}