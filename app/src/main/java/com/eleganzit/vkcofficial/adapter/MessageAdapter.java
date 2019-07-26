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
import com.eleganzit.vkcofficial.model.VendorMessage;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    List<VendorMessage> campaigns;
    Context context;
    Activity activity;

    public MessageAdapter(List<VendorMessage> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_message,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
VendorMessage vendorMessage=campaigns.get(i);
holder.message.setText(vendorMessage.getMessage());
holder.date_time.setText(vendorMessage.getDateTime());

holder.vendor_id.setText(vendorMessage.getVendor_name());


    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message,date_time,vendor_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            vendor_id=itemView.findViewById(R.id.vendor_id);
            date_time=itemView.findViewById(R.id.date_time);
        }
    }
}
