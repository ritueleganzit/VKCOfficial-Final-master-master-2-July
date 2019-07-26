package com.eleganzit.vkcofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.model.SearchData;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable
{

    private List<SearchData> searchList;
    private List<SearchData> searchListFiltered;
    Context context;
    Activity activity;
    private SearchAdapterListener listener;

    public SearchAdapter(List<SearchData> searches, Context context, SearchAdapterListener listener) {
        this.searchList = searches;
        this.searchListFiltered = searches;
        this.listener = listener;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        final SearchData searchData = searchList.get(i);
        holder.name.setText(searchData.getVendorName());

        Log.d("responseseeee",i+"adapter"+searchData.getVendorName());


    }

    @Override
    public int getItemCount() {
        return searchListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchListFiltered = searchList;
                } else {
                    List<SearchData> filteredList = new ArrayList<>();
                    for (SearchData row : searchList) {
                        Log.d("wwwwwwwwwwwwww",charString.toLowerCase()+"");
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        //if (row.getFullname().toLowerCase().contains(charString.toLowerCase()) || row.getUsername().contains(charSequence) || row.getCity().contains(charSequence) || row.getEmail().contains(charSequence) || row.getHometown().contains(charSequence) || row.getState().contains(charSequence) ) {
                        filteredList.add(row);
                        //  }
                    }

                    searchListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchListFiltered = (ArrayList<SearchData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback

                    listener.onSearchSelected(searchListFiltered.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface SearchAdapterListener {
        void onSearchSelected(SearchData searchData);
    }
}