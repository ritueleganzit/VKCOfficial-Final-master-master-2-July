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
import com.eleganzit.vkcofficial.adapter.HourlyPOAdapter;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.api.uploadMultupleImage.CallAPiActivity;
import com.eleganzit.vkcofficial.api.uploadMultupleImage.GetResponse;
import com.eleganzit.vkcofficial.model.griddata.POGridResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.nereo.multi_image_selector.MultiImageSelector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlyCompleteActivity extends AppCompatActivity {
    RecyclerView rc_hourly;
    LinearLayout hourlylin;
    ArrayList<String> arrayList=new ArrayList<>();
    private static final int REQUEST_IMAGE = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 202;
    private ArrayList<String> mSelectPath;
    UserLoggedInSession userLoggedInSession;
    LinearLayout lin_nodata;
    ArrayList<String> imagesList=new ArrayList<>();

    CallAPiActivity callAPiActivity;
    public static String URL_COMPLETE = "http://itechgaints.com/VKC-API/addDefect";
    ProgressDialog progressDialog;
    ArrayList<String> imglist=new ArrayList<>();
    RecyclerView rc_image;

    ArrayList<String> str_photo_array=new ArrayList<>();
    private String mediapath,txt_pur_doc_num,article,item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_complete);
        rc_hourly=findViewById(R.id.rc_hourly);
        hourlylin=findViewById(R.id.hourlylin);
        lin_nodata = findViewById(R.id.lin_nodata);
        txt_pur_doc_num = getIntent().getStringExtra("pur_doc_num");
        article = getIntent().getStringExtra("article");
        item = getIntent().getStringExtra("item");
        callAPiActivity = new CallAPiActivity(HourlyCompleteActivity.this);
        userLoggedInSession = new UserLoggedInSession(HourlyCompleteActivity.this);
        progressDialog = new ProgressDialog(HourlyCompleteActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(HourlyCompleteActivity.this,LinearLayoutManager.VERTICAL,false);
        rc_hourly.setLayoutManager(layoutManager);
       //
        hourlylin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(HourlyCompleteActivity.this,
                        R.style.Theme_Dialog);
                d.setContentView(R.layout.upload_defects_dialog);

                TextView ok=d.findViewById(R.id.ok);
                rc_image=d.findViewById(R.id.rc_image);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(HourlyCompleteActivity.this,LinearLayoutManager.HORIZONTAL,false);
                rc_image.setLayoutManager(layoutManager);                TextView cancel=d.findViewById(R.id.cancel);
                final EditText ed_email=d.findViewById(R.id.ed_email);

                ed_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage();
                    }
                });
ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //d.dismiss();
        Log.d("afasghasd",txt_pur_doc_num+"   "+article+"   "+imglist);

        if (mSelectPath!=null)
        {
            if (mSelectPath.size()>0)
            {
                addMarkCompleteDetail2();
                d.dismiss();
            }
            else {
                Toast.makeText(HourlyCompleteActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(HourlyCompleteActivity.this, "Select Image", Toast.LENGTH_SHORT).show();

        }
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
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pendingGrid();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }




    public void addMarkCompleteDetail2(){

        progressDialog.show();

        HashMap<String, String> map = new HashMap<>();

        map.put("pur_doc_num", txt_pur_doc_num);
        map.put("article", article);
        map.put("item", item);

        callAPiActivity.doPostWithFiles(HourlyCompleteActivity.this, map, URL_COMPLETE, mSelectPath, "image[]", new GetResponse() {

            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {
                progressDialog.dismiss();
                String status = result.getString("status");
                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(HourlyCompleteActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    //PlanFragment myPhotosFragment = new PlanFragment();
/*
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, myPhotosFragment, "TAG")
                            .commit();*/
                }
                else
                {
                    Toast.makeText(HourlyCompleteActivity.this, ""+result.getString("message"), Toast.LENGTH_SHORT).show();
                }

                Log.d("messageeeeeeeeeee", "succccccccessss" + status);
            }

            @Override
            public void onFailureResult(JSONObject result) {
                progressDialog.dismiss();
                Toast.makeText(HourlyCompleteActivity.this, "Server or Internet Error"+result.toString(), Toast.LENGTH_SHORT).show();
                Log.d("messageeeeeeeeeee", result.toString());

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
                && ActivityCompat.checkSelfPermission(HourlyCompleteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(HourlyCompleteActivity.this);
            selector.multi();
             selector.count(6);
            selector.showCamera(true);

            selector.origin(mSelectPath);
            selector.start(HourlyCompleteActivity.this, REQUEST_IMAGE);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HourlyCompleteActivity.this, permission)) {
            new AlertDialog.Builder(HourlyCompleteActivity.this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HourlyCompleteActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(HourlyCompleteActivity.this, new String[]{permission}, requestCode);
        }
    }

    private void pendingGrid() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<POGridResponse> call=myInterface.pendingGriditem(txt_pur_doc_num,article,item);
        call.enqueue(new Callback<POGridResponse>() {
            @Override
            public void onResponse(Call<POGridResponse> call, Response<POGridResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_hourly.setVisibility(View.VISIBLE);
                        rc_hourly.setAdapter(new HourlyPOAdapter(response.body().getData(),HourlyCompleteActivity.this));


                    }
                    else {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_hourly.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<POGridResponse> call, Throwable t) {
                progressDialog.dismiss();

                lin_nodata.setVisibility(View.VISIBLE);
                rc_hourly.setVisibility(View.GONE);
                Toast.makeText(HourlyCompleteActivity.this, "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public class ImageAdapter extends RecyclerView.Adapter<HourlyCompleteActivity.ImageAdapter.ViewHolder>
    {
        ArrayList<String> img;
        Context context;

        public ImageAdapter(ArrayList<String> img, Context context) {
            this.img = img;
            this.context = context;
        }

        @NonNull
        @Override
        public HourlyCompleteActivity.ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upload_defects, viewGroup, false);
            HourlyCompleteActivity.ImageAdapter.ViewHolder myViewHolder = new HourlyCompleteActivity.ImageAdapter.ViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HourlyCompleteActivity.ImageAdapter.ViewHolder viewHolder, final int i) {
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
                Log.d("LOG_TAG", "Selected Images 1.5" + mSelectPath);

                Log.d("mediapathhhhhhhh", "" + mediapath);
                imglist.add(""+mediapath.trim());

                rc_image.setAdapter(new HourlyCompleteActivity.ImageAdapter(mSelectPath,HourlyCompleteActivity.this));

            }
        }
    }
}
