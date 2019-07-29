package com.eleganzit.vkcofficial;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.api.uploadMultupleImage.CallAPiActivity;
import com.eleganzit.vkcofficial.api.uploadMultupleImage.GetResponse;
import com.eleganzit.vkcofficial.model.p_grid.PendingPoGridResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.nereo.multi_image_selector.MultiImageSelector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedPOActivity extends AppCompatActivity {
    RecyclerView rc_production_complete;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> imglist=new ArrayList<>();
    LinearLayout hourlyproduction,uploadlinear;
    RecyclerView rc_image;
LinearLayout view_gallery;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    private ArrayList<String> mSelectPath;
TextView pur_doc_num,article,doc_date;
String txt_pur_doc_num,txt_article,txt_doc_date,item;
    CallAPiActivity callAPiActivity;
    public static String URL_COMPLETE = "http://itechgaints.com/VKC-API/addDefect";
    ProgressDialog progressDialog;
    ArrayList<String> str_photo_array=new ArrayList<>();
    private String mediapath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_po);
        callAPiActivity = new CallAPiActivity(CompletedPOActivity.this);
        progressDialog = new ProgressDialog(CompletedPOActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        rc_production_complete=findViewById(R.id.rc_production_complete);
        hourlyproduction=findViewById(R.id.hourlyproduction);
        view_gallery=findViewById(R.id.view_gallery);
        pur_doc_num=findViewById(R.id.pur_doc_num);
        doc_date=findViewById(R.id.doc_date);
        article=findViewById(R.id.article);
        txt_pur_doc_num=getIntent().getStringExtra("pur_doc_num");
        txt_article=getIntent().getStringExtra("article");
        item=getIntent().getStringExtra("item");
        uploadlinear=findViewById(R.id.uploadlinear);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CompletedPOActivity.this,LinearLayoutManager.VERTICAL,false);
        rc_production_complete.setLayoutManager(layoutManager);
      //  rc_production_complete.setAdapter(new PendingPODetail.PendingPODetailAdapter(arrayList,CompletedPOActivity.this));

        hourlyproduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompletedPOActivity.this,HourlyCompleteActivity.class)
                .putExtra("pur_doc_num",txt_pur_doc_num)
                .putExtra("article",txt_article)
                .putExtra("item",item)



                );
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        view_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompletedPOActivity.this, "No Gallery", Toast.LENGTH_SHORT).show();
            }
        });
        uploadlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(CompletedPOActivity.this,
                        R.style.Theme_Dialog);
                d.setContentView(R.layout.upload_defects_dialog);

                TextView ok=d.findViewById(R.id.ok);
                 rc_image=d.findViewById(R.id.rc_image);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CompletedPOActivity.this,LinearLayoutManager.HORIZONTAL,false);
                rc_image.setLayoutManager(layoutManager);                TextView cancel=d.findViewById(R.id.cancel);
                final EditText ed_email=d.findViewById(R.id.ed_email);
ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mSelectPath!=null) {

            if (mSelectPath.size() > 0) {
                addMarkCompleteDetail2();
                d.dismiss();
            }
            else
            {
                Toast.makeText(CompletedPOActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(CompletedPOActivity.this, "Select Image", Toast.LENGTH_SHORT).show();

        }

    }
});
                ed_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();

                    }
                });

                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if(!isFinishing())
                {
                    d.show();
                }
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


