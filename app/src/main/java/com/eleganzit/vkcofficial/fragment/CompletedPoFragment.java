package com.eleganzit.vkcofficial.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.PendingPODetail;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.adapter.CompletedPOAdapter;
import com.eleganzit.vkcofficial.adapter.PendingPOAdapter;
import com.eleganzit.vkcofficial.adapter.SearchAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.AllArticleResponse;
import com.eleganzit.vkcofficial.model.AllPO;
import com.eleganzit.vkcofficial.model.AllPOResponse;
import com.eleganzit.vkcofficial.model.PendingPO;
import com.eleganzit.vkcofficial.model.SearchData;
import com.eleganzit.vkcofficial.model.SearchDataResponse;
import com.eleganzit.vkcofficial.model.SearchPO;
import com.eleganzit.vkcofficial.model.SearchPOResponse;
import com.eleganzit.vkcofficial.util.LinearLayoutManagerWrapper;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedPoFragment extends Fragment{
TextInputEditText edallpo;
    ProgressDialog progressDialog;
    private Timer timer;
    LinearLayout save;
    TextView records;
    final Calendar newCalendar = Calendar.getInstance();
RecyclerView rc_completed_list;
ArrayList<SearchPO> arrayList;
    public CompletedPoFragment() {
        // Required empty public constructor
    }
    List<String> stateArrayList=new ArrayList();
    List<String> stateArrayListnum=new ArrayList();
    TextInputEditText edenddate,edvendor;
    String vendor_id,date;
    UserLoggedInSession userLoggedInSession;
    List<String> articlelist;
CheckBox advance;
     RecyclerView rc_search;
String searchdate,searchname,vendornamee;
    List<SearchData> searchList;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SearchAdapter searchAdapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v=inflater.inflate(R.layout.fragment_completed_po, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        records=v.findViewById(R.id.records);
        advance=v.findViewById(R.id.advance);
        rc_completed_list=v.findViewById(R.id.rc_completed_list);
        edallpo=v.findViewById(R.id.edallpo);
        edvendor=v.findViewById(R.id.edvendor);
        save=v.findViewById(R.id.save);
        edenddate=v.findViewById(R.id.edenddate);
        userLoggedInSession = new UserLoggedInSession(getActivity());
        edvendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d=new Dialog(getActivity(),
                        R.style.Theme_Dialog);
                d.setContentView(R.layout.vendorsearch);
                final EditText ed_search=d.findViewById(R.id.ed_search);
                TextView cancel,ok;
                final String[] userInput = new String[1];
                final RecyclerView rc_search;

                cancel=d.findViewById(R.id.cancel);
                ok=d.findViewById(R.id.ok);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        advance.setChecked(false);
                        d.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_search.getText().toString().equals("")) {

                            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_search);

                        }
                        else {
                            edvendor.setText(vendornamee);
getPOLine();
                            String art[]=edenddate.getText().toString().split("-");
                            //searchname=ed_search.getText().toString();
                            //searchData(edallpo.getText().toString(),art[0],ed_date.getText().toString(),searchname,art[1]);
                            d.dismiss();
                        }


                    }
                });

                RecyclerView.LayoutManager layoutManager1=new LinearLayoutManagerWrapper(getActivity(),LinearLayoutManager.VERTICAL,false);
                rc_search=d.findViewById(R.id.rc_search);

                rc_search.setLayoutManager(layoutManager1);
                rc_search.setItemAnimator(new DefaultItemAnimator());

                searchList = new ArrayList<>();

                searchAdapter = new SearchAdapter(searchList, getActivity(), new SearchAdapter.SearchAdapterListener() {
                    @Override
                    public void onSearchSelected(SearchData searchData) {
                    }
                });
                rc_search.setAdapter(searchAdapter);

                ed_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(final CharSequence charSequence, final int i, int i1, int i2) {

                        // user is typing: reset already started timer (if existing)
                        if (timer != null) {
                            timer.cancel();
                        }

                        userInput[0] =charSequence.toString();
/*
                if(!userInput.isEmpty())
                {
                    rc_search.setVisibility(View.VISIBLE);
                    search_img.setVisibility(View.GONE);
                    search_progress.setVisibility(View.VISIBLE);
                    MyInterface myInterface = RetrofitApiClient.getRetrofit().create(MyInterface.class);
                    Call<SearchDataResponse> call=myInterface.searchAll(userData.get(UserSessionManager.KEY_USER_ID),ed_search.getText().toString(),"0");
                    call.enqueue(new Callback<SearchDataResponse>() {
                        @Override
                        public void onResponse(Call<SearchDataResponse> call, final Response<SearchDataResponse> response) {
                            search_progress.setVisibility(View.GONE);
                            if(response.isSuccessful()) {

                                //Toast.makeText(getActivity(), ""+response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();

                                if(response.body()!=null)
                                {
                                    if(searchList.size()>0)
                                    {
                                        searchList.clear();
                                    }
                                    if(response.body().getData()!=null)
                                    {
                                        for(int i=0; i<response.body().getData().size();i++)
                                        {
                                            Log.d("responseseeee "+i,""+response.body().getData().get(i).getFullname());
                                            SearchData searchData=new SearchData(response.body().getData().get(i).getPhoto(),response.body().getData().get(i).getFullname(),response.body().getData().get(i).getUsername(),response.body().getData().get(i).getEmailId(),response.body().getData().get(i).getCity(),response.body().getData().get(i).getHometown(),response.body().getData().get(i).getState());
                                            searchList.add(searchData);

                                        }
                                    }
                                    if (searchList.size() > 0) {
                                        Log.d("whereeeeeeeeee", "      "+searchList.size());

                                        //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                        searchAdapter = new SearchAdapter(searchList,getActivity(), new SearchAdapter.SearchAdapterListener() {
                                            @Override
                                            public void onSearchSelected(SearchData searchData) {
                                                ed_search.setText("");
                                                ed_search.append(searchData.getFullname()+" ");

                                            }
                                        });

                                        if(!userInput.isEmpty())
                                        {

                                            Log.d("wwwwwwwwwwwwww",userInput+"");
                                            rc_search.getRecycledViewPool().clear();
                                            rc_search.setAdapter(searchAdapter);
                                            searchAdapter.getFilter().filter(userInput);
                                            searchAdapter.notifyDataSetChanged();
                                        }

                                    } else {
                                        Log.d("whereeeeeeeeee", "      switch case '@' in else");

                                    }

                                }

                            }
                            else
                            {
                                Log.d("whereeeeeeeeee", "   "+response.errorBody());

                            }
                        }

                        @Override
                        public void onFailure(Call<SearchDataResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                            search_progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    search_progress.setVisibility(View.GONE);
                    search_img.setVisibility(View.VISIBLE);
                    searchList.clear();
                    searchAdapter = new SearchAdapter(searchList,getActivity(), new SearchAdapter.SearchAdapterListener() {
                        @Override
                        public void onSearchSelected(SearchData searchData) {
                            ed_search.setText("");
                            ed_search.append(searchData.getFullname()+" ");

                        }
                    });
                    rc_search.getRecycledViewPool().clear();
                    rc_search.setAdapter(searchAdapter);
                    searchAdapter.getFilter().filter(userInput);
                    searchAdapter.notifyDataSetChanged();
                    rc_search.setVisibility(View.GONE);
                }*/
                        //Toast.makeText(getActivity(), "onTextChanged  "+ed_search.getText().toString().isEmpty(), Toast.LENGTH_SHORT).show();
                        Log.d("whrrrrrr","onTextChanged  "+ed_search.getText().toString().isEmpty());
                    }

                    @Override
                    public void afterTextChanged(final Editable editable) {


                        // user typed: start the timer
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // do your actual work here
                                        userInput[0] =editable.toString();

                                        if(!userInput[0].isEmpty())
                                        {
                                            rc_search.setVisibility(View.VISIBLE);

                                            RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
                                            Call<SearchDataResponse> call=myInterface.serchVendorList(ed_search.getText().toString());
                                            call.enqueue(new Callback<SearchDataResponse>() {
                                                @Override
                                                public void onResponse(Call<SearchDataResponse> call, final Response<SearchDataResponse> response) {

                                                    if(response.isSuccessful()) {
                                                        Log.d("whereeeeeeeeee", "     onresponse ");

                                                        //Toast.makeText(getActivity(), ""+response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();

                                                        if(response.body()!=null)
                                                        {
                                                            Log.d("whereeeeeeeeee", "   response is not null   ");

                                                            if(searchList.size()>0)
                                                            {
                                                                Log.d("whereeeeeeeeee", "   list size was greater than 0   ");

                                                                searchList.clear();
                                                            }
                                                            if(response.body().getData()!=null)
                                                            {
                                                                Log.d("whereeeeeeeeee", "   response body data list is not null   ");
                                                                if(response.body().getStatus().toString().equalsIgnoreCase("1"))
                                                                {
                                                                    for(int i=0; i<response.body().getData().size();i++)
                                                                    {
                                                                        SearchData searchData = new SearchData(response.body().getData().get(i).getVendorId(), response.body().getData().get(i).getVendorName());
                                                                        searchList.add(searchData);
                                                                        Log.d("whereeeeeeeeee", "      " + searchList.size());
                                                                    }


                                                                    rc_search.getAdapter().notifyDataSetChanged();
                                                                }
                                                                else
                                                                {
                                                                }
                                                            }
                                                            else
                                                            {
                                                                //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                                                searchAdapter = new SearchAdapter(searchList,getActivity(), new SearchAdapter.SearchAdapterListener() {
                                                                    @Override
                                                                    public void onSearchSelected(SearchData searchData) {
                                                                        ed_search.setText("");
                                                                        ed_search.append(searchData.getVendorName()+"");

                                                                        Log.d("whereeeeeeeeee", "calledd");


                                                                    }
                                                                });

                                                                if(!userInput[0].isEmpty())
                                                                {

                                                                    Log.d("wwwwwwwwwwwwww", userInput[0] +"");
                                                                    rc_search.getRecycledViewPool().clear();
                                                                    rc_search.setAdapter(searchAdapter);
                                                                    searchAdapter.getFilter().filter(userInput[0]);
                                                                    searchAdapter.notifyDataSetChanged();
                                                                }
                                                            }
                                                            if (searchList.size() > 0) {
                                                                Log.d("whereeeeeeeeee", " dsf     "+searchList.size());

                                                                //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                                                searchAdapter = new SearchAdapter(searchList,getActivity(), new SearchAdapter.SearchAdapterListener() {
                                                                    @Override
                                                                    public void onSearchSelected(SearchData searchData) {
                                                                        ed_search.setText("");
                                                                        ed_search.append(searchData.getVendorName()+"");
                                                                        searchname=searchData.getVendorId();
                                                                        vendornamee=searchData.getVendorName();
                                                                        Log.d("whereeeeeeeeee", " called  cgcgb   "+searchname);


                                                                    }
                                                                });

                                                                if(!userInput[0].isEmpty())
                                                                {

                                                                    Log.d("wwwwwwwwwwwwww", userInput[0] +"");
                                                                    rc_search.getRecycledViewPool().clear();
                                                                    rc_search.setAdapter(searchAdapter);
                                                                    searchAdapter.getFilter().filter(userInput[0]);
                                                                    searchAdapter.notifyDataSetChanged();
                                                                }

                                                            } else {
                                                                Log.d("whereeeeeeeeee", "      switch case '@' in else");

                                                            }

                                                        }

                                                    }
                                                    else
                                                    {
                                                        Log.d("whereeeeeeeeee", "   "+response.errorBody());
                                                        Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SearchDataResponse> call, Throwable t) {
                                                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                        else
                                        {
                                            // Toast.makeText(getActivity(), "empty", Toast.LENGTH_SHORT).show();

                                            searchList.clear();
                                            searchAdapter = new SearchAdapter(searchList,getActivity(), new SearchAdapter.SearchAdapterListener() {
                                                @Override
                                                public void onSearchSelected(SearchData searchData) {
                                                    ed_search.setText(searchData.getVendorName());
                                                    Log.d("whereeeeeeeeee", " calledzzv");


                                                }
                                            });
                                            rc_search.getRecycledViewPool().clear();
                                            rc_search.setAdapter(searchAdapter);
                                            searchAdapter.getFilter().filter(userInput[0]);
                                            searchAdapter.notifyDataSetChanged();
                                            rc_search.setVisibility(View.GONE);
                                        }
                                    }
                                });


                            }
                        }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask

                    }
                });


                ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            // performSearch();
                            return true;
                        }
                        return false;
                    }
                });
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();

            }
        });
