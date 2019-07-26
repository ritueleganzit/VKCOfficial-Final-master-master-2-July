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


import com.eleganzit.vkcofficial.PendingPODetail;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.model.PendingPO;

import java.util.List;

public class PendingPOAdapter extends RecyclerView.Adapter<PendingPOAdapter.MyViewHolder> {

   List<PendingPO> campaigns;
    Context context;
    Activity activity;

    public PendingPOAdapter(List<PendingPO> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pendingpo,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final PendingPO pendingPO=campaigns.get(i);
         if (pendingPO.getDocDate()!=null && !(pendingPO.getDocDate().isEmpty()))
        {
            holder.doc_date.setText(pendingPO.getDocDate());
        }
        if (pendingPO.getPurDocNum()!=null && !(pendingPO.getPurDocNum().isEmpty()))
        {
            holder.pur_doc_num.setText(pendingPO.getPurDocNum());
        }if (pendingPO.getArticle()!=null && !(pendingPO.getArticle().isEmpty()))
        {
            holder.article.setText(pendingPO.getArticle()+"-"+pendingPO.getItem());
        }
            holder.complete.setText(""+pendingPO.getComplete()+"%");


holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, PendingPODetail.class)
        .putExtra("pur_doc_num",pendingPO.getPurDocNum())
        .putExtra("article",pendingPO.getArticle())
        .putExtra("doc_date",pendingPO.getDocDate())
        .putExtra("item",pendingPO.getItem())

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
        TextView pur_doc_num,article,doc_date,complete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pur_doc_num=itemView.findViewById(R.id.pur_doc_num);
            article=itemView.findViewById(R.id.article);
            complete=itemView.findViewById(R.id.complete);
            doc_date=itemView.findViewById(R.id.doc_date);

        }
    }
}
