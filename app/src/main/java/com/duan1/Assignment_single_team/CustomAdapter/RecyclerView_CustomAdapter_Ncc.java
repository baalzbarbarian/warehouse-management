package com.duan1.Assignment_single_team.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NccDAO;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.SpNccDAO;
import com.duan1.Assignment_single_team.Interface.FragmentCommunication;
import com.duan1.Assignment_single_team.QuanLyDoiTac.NhaCungCap.ShowSPNccFragment;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mNcc;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class RecyclerView_CustomAdapter_Ncc extends RecyclerView.Adapter<RecyclerView_CustomAdapter_Ncc.ViewHolder>{
    private static final String TAG = "RecyclerView_CustomAdap";

    private MaterialDialog mAnimatedDialog;
    private List<mNcc> list;
    private Context context;
    private View view;
    NccDAO db;
    mNcc m;
    FragmentCommunication fragmentCommunicator;

    public RecyclerView_CustomAdapter_Ncc(List<mNcc> list, Context context, View.OnClickListener mClickListener, FragmentCommunication fragmentCommunication) {
        this.list = list;
        this.context = context;
        this.mClickListener = mClickListener;
        this.fragmentCommunicator = fragmentCommunication;
    }

    //OnItemClick
    View.OnClickListener mClickListener;

//    public void setOnItemClick(OnItemClick onItemClick) {
//        this.onItemClick = onItemClick;
//    }

    public interface ClickListener {
        void mClickListener(View view); //pass any things
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_ncc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, fragmentCommunicator);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onClick(v);
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtTen.setText(list.get(position).getTenncc());
        holder.txtDiaChi.setText(list.get(position).getDiachi());
        holder.txtPhone.setText(list.get(position).getPhonencc());

        //Convert byte to bitmap
        byte[] logoNcc = list.get(position).getLogoncc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(logoNcc, 0, logoNcc.length);
        holder.imgLogo.setImageBitmap(bitmap);

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m = list.get(position);
                db = new NccDAO(context);

                mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Xóa Nhà Cung Cấp?")
                        .setMessage("Bạn chắc chắn muốn xóa ?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (db.deleteNcc(m) < 1){
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }else {
                                    list.remove(position);
                                    updateDate(db.getAllNcc());
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

                                    //xoá hàng của ncc
                                    SpNccDAO spNccDAO = new SpNccDAO(context);
                                    String mancc = m.getMancc();
                                    int result = spNccDAO.deleteSpNccByMaNcc(mancc);
                                    Log.e(TAG, "onClick: "+result+" MANCC"+mancc);
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

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClick.getPosition(position);
//            }
//        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick){
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    ShowSPNccFragment fragmentB = new ShowSPNccFragment();
                    activity.getSupportFragmentManager().beginTransaction().commit();
                    fragmentCommunicator.respond(holder.getAdapterPosition(),list.get(holder.getAdapterPosition()).getMancc());
                    Bundle bundle = new Bundle();
                    bundle.putString("MANCC",list.get(position).getMancc());
                    fragmentB.setArguments(bundle);
                }
            }
        });
    }

    public void updateDate(List<mNcc> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView txtTen;
        private TextView txtDiaChi;
        private TextView txtPhone;
        private ImageView imgRemove;
        private ImageView imgLogo;
        private CardView parentLayout;
        private ItemClickListener itemClickListener;
        FragmentCommunication mCommunication;

        public ViewHolder(@NonNull View itemView, FragmentCommunication communicator) {
            super(itemView);
            txtTen      = itemView.findViewById(R.id.custom_layout_adapter_ncc_list_tenNcc);
            txtDiaChi   = itemView.findViewById(R.id.custom_layout_adapter_ncc_list_diaChi);
            txtPhone    = itemView.findViewById(R.id.custom_layout_adapter_ncc_list_phoneNcc);
            imgRemove   = itemView.findViewById(R.id.custom_layout_adapter_ncc_list_imgDeleteItem);
            imgLogo     = itemView.findViewById(R.id.custom_layout_adapter_ncc_list_imgViewLogoNCC);
            parentLayout= itemView.findViewById(R.id.custom_layout_adapter_ncc_list_layoutParent);
            mCommunication = communicator;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }


        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

    }

    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }


}
