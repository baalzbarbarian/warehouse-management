package com.duan1.Assignment_single_team.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mGioHang;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerView_CustomAdapter_HDXHChiTiet extends RecyclerView.Adapter<RecyclerView_CustomAdapter_HDXHChiTiet.ViewHolder>{

    private static final String TAG = "HDXH_Adapter";
    private MaterialDialog mAnimatedDialog;
    private Context context;
    private List<mGioHang> list;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private FragmentCommunication fragmentCommunicator;
    private View view;

    public RecyclerView_CustomAdapter_HDXHChiTiet(Context context, List<mGioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_ds_giohang, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        HangHoaDAO hangHoaDAO = new HangHoaDAO(context);
        String name = hangHoaDAO.getNameHangHoaByMaHang(list.get(position).getMahang());

        holder.tenhang.setText(name);
        holder.giahang.setText(String.valueOf(list.get(position).getGiahang()));
        holder.soluong.setText(String.valueOf(list.get(position).getSoluong()));

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        long giasp = (long) (list.get(position).getGiahang() * list.get(position).getSoluong());
        holder.tongtien.setText(en.format(giasp));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tenhang;
        private TextView giahang;
        private TextView soluong;
        private TextView tongtien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenhang = itemView.findViewById(R.id.custom_layout_giohang_tenhang);
            giahang = itemView.findViewById(R.id.custom_layout_giohang_giahang);
            soluong = itemView.findViewById(R.id.custom_layout_giohang_soluong);
            tongtien = itemView.findViewById(R.id.custom_layout_giohang_tongtien);
        }
    }
}