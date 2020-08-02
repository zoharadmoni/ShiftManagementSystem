package com.zohar.nisayon.listViewShift;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ListViewItemShiftViewHolder extends RecyclerView.ViewHolder {
    private TextView mStart;
    private TextView mFinish;
    private TextView mWorkersAmount;

    public ListViewItemShiftViewHolder(View itemView){
        super(itemView);
    }

    public TextView getmStart() {
        return mStart;
    }

    public void setmStart(TextView mStart) {
        this.mStart = mStart;
    }

    public TextView getmFinish() {
        return mFinish;
    }

    public void setmFinish(TextView mFinish) {
        this.mFinish = mFinish;
    }

    public TextView getmWorkersAmount() {
        return mWorkersAmount;
    }

    public void setmWorkersAmount(TextView mWorkersAmount) {
        this.mWorkersAmount = mWorkersAmount;
    }
}
