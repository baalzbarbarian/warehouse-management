package com.duan1.Assignment_single_team.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.DaiLyDAO;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mDaiLy;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class RecyclerView_CustomAdapter_DaiLy extends RecyclerView.Adapter<RecyclerView_CustomAdapter_DaiLy.ViewHolder>{

    private static final String TAG = "RecyclerView_CustomAdap";

    private MaterialDialog mAnimatedDialog;
    private List<mDaiLy> mDaiLyList;
    private Context context;
    private View view;
//    private FragmentCommunication mCommunicator;

    public RecyclerView_CustomAdapter_DaiLy(List<mDaiLy> mDaiLyList, Context context) {
        this.mDaiLyList = mDaiLyList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_dai_ly, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtMaDaiLy.setText(mDaiLyList.get(position).getMadaily());
        holder.txtTenDaiLy.setText(mDaiLyList.get(position).getTendaily());
        holder.txtDiaChi.setText(mDaiLyList.get(position).getDiachi());
        holder.txtPhone.setText(mDaiLyList.get(position).getPhone());
        holder.imgRemove.setImageResource(R.drawable.ic_remove);

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final mDaiLy m = mDaiLyList.get(position);
                final DaiLyDAO daiLyDAO = new DaiLyDAO(context);

                    mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                            .setTitle("Xóa Đại Lý?")
                            .setMessage("Bạn chắc chắn muốn xóa ?")
                            .setCancelable(false)
                            .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (daiLyDAO.deleteDaily(m) < 1){
                                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }else {
                                        mDaiLyList.remove(position);
                                        updateDate(daiLyDAO.getAllDaiLy());
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

        //Edit employee
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom_edit_dai_ly);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText edtUser  = dialog.findViewById(R.id.dialog_custom_change_daily_maDaiLy);
        final EditText edtFullname = dialog.findViewById(R.id.dialog_custom_change_daily_tenDaiLy);
        final EditText edtPhone = dialog.findViewById(R.id.dialog_custom_change_daily_soDienThoai);
        final EditText edtAddress = dialog.findViewById(R.id.dialog_custom_change_daily_diaChi);


        if (edtUser.isClickable()){
            edtUser.setError("Mã Đại Lý không thể thay thế");
        }else {
            edtUser.setError(null);
        }

        Button btnChange = dialog.findViewById(R.id.dialog_custom_change_daily_btnSave);
        Button btnCancel = dialog.findViewById(R.id.dialog_custom_change_daily_btnCancel);
//        Button btnDelete = dialog.findViewById(R.id.dialog_custom_change_daily_btnDelete);

        edtUser.setText(mDaiLyList.get(position).getMadaily());
        edtFullname.setText(mDaiLyList.get(position).getTendaily());
        edtPhone.setText(mDaiLyList.get(position).getPhone());
        edtAddress.setText(mDaiLyList.get(position).getDiachi());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //Data change
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(edtFullname)){
                    edtFullname.setError("Không được trống");
                }else if (isEmpty(edtPhone)){
                    edtPhone.setError("Không được trống");
                }else if (isEmpty(edtAddress)){
                    edtAddress.setError("Không được trống");
                }else {
                    mDaiLy m = new mDaiLy();
                    m.setMadaily(mDaiLyList.get(position).getMadaily());

                    m.setTendaily(edtFullname.getText().toString());
                    m.setPhone(edtPhone.getText().toString());
                    m.setDiachi(edtAddress.getText().toString());

                    DaiLyDAO db = new DaiLyDAO(context);

                    if (db.updateDaily(m) < 0){
                        Toast.makeText(context, "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                        updateDate(db.getAllDaiLy());
                        dialog.dismiss();
                    }

                }

            }
        });

//
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    dialog.dismiss();
//
//                    final DaiLyDAO daiLyDAO = new DaiLyDAO(context);
//                    final mDaiLy m = new mDaiLy();
//                    m.setMadaily(mDaiLyList.get(position).getMadaily());
//
//                    mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
//                            .setTitle("Xóa Đại Lý?")
//                            .setMessage("Bạn chắc chắn muốn xóa ?")
//                            .setCancelable(false)
//                            .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    if (daiLyDAO.deleteDaily(m) < 1){
//                                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
//                                        notifyItemRemoved(position);
//                                        notifyItemChanged(position);
//                                        updateDate(daiLyDAO.getAllDaiLy());
//
//                                    }
//                                    dialogInterface.dismiss();
//                                }
//                            })
//                            .setNegativeButton("Bỏ qua", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int which) {
//                                    dialogInterface.dismiss();
//                                }
//                            })
//                            .setAnimation("delete_anim.json")
//                            .build();
//
//                    mAnimatedDialog.show();
//
//
//            }
//        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    @Override
    public int getItemCount() {
        return mDaiLyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtMaDaiLy;
        private TextView txtTenDaiLy;
        private TextView txtDiaChi;
        private TextView txtPhone;
        private ImageView imgRemove;
        private CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaDaiLy  = itemView.findViewById(R.id.custom_layout_adapter_daily_list_maDaiLy);
            txtTenDaiLy = itemView.findViewById(R.id.custom_layout_adapter_daily_list_tenDaiLy);
            txtDiaChi   = itemView.findViewById(R.id.custom_layout_adapter_daily_list_diaChiDaiLy);
            txtPhone    = itemView.findViewById(R.id.custom_layout_adapter_daily_list_phoneDaiLy);
            imgRemove   = itemView.findViewById(R.id.custom_layout_adapter_daily_list_imgRemove);
            parentLayout= itemView.findViewById(R.id.custom_layout_adapter_daily_list_parentLayout);

        }

    }

    public void updateDate(List<mDaiLy> data){
        if (data == null){
            return;
        }
        mDaiLyList.clear();
        mDaiLyList.addAll(data);
        notifyDataSetChanged();
    }

}
