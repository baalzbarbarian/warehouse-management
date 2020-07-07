package com.duan1.Assignment_single_team.QuanLyDoiTac.NhanVien;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.duan1.Assignment_single_team.mModel.mEmployee;

import java.util.List;

public class EmployeeDiffCallBack extends DiffUtil.Callback {

    private final List<mEmployee> mOldEmployeelist;
    private final List<mEmployee> mNewEmployeelist;

    public EmployeeDiffCallBack(List<mEmployee> mOldEmployeelist, List<mEmployee> mNewEmployeelist) {
        this.mOldEmployeelist = mOldEmployeelist;
        this.mNewEmployeelist = mNewEmployeelist;
    }

    @Override
    public int getOldListSize() {
        return mOldEmployeelist.size();
    }

    @Override
    public int getNewListSize() {
        return mNewEmployeelist.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldEmployeelist.get(oldItemPosition).getUsername() == mNewEmployeelist.get(newItemPosition).getUsername();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final mEmployee oldEmployee = mOldEmployeelist.get(oldItemPosition);
        final mEmployee newEmployee = mNewEmployeelist.get(newItemPosition);

        return oldEmployee.getName().equals(newEmployee.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
