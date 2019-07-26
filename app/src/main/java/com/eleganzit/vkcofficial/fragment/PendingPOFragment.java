package com.eleganzit.vkcofficial.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.adapter.PendingPOAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.PendingPOResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingPOFragment extends AppCompatActivity {
    LinearLayout lin_nodata;
    ArrayList<String> arrayList=new ArrayList<>();
    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    RecyclerView rc_plan;
String vendor_id;
    public PendingPOFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pending_po);
        rc_plan=findViewById(R.id.rc_pendingpo);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(PendingPOFragment.this,LinearLayoutManager.VERTICAL,false);
        rc_plan.setLayoutManager(layoutManager);
        lin_nodata=findViewById(R.id.lin_nodata);
        userLoggedInSession=new UserLoggedInSession(PendingPOFragment.this);
        progressDialog=new ProgressDialog(PendingPOFragment.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        vendor_id=getIntent().getStringExtra("vendor_id");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
   
    private void getPendingPO() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PendingPOResponse> call=myInterface.pendingPO(vendor_id);
        call.enqueue(new Callback<PendingPOResponse>() {
            @Override
            public void onResponse(Call<PendingPOResponse> call, Response<PendingPOResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_plan.setVisibility(View.VISIBLE);
                        rc_plan.setAdapter(new PendingPOAdapter(response.body().getData(),PendingPOFragment.this));

                    }
                    else
                    {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_plan.setVisibility(View.GONE);
                    }
                }
                else
                {
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_plan.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PendingPOResponse> call, Throwable t) {
                progressDialog.dismiss();
                lin_nodata.setVisibility(View.VISIBLE);
                rc_plan.setVisibility(View.GONE);
                Toast.makeText(PendingPOFragment.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (vendor_id!=null && !(vendor_id.isEmpty())) {
            getPendingPO();
        }
    }

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeActivity.textTitle.setText("PENDING PO");


    }*/
}
