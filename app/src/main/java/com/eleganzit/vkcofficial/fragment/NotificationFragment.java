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
import com.eleganzit.vkcofficial.adapter.NotificationAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.CompletePoNotificationResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    ArrayList<String> arrayList=new ArrayList<>();
    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
RecyclerView  rc_notification;
    public NotificationFragment() {
        // Required empty public constructor
    }
    LinearLayout lin_nodata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_notification, container, false);
        rc_notification=v.findViewById(R.id.rc_notification);
        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        lin_nodata=v.findViewById(R.id.lin_nodata);

        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        progressDialog.setCanceledOnTouchOutside(false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_notification.setLayoutManager(layoutManager);
        getNotification();
        return v;
    }

    private void getNotification() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<CompletePoNotificationResponse> call=myInterface.completePoNotification();
        call.enqueue(new Callback<CompletePoNotificationResponse>() {
            @Override
            public void onResponse(Call<CompletePoNotificationResponse> call, Response<CompletePoNotificationResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_notification.setVisibility(View.VISIBLE);
                        rc_notification.setAdapter(new NotificationAdapter(response.body().getData(),getActivity()));

                    }
                    else
                    {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_notification.setVisibility(View.GONE);
                    }

                }
                else
                {
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_notification.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<CompletePoNotificationResponse> call, Throwable t) {
                lin_nodata.setVisibility(View.VISIBLE);
                progressDialog.dismiss();

                rc_notification.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
