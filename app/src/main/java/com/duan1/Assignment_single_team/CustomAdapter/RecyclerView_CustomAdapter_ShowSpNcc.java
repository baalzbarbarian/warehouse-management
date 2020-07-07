package com.duan1.Assignment_single_team.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mNcc;
import com.duan1.Assignment_single_team.mModel.mSpNcc;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerView_CustomAdapter_ShowSpNcc extends RecyclerView.Adapter<RecyclerView_CustomAdapter_ShowSpNcc.ViewHolder>{

    private MaterialDialog mAnimatedDialog;
    private List<mSpNcc> list;
    private Context context;
    private mSpNcc m;

    public RecyclerView_CustomAdapter_ShowSpNcc(List<mSpNcc> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_adapter_ds_sp_ncc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        byte[] logoNcc = list.get(position).getLogospncc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(logoNcc, 0, logoNcc.length);
        holder.imgShowLogo.setImageBitmap(bitmap);

        holder.txtTenSp.setText(list.get(position).getTensp());

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        long giasp = (long) list.get(position).getGiasp();

        holder.txtGiaSp.setText(en.format(giasp)+" vnđ");
        holder.imgDeleteItem.setImageResource(R.drawable.ic_remove);
        holder.imgDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = list.get(position);
                final SpNccDAO db = new SpNccDAO(context);

                mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Xóa Nhà Sản Phẩm?")
                        .setMessage("Bạn chắc chắn muốn xóa ?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (db.deleteSpNccByMaSp(m) < 1){
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }else {
                                    list.remove(position);
                                    updateDate(db.getSpByMaNcc(m.getMancc()));
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
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
    }

    public void updateDate(List<mSpNcc> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgShowLogo;
        private ImageView imgDeleteItem;
        private TextView txtTenSp;
        private TextView txtGiaSp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShowLogo = itemView.findViewById(R.id.ds_sp_ncc_imgLogoSp);
            txtTenSp    = itemView.findViewById(R.id.ds_sp_ncc_tensp);
            txtGiaSp    = itemView.findViewById(R.id.ds_sp_ncc_giaSp);
            imgDeleteItem = itemView.findViewById(R.id.imgDelete_xoaSpNcc);
        }
    }
}
