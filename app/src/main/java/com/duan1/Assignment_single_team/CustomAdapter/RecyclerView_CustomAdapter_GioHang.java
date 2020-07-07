package com.duan1.Assignment_single_team.CustomAdapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mGioHang;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerView_CustomAdapter_GioHang extends RecyclerView.Adapter<RecyclerView_CustomAdapter_GioHang.ViewHolder>{

    private Context context;
    private List<mGioHang> list;

    public RecyclerView_CustomAdapter_GioHang(Context context, List<mGioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_ds_giohang, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        HangHoaDAO hangHoaDAO = new HangHoaDAO(context);

        holder.txtTenHang.setText(hangHoaDAO.getNameHangHoaByMaHang(list.get(position).getMahang()));
        holder.txtGia.setText(String.valueOf(list.get(position).getGiahang()));
        holder.txtSoLuong.setText(String.valueOf(list.get(position).getSoluong()));

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        long tong = (long) (list.get(position).getSoluong() * list.get(position).getGiahang());
        String tongtien = en.format(tong);
        holder.txtTongTien.setText(tongtien);

        holder.imgRemove.setImageResource(R.drawable.ic_remove);
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTenHang;
        private TextView txtSoLuong;
        private TextView txtGia;
        private TextView txtTongTien;
        private ImageView imgRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHang = itemView.findViewById(R.id.custom_layout_giohang_tenhang);
            txtSoLuong = itemView.findViewById(R.id.custom_layout_giohang_soluong);
            txtGia = itemView.findViewById(R.id.custom_layout_giohang_giahang);
            txtTongTien = itemView.findViewById(R.id.custom_layout_giohang_tongtien);
            imgRemove = itemView.findViewById(R.id.custom_layout_giohang_imgRemove);
        }
    }
}
