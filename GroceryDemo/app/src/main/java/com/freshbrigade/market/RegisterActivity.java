package com.freshbrigade.market;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.freshbrigade.market.Config.BaseURL;

import com.freshbrigade.market.Config.SharedPref;
import com.freshbrigade.market.fcm.MyFirebaseRegister;
import com.freshbrigade.market.util.ConnectivityReceiver;
import com.freshbrigade.market.util.CustomProgress;
import com.freshbrigade.market.util.CustomVolleyJsonRequest;
import com.freshbrigade.market.util.Session_management;

public class RegisterActivity extends AppCompatActivity  {

    private static String TAG = RegisterActivity.class.getSimpleName();

    private RelativeLayout upload_photo ;
    Spinner bt_layout;

    String base64String="";

    CustomProgress customProgress;

    String mob;

    private EditText et_phone, et_name, et_password, et_email ,et_dis;
    private RelativeLayout btn_register;
    private TextView tv_phone, tv_name, tv_password, tv_email;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String bussType;
    String[] country = { "Retailer", "Wholesaler", "Distributor", "Manufacturer", "Brand","Agent"};

    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title

        setContentView(R.layout.activity_register);


        customProgress = new CustomProgress(this);


        upload_photo = findViewById(R.id.uploadPhoto);
        bt_layout = findViewById(R.id.business_type);
        imageView = findViewById(R.id.docImg);

        et_phone = (EditText) findViewById(R.id.et_reg_phone);
        et_name = (EditText) findViewById(R.id.et_reg_name);
        et_password = (EditText) findViewById(R.id.et_reg_password);
        et_email = (EditText) findViewById(R.id.et_reg_email);
        tv_password = (TextView) findViewById(R.id.tv_reg_password);
        tv_phone = (TextView) findViewById(R.id.tv_reg_phone);
        tv_name = (TextView) findViewById(R.id.tv_reg_name);
        tv_email = (TextView) findViewById(R.id.tv_reg_email);
        btn_register = (RelativeLayout) findViewById(R.id.btnRegister);
        et_dis = findViewById(R.id.edit_text_dis);


        mob = getIntent().getStringExtra("mob");

        et_phone.setText(mob);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 attemptRegister();
            }
        });


        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        bt_layout.setAdapter(aa);



        upload_photo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    private void attemptRegister() {

        customProgress.show("");

        tv_phone.setText(getResources().getString(R.string.et_login_phone_hint));
        tv_email.setText(getResources().getString(R.string.tv_login_email));
        tv_name.setText(getResources().getString(R.string.tv_reg_name_hint));
        tv_password.setText(getResources().getString(R.string.tv_login_password));

        tv_name.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_phone.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_password.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_email.setTextColor(getResources().getColor(R.color.dark_gray));

        String getphone = et_phone.getText().toString();
        String getname = et_name.getText().toString();
        String getpassword = et_password.getText().toString();
        String getemail = et_email.getText().toString();
        String getdis = et_dis.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(getphone)) {
            tv_phone.setTextColor(getResources().getColor(R.color.black));
            focusView = et_phone;
            cancel = true;
        } else if (!isPhoneValid(getphone)) {
            tv_phone.setText(getResources().getString(R.string.phone_too_short));
            tv_phone.setTextColor(getResources().getColor(R.color.black));
            focusView = et_phone;
            cancel = true;
        }



        if (TextUtils.isEmpty(getname)) {
            tv_name.setTextColor(getResources().getColor(R.color.black));
            focusView = et_name;
            cancel = true;
        }

//        if (TextUtils.isEmpty(getpassword)) {
//            tv_password.setTextColor(getResources().getColor(R.color.black));
//            focusView = et_password;
//            cancel = true;
//        } else if (!isPasswordValid(getpassword)) {
//            tv_password.setText(getResources().getString(R.string.password_too_short));
//            tv_password.setTextColor(getResources().getColor(R.color.black));
//            focusView = et_password;
//            cancel = true;
//        }

//        if (TextUtils.isEmpty(getemail)) {
//            focusView = et_email;
//            cancel = true;
//
//        } else if (!isEmailValid(getemail)) {
//            tv_email.setText(getResources().getString(R.string.invalide_email_address));
//            tv_email.setTextColor(getResources().getColor(R.color.black));
//            focusView = et_email;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (ConnectivityReceiver.isConnected()) {
                Log.e("sdsadsfsdfsd","dsadsadfsafsdfsd");
                makeRegisterRequest(getname, getphone, getemail, getpassword , getdis);
            }
        }


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }

    /**
     * Method to make json object request where json response starts wtih
     */
    private void makeRegisterRequest(String name, String mobile,
                                     String email, String password , String dis) {

        // Tag used to cancel the request


        Log.e("0000000000", "runnnnnn");

        String tag_json_obj = "json_register_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_name", name);
        params.put("user_mobile", mobile);
        params.put("user_email", mobile);
        params.put("password", "qwerty1234");
        params.put("business_type",bt_layout.getSelectedItem().toString());
        params.put("description",dis);
        params.put("upload_document_photo",base64String);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.REGISTER_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("0000000000", response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {


                        customProgress.hide();

                        SharedPref.putBoolean(RegisterActivity.this,"login",true);

                        JSONObject obj = response.getJSONObject("data");
                       // Log.e("sadfsfsdf",is_newuser);
                        String user_id = obj.getString("user_id");
                        String user_fullname = obj.getString("user_fullname");
                        String user_email = obj.getString("user_email");
                        String user_phone = obj.getString("user_phone");
                        String user_image = obj.getString("user_image");
                        String wallet_ammount = obj.getString("wallet");
                        String reward_points = obj.getString("rewards");
                        Session_management sessionManagement = new Session_management(RegisterActivity.this);
                        sessionManagement.createLoginSession(user_id, user_email, user_fullname, user_phone, user_image, wallet_ammount, reward_points, "", "", "", "", "");
                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        MyFirebaseRegister myFirebaseRegister=new MyFirebaseRegister(RegisterActivity.this);
                        myFirebaseRegister.RegisterUser(user_id);



                        /////////////////////////////////////////

//                        String msg = response.getString("message");
//                        Toast.makeText(RegisterActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
//
//                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
//                        startActivity(i);
//                        finish();
//                        btn_register.setEnabled(false);

                    } else {
                        String error = response.getString("error");
                        Log.d("chackerror", error);
                        btn_register.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
             base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.e("sdsafdsdf","fsdfsdf");

            Log.e("sdsafdsdf",base64String);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}
