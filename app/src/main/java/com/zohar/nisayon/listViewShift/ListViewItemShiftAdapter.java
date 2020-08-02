package com.zohar.nisayon.listViewShift;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zohar.nisayon.R;

import java.util.List;


public class ListViewItemShiftAdapter extends BaseAdapter {
    List<ListViewItemShift> listViewItemShiftList=null;
    private Context ctx=null;

    public ListViewItemShiftAdapter(Context ctx,List<ListViewItemShift> listViewItemShiftList) {
        this.listViewItemShiftList = listViewItemShiftList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        int ret=0;
        if(listViewItemShiftList!=null){
            ret=listViewItemShiftList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        Object ret =null;
        if(listViewItemShiftList!=null){
            ret= listViewItemShiftList.get((position));
        }
        return ret;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewItemShiftViewHolder viewHolder=null;
        if(convertView!=null){
            viewHolder=(ListViewItemShiftViewHolder) convertView.getTag();
        }
        else{
            convertView = View.inflate(ctx, R.layout.shift_item,null);
            TextView pStart=convertView.findViewById(R.id.start_shift);
            TextView pFinish=convertView.findViewById(R.id.end_shift);
            TextView pWorkersAmount=convertView.findViewById(R.id.workers_amount);

            viewHolder=new ListViewItemShiftViewHolder(convertView);
            viewHolder.setmStart(pStart);
            viewHolder.setmFinish(pFinish);
            viewHolder.setmWorkersAmount(pWorkersAmount);

            convertView.setTag(viewHolder);
        }
        ListViewItemShift listViewItemShift=listViewItemShiftList.get(position);
        /////////////////////////////////////// OPTION TO GET CHECKBOX

        viewHolder.getmStart().setText(listViewItemShift.getStartShift());
        viewHolder.getmFinish().setText(listViewItemShift.getFinishShift());
        viewHolder.getmWorkersAmount().setText(listViewItemShift.getEmployeeAmount());

        return convertView;
    }
}
