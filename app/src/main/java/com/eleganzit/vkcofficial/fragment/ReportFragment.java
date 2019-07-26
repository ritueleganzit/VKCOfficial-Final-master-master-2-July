package com.eleganzit.vkcofficial.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.adapter.ReportAdapter;
import com.eleganzit.vkcofficial.adapter.ReportUtilizationAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.AllVendorListResponse;
import com.eleganzit.vkcofficial.model.ReportResponse;
import com.eleganzit.vkcofficial.util.DateRangePickerFragment;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {
    List<String> stateArrayListnum=new ArrayList();
    List<String> stateArrayList=new ArrayList();
RadioGroup rg;
String type="1";
TextInputLayout txno_of_working_days;

    RecyclerView rc_completed_list;
    ArrayList<String> arrayList=new ArrayList<>();
    public ReportFragment() {
        // Required empty public constructor
    }
    ProgressDialog progressDialog;
    String lineid,daterange;
    UserLoggedInSession userLoggedInSession;
TextInputEditText edenddate,vendor_name_ed,no_of_working_days;
LinearLayout save,lin_nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_report, container, false);
        lin_nodata=v.findViewById(R.id.lin_nodata);
        rc_completed_list=v.findViewById(R.id.rc_report);
        txno_of_working_days=v.findViewById(R.id.txno_of_working_days);
        no_of_working_days=v.findViewById(R.id.no_of_working_days);
        save=v.findViewById(R.id.save);
        edenddate=v.findViewById(R.id.edenddate);
        vendor_name_ed=v.findViewById(R.id.vendor_name_ed);
        rg=v.findViewById(R.id.rg);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_completed_list.setLayoutManager(layoutManager);
        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.performance)
        {
            type="1";
            txno_of_working_days.setVisibility(View.GONE);
        }else
        {
            txno_of_working_days.setVisibility(View.VISIBLE);
            type="2";
        }
    }
});
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vendor_name_ed.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(vendor_name_ed);

                }
                else if (edenddate.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Select Date Range", Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edenddate);

                }  else {
                    if (type.equalsIgnoreCase("1"))
                    {
                        searchData();
                    }
                    else
                    {
                        if (no_of_working_days.getText().toString().equalsIgnoreCase(""))
                        {
                            Toast.makeText(getActivity(), "Enter no of working days", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            searchData1();

                        }
                    }

                }
            }
        });
        vendor_name_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateArrayList.size()>0){
                    final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stateArrayList);

                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

                    builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();


                            vendor_name_ed.setText(stateArrayList.get(i));

                            lineid=""+stateArrayListnum.get(i);



                        }
                    });
                    builder.show();
                }
            }
        });
        edenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerFragment dateRangePickerFragment= DateRangePickerFragment.newInstance(new DateRangePickerFragment.OnDateRangeSelectedListener() {
                    @Override
                    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {

                        edenddate.setText(startYear+"-"+(startMonth+1)+"-"+startDay+"/"+endYear+"-"+(endMonth+1)+"-"+endDay);
                        daterange=edenddate.getText().toString();

                    }
                }, false);
                dateRangePickerFragment.show(getActivity().getSupportFragmentManager(),"datePicker");
            }
        });
        return v;
    }

    private void searchData1() {
        progressDialog.show();
        Log.d("sdfs",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ReportResponse> call=myInterface.report(daterange,lineid,type,no_of_working_days.getText().toString());
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_completed_list.setVisibility(View.VISIBLE);
                        Log.d("fsdfsd",""+response.body().getData());
                        rc_completed_list.setAdapter(new ReportUtilizationAdapter(response.body().getData(),getActivity()));

                    }
                    else
                    {
                        Log.d("fsdfsd","hh"+response.body().getMessage());

                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_completed_list.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Log.d("fsdfsd","hh"+response.body().getMessage());
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_completed_list.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                lin_nodata.setVisibility(View.VISIBLE);
                rc_completed_list.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchData() {
        progressDialog.show();
        Log.d("sdfs",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ReportResponse> call=myInterface.report(daterange,lineid,type,"");
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {

                        Log.d("fsdfsd",""+response.body().getData());
                        rc_completed_list.setAdapter(new ReportAdapter(response.body().getData(),getActivity()));
                        lin_nodata.setVisibility(View.GONE);
                        rc_completed_list.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_completed_list.setVisibility(View.GONE);
                    }
                }
                else
                {
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_completed_list.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                lin_nodata.setVisibility(View.VISIBLE);
                rc_completed_list.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeActivity.textTitle.setText("Reports");
        getPOLine();

    }
    private void getPOLine() {
        progressDialog.show();
        Log.d("sdfs",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AllVendorListResponse> call=myInterface.allVendorList();
        call.enqueue(new Callback<AllVendorListResponse>() {
            @Override
            public void onResponse(Call<AllVendorListResponse> call, Response<AllVendorListResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Log.d("stattelist","--"+response.body().getMessage()    );
                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            stateArrayList.add(response.body().getData().get(i).getVendorName());
                            stateArrayListnum.add(response.body().getData().get(i).getVendorId());


                        }
                        vendor_name_ed.setText(stateArrayList.get(0));
                        lineid=""+stateArrayListnum.get(0);
                    }


                }
            }

            @Override
            public void onFailure(Call<AllVendorListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
