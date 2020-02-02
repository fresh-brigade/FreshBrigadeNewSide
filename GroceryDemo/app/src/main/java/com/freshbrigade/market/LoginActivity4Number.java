package com.freshbrigade.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.freshbrigade.market.Config.BaseURL;
import com.freshbrigade.market.Config.MySingleton;
import com.freshbrigade.market.Config.SharedPref;
import com.freshbrigade.market.fcm.MyFirebaseRegister;

import com.freshbrigade.market.util.CustomProgress;
import com.freshbrigade.market.util.CustomVolleyJsonRequest;
import com.freshbrigade.market.util.Session_management;

public class LoginActivity4Number extends AppCompatActivity {

    EditText otp, number_;
    ImageButton btn,btn_otp_code;
    TextView tvtext_of_otp;
    int rand;
    boolean isChackUser=false;

    CustomProgress customProgress;

    boolean numberCompletedStatus;
    RelativeLayout otpLayout_ , numberLayout_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_number_layout);
        rand=getRandomNumber(100000,999999);
        otp = findViewById(R.id.otp_confirm);
        number_ = findViewById(R.id.phone_number);
        btn = findViewById(R.id.btn_Otp);
        otpLayout_ = findViewById(R.id.otpLayout);
        numberLayout_ = findViewById(R.id.numberLayout);
        tvtext_of_otp = findViewById(R.id.text_of_otp);
        btn_otp_code = findViewById(R.id.btn_otp_code);

        customProgress = new CustomProgress(LoginActivity4Number.this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isChackUser)
                {
                    SendOtp(number_.getText().toString());
                }
                else{
                    checkOtp(number_.getText().toString());
                }


            }
        });

        number_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<10){
                    number_.setError("Please Enter 10 numbers!");
                }
                else if(s.length()>10){
                    number_.setError("Please Enter 10 numbers!");
                }
                else if(s.length()==10){
                    numberCompletedStatus=true;
                }

            }
        });

        btn_otp_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOtp(number_.getText().toString());
            }
        });
    }


    private void SendOtp(final String mobileNumber) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://control.msg91.com/api/sendotp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aaa",response );
                        //   JSONArray jsonArray = new JSONArray(response);
                        try {

                            JSONObject jsonObject = new  JSONObject(response);
                            String status = jsonObject.getString("type");
                            String message = jsonObject.getString("message").toString();

                            Log.e("phpppp",response);
                            if(status.equals("success")){
                              //  progressBar.setVisibility(View.GONE);

                                isChackUser=true;

                                tvtext_of_otp.setText("Otp Verify");
                                otpLayout_.setVisibility(View.VISIBLE);
                                numberLayout_.setVisibility(View.GONE);

//                                Intent intent=new Intent(LoginActivity4Number.this,MainActivity.class);
//                                intent.putExtra("mobilenumber",mobileNumber);
//                                startActivity(intent);
//                                finish();


                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            Log.e("json",e.toString());
                            e.printStackTrace();
                          //  progressBar.setVisibility(View.GONE);
                            //  progressBar.dismiss();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.e("json",error.toString());
             //   progressBar.setVisibility(View.GONE);
//                progressBar.dismiss();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();


                params.put("authkey","281608AqE2Kl6kNfy5d08c2d2");
                params.put("mobile","91"+mobileNumber);
                params.put("otp",String.valueOf(rand));
                params.put("sender","FreshBrigade");
                params.put("message","Your OTP for Fresh Brigade is : "+rand+" .");
                params.put("otp_length","6");
                params.put("otp_expiry","1");

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 96) + min;
    }



    private void checkOtp(final String mobileNumber) {

    customProgress.show("");

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://control.msg91.com/api/verifyRequestOTP.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aaa",response );
                        //   JSONArray jsonArray = new JSONArray(response);
                        try {

                            JSONObject jsonObject = new  JSONObject(response);
                            String status = jsonObject.getString("type").toString();
                            String message = jsonObject.getString("message").toString();

                            Log.e("phpppp",response);
                            if(status.equals("success")){



                                SharedPref.putBoolean(LoginActivity4Number.this,"login",true);
                                makeLoginRequest(mobileNumber);


                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            Log.e("json",e.toString());
                            e.printStackTrace();
                           // progressBar.setVisibility(View.INVISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.e("json",error.toString());
             //   progressBar.setVisibility(View.INVISIBLE);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String>params=new HashMap<String,String>();
                params.put("authkey","281608AqE2Kl6kNfy5d08c2d2");
                params.put("mobile","91"+mobileNumber);
                params.put("otp",String.valueOf(rand));

                return params;
            }
        };

        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



    private void makeLoginRequest(String email) {

        // Tag used to cancel the request
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_email", email);
//        params.put("password", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.LOGIN_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("sdfsf", response.toString());

                try {
                    Boolean status = response.getBoolean("responce");




                    if (status) {
                        customProgress.hide();

                        String is_newuser =response.getString("is_newuser");

                        if(is_newuser.equals("n"))
                        {

                            SharedPref.putBoolean(LoginActivity4Number.this,"login",true);

                            JSONObject obj = response.getJSONObject("data");
                            Log.e("sadfsfsdf",is_newuser);
                            String user_id = obj.getString("user_id");
                            String user_fullname = obj.getString("user_fullname");
                            String user_email = obj.getString("user_email");
                            String user_phone = obj.getString("user_phone");
                            String user_image = obj.getString("user_image");
                            String wallet_ammount = obj.getString("wallet");
                            String reward_points = obj.getString("rewards");
                            Session_management sessionManagement = new Session_management(LoginActivity4Number.this);
                            sessionManagement.createLoginSession(user_id, user_email, user_fullname, user_phone, user_image, wallet_ammount, reward_points, "", "", "", "", "");
                            Intent i = new Intent(LoginActivity4Number.this, MainActivity.class);
                            startActivity(i);
                            finish();
                            MyFirebaseRegister myFirebaseRegister=new MyFirebaseRegister(LoginActivity4Number.this);
                            myFirebaseRegister.RegisterUser(user_id);
                        }else
                        {
                            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                            intent.putExtra("mob",number_.getText().toString());
                            startActivity(intent);
                            finish();
                        }



                      //  btn_continue.setEnabled(false);

                    } else {
                        String error = response.getString("error");
                       // btn_continue.setEnabled(true);

                      //  Toast.makeText(LoginActivity4Number.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("fgfdgf", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                   // Toast.makeText(LoginActivity4Number.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}
