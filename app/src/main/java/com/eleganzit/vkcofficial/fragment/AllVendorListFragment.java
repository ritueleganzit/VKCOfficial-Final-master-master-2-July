package com.eleganzit.vkcofficial.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.adapter.AllVendorAdapter;
import com.eleganzit.vkcofficial.adapter.PendingPOAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.AllVendorList;
import com.eleganzit.vkcofficial.model.AllVendorListResponse;
import com.eleganzit.vkcofficial.model.PendingPOResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllVendorListFragment extends Fragment {
    ProgressDialog progressDialog;
    RecyclerView rc_allvendor;
    LinearLayout lin_nodata;
    UserLoggedInSession userLoggedInSession;
    private Context mContext;
    List<AllVendorList> allVendorLists=new ArrayList<>();

    public AllVendorListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
       // populateList();

    }
    AllVendorAdapter  mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_all_vendor_list, container, false);
        rc_allvendor=v.findViewById(R.id.rc_allvendor);
        lin_nodata=v.findViewById(R.id.lin_nodata);
        userLoggedInSession=new UserLoggedInSession(getActivity());

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_allvendor.setLayoutManager(layoutManager);
        mAdapter=new AllVendorAdapter(allVendorLists,getActivity());
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) searchView.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 mAdapter.getFilter().filter(query);
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String query) {
                 mAdapter.getFilter().filter(query);
                 return false;
             }
         });
        //searchView.setQueryHint("Search");


        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + "Search" + "</font>"));


        super.onCreateOptionsMenu(menu, inflater);

    }

    private void getAllVendor() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AllVendorListResponse> call=myInterface.allVendorList();
        call.enqueue(new Callback<AllVendorListResponse>() {
            @Override
            public void onResponse(Call<AllVendorListResponse> call, Response<AllVendorListResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_allvendor.setVisibility(View.VISIBLE);
                        allVendorLists.addAll((response.body().getData()));
                        rc_allvendor.setAdapter(mAdapter);

                    }
                    else
                    {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_allvendor.setVisibility(View.GONE);
                    }
                }
                else
                {
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_allvendor.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<AllVendorListResponse> call, Throwable t) {
                progressDialog.dismiss();
                lin_nodata.setVisibility(View.VISIBLE);
                rc_allvendor.setVisibility(View.GONE);
                if (getActivity()!=null)
                {
                    Toast.makeText(getContext(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
               //
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeActivity.textTitle.setText("VENDORS");

        getAllVendor();
    }


}