getPOGridDetails();
    }
    private void getPOGridDetails() {

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PendingPoGridResponse> call = myInterface.pendingArticle(txt_pur_doc_num, txt_article, item);
        call.enqueue(new Callback<PendingPoGridResponse>() {
            @Override
            public void onResponse(Call<PendingPoGridResponse> call, Response<PendingPoGridResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            if (response.body().getData().get(i).getGridData() != null) {
                                rc_production_complete.setAdapter(new PendingPODetail.PendingPODetailAdapter(response.body().getData().get(i).getGridData(), CompletedPOActivity.this));

                            } else {
                            }

                            if (response.body().getData().get(i).getPurDocNum() != null && !(response.body().getData().get(i).getPurDocNum().isEmpty())) {
                                pur_doc_num.setText("" + response.body().getData().get(i).getPurDocNum());

                            }

                            if ((response.body().getData().get(i).getArticle() != null && !(response.body().getData().get(i).getArticle().isEmpty()))) {
                                article.setText("" + response.body().getData().get(i).getArticle());


                            }

                            if ((response.body().getData().get(i).getLineNumber() == null)) {
                                // Toast.makeText(PendingPODetail.this, "dd"+response.body().getData().get(i).getLineNumber(), Toast.LENGTH_SHORT).show();
                                //
                            } else {
                                article.setText("" + response.body().getData().get(i).getArticle() + "/" + response.body().getData().get(i).getLineNumber());
                            }


                            if ((response.body().getData().get(i).getNumberOfStichers() != null && !(response.body().getData().get(i).getNumberOfStichers().isEmpty()))) {
                                doc_date.setText("" + response.body().getData().get(i).getNumberOfStichers());

                            }
                            if ((response.body().getData().get(i).getNumberOfHelpers() == null)) {

                            } else {
                                doc_date.setText("" + response.body().getData().get(i).getNumberOfStichers() + "/" + response.body().getData().get(i).getNumberOfHelpers());

                            }

                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<PendingPoGridResponse> call, Throwable t) {
                Toast.makeText(CompletedPOActivity.this, "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void addMarkCompleteDetail2(){

        progressDialog.show();

        HashMap<String, String> map = new HashMap<>();


        Log.d("messageee",""+txt_article+" "+txt_pur_doc_num+" "+mSelectPath);
        map.put("pur_doc_num", txt_pur_doc_num);
        map.put("article", txt_article);
        map.put("item", item);

        callAPiActivity.doPostWithFiles(CompletedPOActivity.this, map, URL_COMPLETE, mSelectPath, "image[]", new GetResponse() {

            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {
                progressDialog.dismiss();
                String status = result.getString("status");
                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(CompletedPOActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    //PlanFragment myPhotosFragment = new PlanFragment();
/*
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, myPhotosFragment, "TAG")
                            .commit();*/
                }
                else
                {
                    Toast.makeText(CompletedPOActivity.this, ""+result.getString("message"), Toast.LENGTH_SHORT).show();
                }

                Log.d("messageeeeeeeeeee", "succccccccessss" + status);
            }

            @Override
            public void onFailureResult(JSONObject result) {
                progressDialog.dismiss();
                Toast.makeText(CompletedPOActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                Log.d("messageeeeeeeeeee--", result.toString());

            }

            @Override
            public void onException(String message) {
                progressDialog.dismiss();
                Log.d("messageeeeeeeeeee", message);

            }
        });


    }
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(CompletedPOActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else {

            MultiImageSelector selector = MultiImageSelector.create(CompletedPOActivity.this);
            selector.showCamera(true);
            selector.count(6);

            selector.multi();

            selector.origin(mSelectPath);
            selector.start(CompletedPOActivity.this, REQUEST_IMAGE);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CompletedPOActivity.this, permission)) {
            new AlertDialog.Builder(CompletedPOActivity.this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CompletedPOActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(CompletedPOActivity.this, new String[]{permission}, requestCode);
        }
    }
    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>
    {
        ArrayList<String> img;
        Context context;

        public ImageAdapter(ArrayList<String> img, Context context) {
            this.img = img;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upload_defects, viewGroup, false);
            ViewHolder myViewHolder = new ViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            Log.d("ddddddd",""+mediapath);

            Glide.with(getApplicationContext()).load(img.get(i)).into(viewHolder.imageView);

            viewHolder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(i);
            }
        });
        }

        @Override
        public int getItemCount() {
            return img.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageButton mRemoveButton;
            public ImageView imageView;

            public ViewHolder(View v){
                super(v);
                imageView = (ImageView) v.findViewById(R.id.img);
                mRemoveButton = (ImageButton) v.findViewById(R.id.ib_remove);
            }
        }

        private void removeAt(int position) {

            img.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, img.size());
            notifyItemChanged(position);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }


                mediapath = sb.toString().trim();
                Log.d("LOG_TAG", "Selected Images 1.5" + mediapath);

                Log.d("mediapathhhhhhhh", "" + mSelectPath);
                imglist.add(""+mediapath.trim());
                rc_image.setAdapter(new ImageAdapter(mSelectPath,CompletedPOActivity.this));

            }
        }
    }


}
