package com.eleganzit.vkcofficial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.p_grid.GridDatum;
import com.eleganzit.vkcofficial.model.p_grid.PendingPoGridResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingPODetail extends AppCompatActivity {
    RecyclerView rc_production_complete;
    ArrayList<String> arrayList = new ArrayList<>();
    LinearLayout hourly;
    LinearLayout lin_nodata;

    ProgressDialog progressDialog;
    String txt_pur_doc_num, article, doc_date,item;
    TextView pur_doc_num, number_of_stichers, articleline_number,startendtime;
    UserLoggedInSession userLoggedInSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_podetail);
        lin_nodata = findViewById(R.id.lin_nodata);
        txt_pur_doc_num = getIntent().getStringExtra("pur_doc_num");
        article = getIntent().getStringExtra("article");
        doc_date = getIntent().getStringExtra("doc_date");
        item = getIntent().getStringExtra("item");

        rc_production_complete = findViewById(R.id.rc_production_complete);
        hourly = findViewById(R.id.hourly);
        pur_doc_num = findViewById(R.id.pur_doc_num);
        number_of_stichers = findViewById(R.id.number_of_stichers);
        articleline_number = findViewById(R.id.articleline_number);
        startendtime = findViewById(R.id.startendtime);
        userLoggedInSession = new UserLoggedInSession(PendingPODetail.this);
        progressDialog = new ProgressDialog(PendingPODetail.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PendingPODetail.this, LinearLayoutManager.VERTICAL, false);
        rc_production_complete.setLayoutManager(layoutManager);
        hourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PendingPODetail.this, HourlyProductionActivity.class)
                        .putExtra("pur_doc_num",txt_pur_doc_num)
                        .putExtra("article",article)
                        .putExtra("item",item)
                        .putExtra("doc_date",doc_date));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getPOGridDetails();
    }

    private void getPOGridDetails() {
Log.d("sfsfsf",""+item+" "+txt_pur_doc_num+" "+article+" "+item);
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PendingPoGridResponse> call = myInterface.pendingPoDetails(txt_pur_doc_num, article,item);
        call.enqueue(new Callback<PendingPoGridResponse>() {
            @Override
            public void onResponse(Call<PendingPoGridResponse> call, Response<PendingPoGridResponse> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    if (response.body().getData() != null) {

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            if (response.body().getData().get(i).getGridData() != null) {
                                lin_nodata.setVisibility(View.GONE);
                                rc_production_complete.setVisibility(View.VISIBLE);
                                rc_production_complete.setAdapter(new PendingPODetailAdapter(response.body().getData().get(i).getGridData(), PendingPODetail.this));

                            } else {
                                lin_nodata.setVisibility(View.VISIBLE);
                                rc_production_complete.setVisibility(View.GONE);
                            }

                            if (response.body().getData().get(i).getPurDocNum() != null && !(response.body().getData().get(i).getPurDocNum().isEmpty())) {
                                pur_doc_num.setText("" + response.body().getData().get(i).getPurDocNum());

                            }

                            if ((response.body().getData().get(i).getArticle() != null && !(response.body().getData().get(i).getArticle().isEmpty()))) {
                                articleline_number.setText("" + response.body().getData().get(i).getArticle());


                            }if ((response.body().getData().get(i).getVendor_start_time() != null && !(response.body().getData().get(i).getVendor_start_time().isEmpty()))) {
                                startendtime.setText("PRODUCTION COMPLETE FROM " + response.body().getData().get(i).getVendor_start_time()+" TO "+response.body().getData().get(i).getVendor_end_time());


                            }
                            else
                            {
                                startendtime.setText("PRODUCTION NOT STARTED");

                            }

                            if ((response.body().getData().get(i).getLineNumber() == null)) {
                                // Toast.makeText(PendingPODetail.this, "dd"+response.body().getData().get(i).getLineNumber(), Toast.LENGTH_SHORT).show();
                                //
                            } else {
                                articleline_number.setText("" + response.body().getData().get(i).getArticle() + "/" + response.body().getData().get(i).getLineNumber());
                            }


                            if ((response.body().getData().get(i).getNumberOfStichers() != null && !(response.body().getData().get(i).getNumberOfStichers().isEmpty()))) {
                                number_of_stichers.setText("" + response.body().getData().get(i).getNumberOfStichers());

                            }
                            if ((response.body().getData().get(i).getNumberOfHelpers() == null)) {

                            } else {
                                number_of_stichers.setText("" + response.body().getData().get(i).getNumberOfStichers() + "/" + response.body().getData().get(i).getNumberOfHelpers());

                            }

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PendingPoGridResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PendingPODetail.this, "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class PendingPODetailAdapter extends RecyclerView.Adapter<PendingPODetailAdapter.MyViewHolder> {

        List<GridDatum> campaigns;
        Context context;
        Activity activity;

        public PendingPODetailAdapter(List<GridDatum> campaigns, Context context) {
            this.campaigns = campaigns;
            this.context = context;
            activity = (Activity) context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_production_complete, viewGroup, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
            GridDatum gridDatum = campaigns.get(i);
            if (gridDatum.getGridValue() != null) {
                holder.grid_value.setText("-" + gridDatum.getGridValue());
            }
            if (gridDatum.getQualityProduced() != null) {
                holder.quality_produced.setText("(" + gridDatum.getQualityProduced() + "/");

            }

            if (gridDatum.getScheduledQuantity() != null) {
                holder.scheduled_quantity.setText(gridDatum.getScheduledQuantity() + ")");

            }


        }

        @Override
        public int getItemCount() {
            return campaigns.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView grid_value, scheduled_quantity, quality_produced;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                grid_value = itemView.findViewById(R.id.grid_value);
                scheduled_quantity = itemView.findViewById(R.id.scheduled_quantity);
                quality_produced = itemView.findViewById(R.id.quality_produced);

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
