package com.duan1.Assignment_single_team.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HDNHChiTietDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HoaDonNHDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.QuanLyNhapHang.XacNhanHDNhapHangFragment;
import com.duan1.Assignment_single_team.QuanLyNhapHang.ThemHoaDonNhapHangFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHoaDon;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerView_CustomAdapter_HoaDon extends RecyclerView.Adapter<RecyclerView_CustomAdapter_HoaDon.ViewHolder>{

    private MaterialDialog mAnimatedDialog;
    private Context context;
    private List<mHoaDon> list;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private FragmentCommunication fragmentCommunicator;
    private View view;

    public RecyclerView_CustomAdapter_HoaDon(Context context, List<mHoaDon> list, FragmentCommunication fragmentCommunication) {
        this.context = context;
        this.list = list;
        this.fragmentCommunicator = fragmentCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_hoadon, parent, false);
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

        holder.imgRemove.setImageResource(R.drawable.ic_remove);
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HoaDonNHDAO hoaDonNHDAO = new HoaDonNHDAO(context);
                final mHoaDon m = list.get(position);
                mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Xóa Hoá Đơn?")
                        .setMessage("Bạn chắc chắn muốn xóa ?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (hoaDonNHDAO.deleteHD(m) < 1){
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }else {

                                    HDNHChiTietDAO HDNHChiTietDAO = new HDNHChiTietDAO(context);
                                    int result = HDNHChiTietDAO.deleteHDCT(m.getMahoadon());

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



        holder.cardView.setCardBackgroundColor(Color.RED);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HDNHChiTietDAO HDNHChiTietDAO = new HDNHChiTietDAO(context);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                if(HDNHChiTietDAO.checkSizeHDCT(list.get(position).getMahoadon())){
                    XacNhanHDNhapHangFragment myFragment = new XacNhanHDNhapHangFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_nhap_hang, myFragment).addToBackStack(null).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("MAHD",list.get(position).getMahoadon());
                    myFragment.setArguments(bundle);
                }else {
                    ThemHoaDonNhapHangFragment fragmentB = new ThemHoaDonNhapHangFragment();
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
        private ImageView imgRemove;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nhanvien = itemView.findViewById(R.id.custom_layout_adapter_hoadon_nhanvien);
            mahoadon = itemView.findViewById(R.id.custom_layout_adapter_hoadon_mahoadon);
            ngaytao = itemView.findViewById(R.id.custom_layout_adapter_hoadon_ngaytao);
            ghichu = itemView.findViewById(R.id.custom_layout_adapter_hoadon_ghichu);
            imgRemove = itemView.findViewById(R.id.custom_layout_adapter_hoadon_imgremove);

            cardView = itemView.findViewById(R.id.hoadonnhap_cardview);
        }
    }

    public void updateData(List<mHoaDon> newList){
        this.list = newList;
        notifyDataSetChanged();
    }
}