advance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
        {
            final Dialog d=new Dialog(getActivity(),
                    R.style.Theme_Dialog);
            d.setContentView(R.layout.advancesearch);
            final EditText ed_date=d.findViewById(R.id.ed_date);;
            TextView cancel,ok;
            final String[] userInput = new String[1];
            final RecyclerView rc_search;

            cancel=d.findViewById(R.id.cancel);
            ok=d.findViewById(R.id.ok);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    advance.setChecked(false);
                    d.dismiss();
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (ed_date.getText().toString().equals("")) {

                        YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_date);
                    }
              else {

                  String art[]=edenddate.getText().toString().split("-");
                  searchdate=ed_date.getText().toString();
                  //searchname=ed_search.getText().toString();
                      //  searchData(edallpo.getText().toString(),art[0],ed_date.getText().toString(),searchname,art[1]);
d.dismiss();
                    }


                }
            });
            ed_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {


                                    int month= monthOfYear+1;
                                    String fm=""+month;
                                    String fd=""+dayOfMonth;
                                    if(month<10){
                                        fm ="0"+month;
                                    }
                                    if (dayOfMonth<10){
                                        fd="0"+dayOfMonth;
                                    }
                                    ed_date.setText(fd + "-" + (fm) + "-" + year);
                                    date=ed_date.getText().toString();

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                    }
            });



            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.show();

        }
    }
});
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_completed_list.setLayoutManager(layoutManager);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edallpo.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edallpo);

                }
                else if (edenddate.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edenddate);

                }
                else
                {
                    String art[]=edenddate.getText().toString().split("-");
                        if (advance.isChecked())
                        {
                            searchData(edallpo.getText().toString(),art[0],searchdate,searchname,art[1]);

                        }
                        else {
                            searchData(edallpo.getText().toString(), art[0], "", searchname, art[1]);
                        }
                }
            }
        });
        edallpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (stateArrayList.size()>0) {
                    final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stateArrayList);

                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

                    builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();


                            edallpo.setText(stateArrayList.get(i));


                            assignArticle(stateArrayList.get(i));


                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getActivity(), "No PO available", Toast.LENGTH_SHORT).show();
                }

            }

        }); edenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articlelist!=null)
                {
                    if(articlelist.size()>0) {

                        final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, articlelist);

                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

                        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();


                                edenddate.setText(articlelist.get(i));

                            }
                        });
                        builder.show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "No Article", Toast.LENGTH_SHORT).show();
                }

            }

        });
    //    getPOLine();

        return v;
    }

    private void searchData(String toString, String toString1,String date,String vendor_id,String item) {

        Log.d("ffffffffffff",""+toString+" "+toString1+" "+date+" "+vendor_id+" "+item);
        arrayList=new ArrayList();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SearchPOResponse> call=myInterface.completePoList(toString,toString1,date,vendor_id,item);
        call.enqueue(new Callback<SearchPOResponse>() {
            @Override
            public void onResponse(Call<SearchPOResponse> call, Response<SearchPOResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        rc_completed_list.setVisibility(View.VISIBLE);
                        if (response.body().getData().size()==1)
                        {
                            records.setText("Found "+response.body().getData().size()+ " Record");

                        }
                        else
                        {
                            records.setText("Found "+response.body().getData().size()+ " Records");

                        }
                        if (response.body().getData()!=null)
                        {
                            rc_completed_list.setAdapter(new CompletedPOAdapter(response.body().getData(),getActivity()));

                        }
                    }
                    else
                    {
                        records.setText("Found 0 Record");
                        rc_completed_list.setVisibility(View.GONE);

                    }

                }
                else
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SearchPOResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void assignArticle(String s) {
        articlelist=new ArrayList();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AllArticleResponse> call=myInterface.poAritcleList(s);
        call.enqueue(new Callback<AllArticleResponse>() {
            @Override
            public void onResponse(Call<AllArticleResponse> call, Response<AllArticleResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Log.d("asfsdf",""+response.body().getData());

                    if(response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            articlelist.add(response.body().getData().get(i).getArticle()+"-"+response.body().getData().get(i).getItem());
                            Log.d("asfsdf",""+response.body().getData().get(i).getArticle());
                        }
                        edenddate.setText(articlelist.get(0));
                    }


                }
            }

            @Override
            public void onFailure(Call<AllArticleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void getPOLine() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AllPOResponse> call=myInterface.allPoNumber(searchname);
        call.enqueue(new Callback<AllPOResponse>() {
            @Override
            public void onResponse(Call<AllPOResponse> call, Response<AllPOResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {

                            Log.d("stattelist","--"+response.body().getData().get(i).getPurDocNum()    );


                            stateArrayList.add(response.body().getData().get(i).getPurDocNum());


                        }
                        edallpo.setText(stateArrayList.get(0));

                        assignArticle(stateArrayList.get(0));


                    }


                }
            }

            @Override
            public void onFailure(Call<AllPOResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeActivity.textTitle.setText("COMPLETED PO");

    }





}
