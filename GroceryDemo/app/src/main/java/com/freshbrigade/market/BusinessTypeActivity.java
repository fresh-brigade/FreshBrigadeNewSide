package com.freshbrigade.market;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class BusinessTypeActivity extends AppCompatActivity {

    Toolbar toolbar;
    int padding = 0;

    Switch retailer,wholesaler,distributor,manufacturer,brand,agent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_type);

        retailer = findViewById(R.id.retailer);
        wholesaler = findViewById(R.id.wholesaler);
        distributor = findViewById(R.id.distributor);
        manufacturer = findViewById(R.id.manufacturer);
        brand = findViewById(R.id.brand);
        agent = findViewById(R.id.agent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(padding, toolbar.getPaddingTop(), padding, toolbar.getPaddingBottom());
        toolbar.setTitle("Business Type");
        setSupportActionBar(toolbar);

        retailer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }else {

                }
            }
        });


        Boolean switchState = retailer.isChecked();
        Log.d("shcdn", String.valueOf(switchState));
        Toast.makeText(getApplicationContext(),String.valueOf(switchState),Toast.LENGTH_LONG).show();

    }
}
