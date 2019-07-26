package com.eleganzit.vkcofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.eleganzit.vkcofficial.CompletedPOActivity;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.model.SearchPO;

import java.util.List;

public class CompletedPOAdapter extends RecyclerView.Adapter<CompletedPOAdapter.MyViewHolder> {

    List<SearchPO> campaigns;
    Context context;
    Activity activity;

    public CompletedPOAdapter(List<SearchPO> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_completed_po,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final SearchPO searchPO=campaigns.get(i);
        holder.vendor_name.setText(""+searchPO.getVendorName());
        holder.pur_doc_num.setText(""+searchPO.getPurDocNum());
        holder.article.setText(""+searchPO.getArticle()+"-"+searchPO.getItem());
        holder.doc_date.setText(""+searchPO.getDocDate()+"");
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
context.startActivity(new Intent(context, CompletedPOActivity.class)
.putExtra("pur_doc_num",searchPO.getPurDocNum())
.putExtra("article",searchPO.getArticle())
.putExtra("item",searchPO.getItem())

);
activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
});
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vendor_name,pur_doc_num,article,doc_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vendor_name=itemView.findViewById(R.id.vendor_name);
            pur_doc_num=itemView.findViewById(R.id.pur_doc_num);
            article=itemView.findViewById(R.id.article);
            doc_date=itemView.findViewById(R.id.doc_date);

        }
    }
}
