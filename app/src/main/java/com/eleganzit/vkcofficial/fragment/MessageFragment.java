package com.eleganzit.vkcofficial.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.adapter.MessageAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.VendorMessageResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    ArrayList<String> arrayList=new ArrayList<>();
    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    RecyclerView rc_message;
    LinearLayout lin_nodata;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_message, container, false);
        rc_message=v.findViewById(R.id.rc_message);
        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        lin_nodata=v.findViewById(R.id.lin_nodata);

        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        progressDialog.setCanceledOnTouchOutside(false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_message.setLayoutManager(layoutManager);
        getMessage();
        return v;
    }

    private void getMessage() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<VendorMessageResponse> call=myInterface.vendorMessage("");
        call.enqueue(new Callback<VendorMessageResponse>() {
            @Override
            public void onResponse(Call<VendorMessageResponse> call, Response<VendorMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_message.setVisibility(View.VISIBLE);
                        rc_message.setAdapter(new MessageAdapter(response.body().getData(),getActivity()));

                    }
                    else
                    {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_message.setVisibility(View.GONE);
                    }

                }
                else
                {
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_message.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<VendorMessageResponse> call, Throwable t) {
                lin_nodata.setVisibility(View.VISIBLE);
                progressDialog.dismiss();

                rc_message.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
