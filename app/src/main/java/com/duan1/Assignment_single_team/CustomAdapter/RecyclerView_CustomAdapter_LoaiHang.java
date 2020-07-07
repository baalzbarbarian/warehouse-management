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

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.HangHoaDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.LoaiHangDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap.ShowSPNccFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mLoaiHang;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class RecyclerView_CustomAdapter_LoaiHang extends RecyclerView.Adapter<RecyclerView_CustomAdapter_LoaiHang.ViewHolder>{

    private static final String TAG = "LoaiHangAdapter";
    private List<mLoaiHang> list;
    private Context context;
    private View view;
    private FragmentCommunication fragmentCommunicator;
    private MaterialDialog mAnimatedDialog;

    public RecyclerView_CustomAdapter_LoaiHang(List<mLoaiHang> list, Context context, FragmentCommunication fragmentCommunication) {
        this.list = list;
        this.context = context;
        this.fragmentCommunicator = fragmentCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_loai_hang, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, fragmentCommunicator);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtMaLoaiHang.setText(list.get(position).getMaloaihang());
        holder.txtTenLoaiHang.setText(list.get(position).getTenloaihang());
        holder.txtViTri.setText(list.get(position).getVitri());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ShowSPNccFragment fragmentB = new ShowSPNccFragment();
                activity.getSupportFragmentManager().beginTransaction().commit();
                fragmentCommunicator.respond(holder.getAdapterPosition(),list.get(holder.getAdapterPosition()).getMaloaihang());

                Bundle bundle = new Bundle();
                bundle.putString("MALOAIHANG",list.get(position).getMaloaihang());
                fragmentB.setArguments(bundle);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LoaiHangDAO hoaDonDAO = new LoaiHangDAO(context);
                final mLoaiHang m = list.get(position);
                mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Xóa Loại Hàng?")
                        .setMessage("Bạn chắc chắn muốn xóa ?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (hoaDonDAO.deleteLoaiHang(m) < 1){
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }else {

                                    HangHoaDAO hangHoaDAO = new HangHoaDAO(context);
                                    int result = hangHoaDAO.deleteHangHoa(m.getMaloaihang());
                                    SpNccDAO spNccDAO = new SpNccDAO(context);
                                    int result1 = spNccDAO.deleteSpNccByLoaiSp(m.getMaloaihang());
                                    Log.e(TAG, "Result: "+result+" | "+result1);

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtMaLoaiHang;
        private TextView txtTenLoaiHang;
        private TextView txtViTri;
        private CardView parentLayout;
        private ImageView imgDelete;
        FragmentCommunication mCommunication;

        public ViewHolder(@NonNull View itemView, FragmentCommunication communicator) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.custom_layout_adapter_loaihang_parentLayout);
            txtMaLoaiHang = itemView.findViewById(R.id.custom_layout_adapter_maloaihang);
            txtTenLoaiHang = itemView.findViewById(R.id.custom_layout_adapter_tenloaihang);
            txtViTri = itemView.findViewById(R.id.custom_layout_adapter_vitri);

            imgDelete = itemView.findViewById(R.id.custom_layout_adapter_img_delete);

            mCommunication = communicator;
        }
    }

    public void updateDate(List<mLoaiHang> data){
        if (data == null){
            return;
        }
        this.list = data;
        notifyDataSetChanged();
    }

}
