package com.example.madprojectx.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.madprojectx.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PropertyActivity extends AppCompatActivity {

    TextView hTitle, hAdd1, hAdd2, hCity, hType, hPrice, hDesc;
    Button btn_rev;

    private String mTitle, mAdd1, mAdd2, mCity, mDesc, mType, mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        /*setContentView(R.layout.activity_property);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);*/

        /*book_tv = findViewById(R.id.hViewType);
        book_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PropertyActivity.this, BookingActivity.class));
            }
        });*/

        mTitle = getIntent().getExtras().get("Prop_Title").toString();
        mAdd1 = getIntent().getExtras().get("Prop_Add1").toString();
        mAdd2 = getIntent().getExtras().get("Prop_Add2").toString();
        mCity = getIntent().getExtras().get("Prop_City").toString();
        mDesc = getIntent().getExtras().get("Prop_Desc").toString();
        mType = getIntent().getExtras().get("Prop_Type").toString();
        mPrice = getIntent().getExtras().get("Prop_Price").toString();

        btn_rev= findViewById(R.id.button3);
        btn_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PropertyActivity.this, ReviewsActivity.class));
            }
        });

        InitializeControllers();

        hTitle.setText(mTitle);
        hAdd1.setText(mAdd1 + ",");
        hAdd2.setText(mAdd2);
        hCity.setText(mCity);
        hType.setText(mType);
        hPrice.setText("Rs. " + mPrice + ".00");
        hDesc.setText(mDesc);
    }

    private void InitializeControllers()
    {
        hTitle = findViewById(R.id.hostViewTitle);
        hAdd1 = findViewById(R.id.hViewAdd1);
        hAdd2 = findViewById(R.id.hViewAdd2);
        hCity = findViewById(R.id.hViewCity);
        hType = findViewById(R.id.hViewType);
        hPrice = findViewById(R.id.hViewPrice);
        hDesc = findViewById(R.id.hViewDesc);
    }
}
