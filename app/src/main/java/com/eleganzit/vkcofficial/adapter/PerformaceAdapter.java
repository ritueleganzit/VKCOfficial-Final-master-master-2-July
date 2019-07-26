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
import com.eleganzit.vkcofficial.model.Report;

import java.util.List;

public class PerformaceAdapter extends RecyclerView.Adapter<PerformaceAdapter.MyViewHolder> {

   List<Report> campaigns;
    Context context;
    Activity activity;

    public PerformaceAdapter(List<Report> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.performancerow,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        Report pNumber=campaigns.get(i);
        int p=i+1;
        holder.srno.setText(""+p);
        if (pNumber.getInputQty()!=null && !(pNumber.getInputQty()).isEmpty())
        {
            holder.inputQty.setText(""+pNumber.getInputQty());

        }
        else
        {
            holder.inputQty.setText("-");

        }  if (pNumber.getOutputQty()!=null && !(pNumber.getOutputQty()).isEmpty())
        {
            holder.outputQty.setText(""+pNumber.getOutputQty());

        }
        else
        {
            holder.outputQty.setText("-");

        }
         if (pNumber.getMonth()!=null && !(pNumber.getMonth()).isEmpty())
        {
            holder.month.setText(""+pNumber.getMonth());

        }
        else
        {
            holder.month.setText("-");

        }



    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView srno,inputQty,outputQty,month;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            srno=itemView.findViewById(R.id.srno);
            inputQty=itemView.findViewById(R.id.inputQty);
            outputQty=itemView.findViewById(R.id.outputQty);
            month=itemView.findViewById(R.id.month);


        }
    }
}
