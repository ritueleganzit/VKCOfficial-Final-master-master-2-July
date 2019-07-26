package com.eleganzit.vkcofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.eleganzit.vkcofficial.PendingPODetail;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.fragment.PendingPOFragment;
import com.eleganzit.vkcofficial.model.AllVendorList;
import com.eleganzit.vkcofficial.model.PendingPO;

import java.util.ArrayList;
import java.util.List;

public class AllVendorAdapter extends RecyclerView.Adapter<AllVendorAdapter.MyViewHolder> implements Filterable {

    private List<AllVendorList> contactList;
    private List<AllVendorList> contactListFiltered;
    Context context;
    Activity activity;
    private AllVendorAdapterListener listener;
    public AllVendorAdapter(List<AllVendorList> campaigns, Context context) {
        this.contactListFiltered = campaigns;
        this.contactList = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_allvendor,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final AllVendorList pendingPO=contactListFiltered.get(i);
        if (pendingPO.getVendorName()!=null && !(pendingPO.getVendorName().isEmpty()))
        {
            holder.vendor_name.setText(pendingPO.getVendorName());
        }

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* PendingPOFragment myPhotosFragment = new PendingPOFragment();
        Bundle bundle=new Bundle();
        bundle.putString("vendor_id",""+pendingPO.getVendorId());
        myPhotosFragment.setArguments(bundle);
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, myPhotosFragment, "TAG")
                .commit();*/

        context.startActivity(new Intent(context, PendingPOFragment.class)
                .putExtra("vendor_id",pendingPO.getVendorId())


        );
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        /*context.startActivity(new Intent(context, PendingPODetail.class)
        .putExtra("pur_doc_num",pendingPO.getPurDocNum())
        .putExtra("article",pendingPO.getArticle())
        .putExtra("doc_date",pendingPO.getDocDate())

        );
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);*/
    }
});
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<AllVendorList> filteredList = new ArrayList<>();
                    for (AllVendorList row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getVendorName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                contactListFiltered = (ArrayList<AllVendorList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vendor_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vendor_name=itemView.findViewById(R.id.vendor_name);


        }
    }
    public interface AllVendorAdapterListener {
        void onAllVendorAdapterListener(AllVendorList contact);
    }
}
