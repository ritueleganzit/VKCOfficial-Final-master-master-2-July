package com.eleganzit.vkcofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.model.Report;

import java.util.List;

public class ReportUtilizationAdapter extends RecyclerView.Adapter<ReportUtilizationAdapter.MyViewHolder> {

   List<Report> campaigns;
    Context context;
    Activity activity;

    public ReportUtilizationAdapter(List<Report> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_report_uti,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(new UtilizationAdapter(campaigns,context));
        /*
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
context.startActivity(new Intent(context, CompletedPOActivity.class));
activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
});*/
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView  recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.rc_performance);

        }
    }
}
