package com.freshbrigade.market;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class WalletActivity extends AppCompatActivity {

    TextView product,history,venders,wallet,exapairDate,pendingAmount,craditAmount,p ,helpline,duePayment;
    CardView cardView ;
    LinearLayout credit,pending,block1,due,pendingamountlayout,avilableLayout,pendingamountlayoutmain;
    ProgressBar progressBar;

    String url="https://freshbrigade.com/b2b/api/wallet.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        progressBar=findViewById(R.id.progress4);
        progressBar.setVisibility(View.VISIBLE);
        cardView = findViewById(R.id.walletCard);
        credit = findViewById(R.id.credit_click);
        pending = findViewById(R.id.pending);
        Button button = findViewById(R.id.clickForGetCredit);
        block1 = findViewById(R.id.block);
        due =findViewById(R.id.due);
        exapairDate =findViewById(R.id.expairdate);
        pendingAmount = findViewById(R.id.pendingamount);
        pendingamountlayout = findViewById(R.id.pendingamountlayout);
        craditAmount = findViewById(R.id.creditAmout);
        p =findViewById(R.id.pend);
        avilableLayout = findViewById(R.id.avilableLayout);
        helpline = findViewById(R.id.helpline);
        duePayment=findViewById(R.id.due_payment);
        pendingamountlayoutmain=findViewById(R.id.pendingamountlayoutmain);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                click();
            }
        });
        vollyWork();
    }

//    private void click() {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Log.e("iop",response );
//
//                    String status = jsonObject.getString("status");
//                    Log.e("xy","status");
//
//
//
//                    if(status.equals("ok")){
//
//                        String U_N_A = jsonObject.getString("activation_status");
//
//                        if(U_N_A.equals("u_n_a")){
//                            credit.setVisibility(View.VISIBLE);
//
//
//                        }else if(U_N_A.equals("pending")){
//                            pending.setVisibility(View.VISIBLE);
//                            credit.setVisibility(View.GONE);
//
//
//                        }else if(U_N_A.equals("active")){
//                            pendingamountlayoutmain.setVisibility(View.VISIBLE);
//                            avilableLayout.setVisibility(View.VISIBLE);
//                            cardView.setVisibility(View.VISIBLE);
//
//
//                        }else if(U_N_A.equals("due")){
//                            due.setVisibility(View.VISIBLE);
//
//                        }else {
//                            block1.setVisibility(View.VISIBLE);
//                        }
//
//                        progressBar.setVisibility(View.GONE);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(WalletActivity.this,e.getMessage(),Toast.LENGTH_LONG);
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//
//                Toast.makeText(WalletActivity.this,error.getMessage(),Toast.LENGTH_LONG);
//
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String>params=new HashMap<String,String>();
//
//
//                SessionManage sessionManage = new SessionManage(getApplicationContext());
//                HashMap<String,String> user = new HashMap<>();
//
//                user = sessionManage.get_client_session();
//
//                String c_code = user.get(sessionManage.CODE);
//
//
//                params.put("c_code",c_code);
//                params.put("action","update");
//
//                return params;
//            }
//        };   stringRequest.setShouldCache(false);
//        requestQueue.add(stringRequest);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//    }



    private void vollyWork() {

        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    Log.e("xyzx",response);

                    if(status.equals("ok")){


                        String U_N_A = jsonObject.getString("activation_status");

                        if(U_N_A.equals("u_n_a")){
                            credit.setVisibility(View.VISIBLE);




                        }else if(U_N_A.equals("pending")){

                            pending.setVisibility(View.VISIBLE);



                        }else if(U_N_A.equals("due")){


                            String dueAmount = jsonObject.getString("pending_amount");
                            duePayment.setText("₹ "+dueAmount);





                            due.setVisibility(View.VISIBLE);

                        }else if(U_N_A.equals("active")){

                            String expairDate = jsonObject.getString("expire_date");
                            Log.e("expairDate",expairDate);
                            exapairDate.setText("Your Expair Date is "+expairDate);

                            String creditAmount = jsonObject.getString("credit_amount");
                            Log.e("creditAmount",creditAmount);
                            craditAmount.setText("₹ "+creditAmount);

                            String pending = jsonObject.getString("pending_amount");
                            Log.e("pending",pending);
                            pendingAmount.setText("₹ "+pending);


                            Double a =  Double.parseDouble(creditAmount);
                            Double b = Double.parseDouble(pending);
                            Double avlbBalance = a-b;



                            pendingamountlayoutmain.setVisibility(View.VISIBLE);
                            // pendingamountlayout.setVisibility(View.VISIBLE);
                            avilableLayout.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);

                            p.setText("₹ "+avlbBalance);

                        }else {





                            helpline.setVisibility(View.VISIBLE);
                            block1.setVisibility(View.VISIBLE);


                        }

                        progressBar.setVisibility(View.GONE);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(WalletActivity.this,e.getMessage(),Toast.LENGTH_LONG);

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(WalletActivity.this,error.getMessage(),Toast.LENGTH_LONG);


            }
        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String>params=new HashMap<String,String>();
//                SharedPreferences sharedPreferences;
//
//                sharedPreferences = getSharedPreferences("com.freshbrigade.market.fcm", Context.MODE_PRIVATE);
//                String fcmId = sharedPreferences.getString("fcmID",null);
//
//                SessionManage sessionManage = new SessionManage(getActivity());
//                HashMap<String,String> user = new HashMap<>();
//
//                user = sessionManage.get_client_session();
//
//                String c_code = user.get(sessionManage.CODE);
//
//
//                params.put("c_code",c_code);
//                params.put("action","get");
//                //  params.put("com.freshbrigade.market.fcm",fcmId);
//                //  Log.e("xyz",c_code);
//
//                return params;
//            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
