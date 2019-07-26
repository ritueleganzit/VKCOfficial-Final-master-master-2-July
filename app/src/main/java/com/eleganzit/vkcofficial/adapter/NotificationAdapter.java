package com.eleganzit.vkcofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.model.CompletePoNotification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    List<CompletePoNotification> campaigns;
    Context context;
    Activity activity;

    public NotificationAdapter(List<CompletePoNotification> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_notication,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        CompletePoNotification completePoNotification=campaigns.get(i);
        holder.vendor_name.setText(completePoNotification.getVendorName());
        holder.created_date.setText(completePoNotification.getCreatedDate());
        holder.pur_doc_num.setText(completePoNotification.getPurDocNum());

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vendor_name,pur_doc_num,created_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vendor_name=itemView.findViewById(R.id.vendor_name);
            pur_doc_num=itemView.findViewById(R.id.pur_doc_num);
            created_date=itemView.findViewById(R.id.created_date);


        }
    }
}
