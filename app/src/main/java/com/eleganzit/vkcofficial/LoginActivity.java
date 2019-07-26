package com.eleganzit.vkcofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcofficial.api.RetrofitAPI;
import com.eleganzit.vkcofficial.api.RetrofitInterface;
import com.eleganzit.vkcofficial.model.LoginResponse;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    LinearLayout login;
    TextView forgotpassword;
    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    TextInputEditText ed_email,ed_pw;
    private String Token;
    private String device_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        userLoggedInSession=new UserLoggedInSession(LoginActivity.this);
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        ed_pw=findViewById(R.id.ed_pw);
        ed_email=findViewById(R.id.ed_email);

        forgotpassword=findViewById(R.id.forgotpassword);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid())
                {
                    if (Token!=null){
                        loginUser();
                    }
                    else
                    {
                        Thread t=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Token= FirebaseInstanceId.getInstance().getToken();
                                if (Token!=null)
                                {
                                    Log.d("mytokenn", Token);

                                    device_token=Token;
                                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                                    StrictMode.setThreadPolicy(threadPolicy);
                                    try {
                                        JSONObject jsonObject=new JSONObject(Token);
                                        Log.d("mytoken", jsonObject.getString("token"));
                                        //devicetoken=jsonObject.getString("token");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //getLoginBoth(Token);

                                }
                                else
                                {

                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });t.start();
                        loginUser();
                    }

                }

            }
        });forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                Token= FirebaseInstanceId.getInstance().getToken();
                if (Token!=null)
                {
                    Log.d("mytokenn", Token);

                    device_token=Token;
                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                    StrictMode.setThreadPolicy(threadPolicy);
                    try {
                        JSONObject jsonObject=new JSONObject(Token);
                        Log.d("mytoken", jsonObject.getString("token"));
                        //devicetoken=jsonObject.getString("token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //getLoginBoth(Token);

                }
                else
                {

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });t.start();
    }

    private void loginUser() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call=myInterface.loginOffical(ed_email.getText().toString(),ed_pw.getText().toString(),device_token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        if (response.body().getData()!=null)
                        {
                            String email,id,username;
                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                email=response.body().getData().get(i).getOfficalEmailId();
                                id=response.body().getData().get(i).getOfficalId();
                                username=response.body().getData().get(i).getOfficalName();
                                userLoggedInSession.createLoginSession(email,id,username);

                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }



                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("dfddddddd",""+t.getMessage());
                Toast.makeText(LoginActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (ed_email.getText().toString().equals("")) {
            ed_email.setError(""+getResources().getString(R.string.Please_enter_email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_email);

            ed_email.requestFocus();
            return false;
        }
        else if (!matcher.matches()) {
            ed_email.setError(""+getResources().getString(R.string.Please_Enter_Valid_Email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_email);

            ed_email.requestFocus();
            return false;
        }
        else  if (ed_pw.getText().toString().equals("")) {

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_pw);

            ed_pw.requestFocus();
            return false;
        }


        return true;
    }
}
