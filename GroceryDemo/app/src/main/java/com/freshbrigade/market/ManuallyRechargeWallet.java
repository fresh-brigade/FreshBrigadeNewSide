package com.freshbrigade.market;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ManuallyRechargeWallet extends AppCompatActivity {

    TextView tv_active_code;
    Button btn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_recharge_wallet);

        tv_active_code= findViewById(R.id.tv_active_code);
        btn = findViewById(R.id.btn_active_code);
       // editText = findViewById(R.id.num)

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"click working" , Toast.LENGTH_LONG).show();
                 //   String number=edittext1.getText().toString();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+"0"));
                    startActivity(callIntent);
            }
        });
//        tv_active_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"click working" , Toast.LENGTH_LONG).show();
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:0377778888"));
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                startActivity(callIntent);
//            }
//        });
    }
}
