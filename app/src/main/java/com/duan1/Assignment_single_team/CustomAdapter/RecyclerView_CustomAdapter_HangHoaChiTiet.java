package com.duan1.Assignment_single_team.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap.ShowSPNccFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mHangHoa;

import java.util.List;

public class RecyclerView_CustomAdapter_HangHoaChiTiet extends RecyclerView.Adapter<RecyclerView_CustomAdapter_HangHoaChiTiet.ViewHolder>{

    private Context context;
    private List<mHangHoa> list;
    private View view;


    public RecyclerView_CustomAdapter_HangHoaChiTiet(Context context, List<mHangHoa> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_hanghoachitiet, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtTenSp.setText(list.get(position).getTenhanghoa());
        holder.txtGiaSp.setText(String.valueOf(list.get(position).getGiahanghoa()));
        holder.txtSoLuongTonKho.setText(String.valueOf(list.get(position).getSoluong()));

        SpNccDAO spNccDAO = new SpNccDAO(context);
        byte[] imgLogo = spNccDAO.getLogoSpByMaSp(list.get(position).getMahanghoa());
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgLogo, 0, imgLogo.length);
        holder.imgLogo.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parentLayout;
        private TextView txtTenSp;
        private TextView txtGiaSp;
        private TextView txtSoLuongTonKho;
        private ImageView imgLogo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.khohang_spchitiet_parentLayout);
            txtTenSp = itemView.findViewById(R.id.khohang_spchitiet_tensp);
            txtGiaSp = itemView.findViewById(R.id.khohang_spchitiet_giasp);
            txtSoLuongTonKho = itemView.findViewById(R.id.khohang_spchitiet_soluongtonkho);
            imgLogo = itemView.findViewById(R.id.khohang_spchitiet_imglogo);

        }
    }
}
