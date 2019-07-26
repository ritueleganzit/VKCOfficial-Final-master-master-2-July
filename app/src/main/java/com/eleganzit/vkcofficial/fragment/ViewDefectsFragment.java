package com.eleganzit.vkcofficial.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
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
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcofficial.GalleryActivity;
import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.AllArticleResponse;
import com.eleganzit.vkcofficial.model.ArticleWiseDefectResponse;
import com.eleganzit.vkcofficial.model.TotalVendorDefect;
import com.eleganzit.vkcofficial.model.TotalVendorDefectResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDefectsFragment extends Fragment {

    ArrayList<TotalVendorDefect> arrayList=new ArrayList<>();
    ArrayList<String> imagelist;
    List<String> articlelist;

    RecyclerView rc_view_defects;
    LinearLayout viewdefectslin,save;
    public ViewDefectsFragment() {
        // Required empty public constructor
    }
    TextInputEditText edstartdate;
    TextView foundrec;

    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view_defects, container, false);
        rc_view_defects=v.findViewById(R.id.rc_view_defects);
        viewdefectslin=v.findViewById(R.id.viewdefectslin);
        edstartdate=v.findViewById(R.id.edstartdate);
        foundrec=v.findViewById(R.id.foundrec);
        save=v.findViewById(R.id.save);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        userLoggedInSession = new UserLoggedInSession(getActivity());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edstartdate.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edstartdate);

                }
                else
                {
                    String art[]=edstartdate.getText().toString().split("-");
                    searchData(art[0],art[1]);
                }
            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_view_defects.setLayoutManager(layoutManager);
        viewdefectslin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edstartdate.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edstartdate);

                }
                else
                {
                    String art[]=edstartdate.getText().toString().split("-");
                    viewdefectsgal(art[0],art[1]);

                }

            }
        });
        edstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articlelist.size()>0){
                    final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, articlelist);

                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

                    builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();


                            edstartdate.setText(articlelist.get(i));




                        }
                    });
                    builder.show();
                }

            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeActivity.textTitle.setText("View Defects");
        assignArticle();


    }

    private void viewdefectsgal(String art,String item) {
        imagelist=new ArrayList<>();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ArticleWiseDefectResponse> call=myInterface.articleWiseDefect(art,item);
        call.enqueue(new Callback<ArticleWiseDefectResponse>() {
            @Override
            public void onResponse(Call<ArticleWiseDefectResponse> call, Response<ArticleWiseDefectResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if(response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            imagelist.add(response.body().getData().get(i).getDefectProductImage());
                        }

                        Log.d("ffffffffffff",""+response.body().getData());
if (imagelist.size()>0) {
    startActivity(new Intent(getActivity(), GalleryActivity.class)
            .putExtra("imagegal", imagelist));
}
                    }
                    {
                       // Toast.makeText(getActivity(), "No Imagespppp"+response.body().getData().size(), Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "No Images", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArticleWiseDefectResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void searchData(String art,String item) {
        arrayList=new ArrayList();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<TotalVendorDefectResponse> call=myInterface.totalVendorDefect(art,item);
        call.enqueue(new Callback<TotalVendorDefectResponse>() {
            @Override
            public void onResponse(Call<TotalVendorDefectResponse> call, Response<TotalVendorDefectResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if(response.body().getData()!=null)
                    {

                        Log.d("ffffffffffff",""+response.body().getData());
                        arrayList.addAll(response.body().getData());
                        rc_view_defects.setAdapter(new ViewDefectsAdapter(arrayList,getActivity()));
                        if (response.body().getData().size()==1)
                        {
                            foundrec.setText("Found "+response.body().getData().size()+ " Record");

                        }
                        else
                        {
                            foundrec.setText("Found "+response.body().getData().size()+ " Records");

                        }
                    }
                }
                else
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TotalVendorDefectResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void assignArticle() {
        articlelist=new ArrayList();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AllArticleResponse> call=myInterface.allArticleList();
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
                        edstartdate.setText(articlelist.get(0));
                    }


                }
                else
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllArticleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class ViewDefectsAdapter extends RecyclerView.Adapter<ViewDefectsAdapter.MyViewHolder> {

        List<TotalVendorDefect> campaigns;
        Context context;
        Activity activity;

        public ViewDefectsAdapter(List<TotalVendorDefect> campaigns, Context context) {
            this.campaigns = campaigns;
            this.context = context;
            activity = (Activity) context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_view_defects, viewGroup, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

            TotalVendorDefect totalVendorDefect=campaigns.get(i);
            holder.totalDefect.setText(totalVendorDefect.getTotalDefect()+" Defects");
            holder.vendor_name.setText(totalVendorDefect.getVendorName());

        }

        @Override
        public int getItemCount() {
            return campaigns.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView totalDefect,vendor_name;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                vendor_name=itemView.findViewById(R.id.vendor_name);
                totalDefect=itemView.findViewById(R.id.totalDefect);

            }
        }
    }



}
