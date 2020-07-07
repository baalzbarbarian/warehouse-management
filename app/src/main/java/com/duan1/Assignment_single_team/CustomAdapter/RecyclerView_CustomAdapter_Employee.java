package com.duan1.Assignment_single_team.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.duan1.Assignment_single_team.DatabaseSQLite.DAO.NhanVienDAO;
import com.duan1.Assignment_single_team.QuanLyDoiTac.NhanVien.EmployeeDiffCallBack;
import com.duan1.Assignment_single_team.R;
import com.duan1.Assignment_single_team.mModel.mEmployee;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import java.util.ArrayList;
import java.util.List;

public class RecyclerView_CustomAdapter_Employee extends RecyclerView.Adapter<RecyclerView_CustomAdapter_Employee.ViewHolder> implements OnShowListener, OnCancelListener, OnDismissListener {

    private static final String TAG = "RecyclerView_CustomAdap";

    private List<mEmployee> mEmployeeList;
    private Context context;
    private View view;
    private List<mEmployee> mEmployeeListFull;

    private MaterialDialog mAnimatedDialog;

    public void updateEmployeeListItems(List<mEmployee> employees) {
        final EmployeeDiffCallBack diffCallback = new EmployeeDiffCallBack(this.mEmployeeList, employees);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.mEmployeeList.clear();
        this.mEmployeeList.addAll(employees);
        diffResult.dispatchUpdatesTo(this);
    }

    public RecyclerView_CustomAdapter_Employee(List<mEmployee> mEmployeeList, Context context) {
        this.mEmployeeList = mEmployeeList;
        this.context = context;
        mEmployeeListFull = new ArrayList<>(mEmployeeList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.custom_layout_adapter_ds_nhan_vien, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.username.setText(mEmployeeList.get(position).getUsername());
        holder.fullname.setText(mEmployeeList.get(position).getName());
        holder.phonenumber.setText(mEmployeeList.get(position).getPhonenumber());
        holder.address.setText(mEmployeeList.get(position).getAddress());

        //Edit employee
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom_edit_employee);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText edtUser  = dialog.findViewById(R.id.dialog_custom_change_employee_username);
        final EditText edtPass1 = dialog.findViewById(R.id.dialog_custom_change_employee_oldPass);
        final EditText edtPass2 = dialog.findViewById(R.id.dialog_custom_change_employee_newPass);
        final EditText edtFullname = dialog.findViewById(R.id.dialog_custom_change_employee_fullname);
        final EditText edtPhone = dialog.findViewById(R.id.dialog_custom_change_employee_phone);
        final EditText edtAddress = dialog.findViewById(R.id.dialog_custom_change_employee_address);

        Button btnChange = dialog.findViewById(R.id.dialog_custom_change_employee_btnChange);
        Button btnCancel = dialog.findViewById(R.id.dialog_custom_change_employee_btnCancel);

        edtUser.setText(mEmployeeList.get(position).getUsername());
        edtPass1.setText(mEmployeeList.get(position).getPassword());
        edtFullname.setText(mEmployeeList.get(position).getName());
        edtPhone.setText(mEmployeeList.get(position).getPhonenumber());
        edtAddress.setText(mEmployeeList.get(position).getAddress());
        //Dialog Show
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Log.e(TAG, "onClick: ParentLayout "+position);
            }
        });

        //Data change
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(edtPass1)){
                    edtPass1.setError("Không được trống");
                }else if (isEmpty(edtFullname)){
                    edtFullname.setError("Không được trống");
                }else if (isEmpty(edtPhone)){
                    edtPhone.setError("Không được trống");
                }else if (isEmpty(edtAddress)){
                    edtAddress.setError("Không được trống");
                }else {
                    mEmployee m = new mEmployee();
                    m.setUsername(mEmployeeList.get(position).getUsername());
                    if (isEmpty(edtPass2)){
                        m.setPassword(edtPass1.getText().toString());
                    }else {
                        m.setPassword(edtPass2.getText().toString());
                    }
                    m.setName(edtFullname.getText().toString());
                    m.setPhonenumber(edtPhone.getText().toString());
                    m.setAddress(edtAddress.getText().toString());

                    NhanVienDAO db = new NhanVienDAO(context);

                    if (db.updateEmployee(m) < 0){
                        Toast.makeText(context, "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                        updateEmployeeListItems(db.getAllEmployee());
                        dialog.dismiss();
                    }

                }

            }
        });

        holder.imgDeleteItem.setImageResource(R.drawable.ic_remove);
        holder.imgDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmployeeList.get(position).getUsername().equals("admin")){
                    dialog.dismiss();
                    Toast.makeText(context, "Tài khoản quản trị không thể xóa", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    dialog.dismiss();

                    final NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
                    final mEmployee m = new mEmployee();
                    m.setUsername(mEmployeeList.get(position).getUsername());

                    mAnimatedDialog = new MaterialDialog.Builder((Activity) context)
                            .setTitle("Xóa nhân viên?")
                            .setMessage("Bạn chắc chắn muốn xóa ?")
                            .setCancelable(false)
                            .setPositiveButton("Xóa", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (nhanVienDAO.deleteAccount(m) < 1){
                                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(position);
                                        notifyItemChanged(position);
                                        updateEmployeeListItems(nhanVienDAO.getAllEmployee());

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
            }
        });

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
        return mEmployeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView fullname;
        private TextView phonenumber;
        private TextView address;
        private CardView parentLayout;
        private ImageView imgDeleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username     = itemView.findViewById(R.id.custom_layout_adapter_employee_list_taikhoan);
            fullname     = itemView.findViewById(R.id.custom_layout_adapter_employee_list_hoten);
            phonenumber  = itemView.findViewById(R.id.custom_layout_adapter_employee_list_phone);
            address      = itemView.findViewById(R.id.custom_layout_adapter_employee_list_diachi);
            parentLayout = itemView.findViewById(R.id.custom_layout_adapter_employee_list_parentLayout);
            imgDeleteItem= itemView.findViewById(R.id.custom_layout_adapter_employee_list_imgDeleteItem);

        }

    }

    public void updateDate(List<mEmployee> data){
        if (data == null){
            return;
        }
        mEmployeeList.clear();
        mEmployeeList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

}
