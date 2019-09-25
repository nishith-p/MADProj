package com.example.madprojectx.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.madprojectx.R;
import com.example.madprojectx.model.Property;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PropertyActivity extends AppCompatActivity {

    TextView hTitle, hAdd1, hAdd2, hCity, hType, hPrice, hDesc, hPhone;
    Button btn_rev, btn_call, btn_book;
    ConstraintLayout cons1, cons2, cons3;
    ImageView pMale, pFemale, pImage;
    private String mTitle, mAdd1, mAdd2, mCity, mDesc, mType, mPrice, mOp1, mOp2, mOp3, mOp4, mGender, mImage, mPhone, mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        cons1 = findViewById(R.id.CONS_LAY_WIFI);
        cons2 = findViewById(R.id.CONS_LAY_AC);
        cons3 = findViewById(R.id.CONS_LAY_L);

        pMale = findViewById(R.id.pm_male);
        pFemale = findViewById(R.id.pm_fem);

        mTitle = getIntent().getExtras().get("Prop_Title").toString();
        mAdd1 = getIntent().getExtras().get("Prop_Add1").toString();
        mAdd2 = getIntent().getExtras().get("Prop_Add2").toString();
        mCity = getIntent().getExtras().get("Prop_City").toString();
        mDesc = getIntent().getExtras().get("Prop_Desc").toString();
        mType = getIntent().getExtras().get("Prop_Type").toString();
        mPrice = getIntent().getExtras().get("Prop_Price").toString();
        mGender = getIntent().getExtras().get("Prop_Gender").toString();
        mImage = getIntent().getExtras().get("Prop_Img").toString();
        mPhone = getIntent().getExtras().get("Prop_Phone").toString();
        mUID = getIntent().getExtras().get("Prop_UID").toString();

        if (getIntent().getExtras().get("Prop_Op1") != null){
            mOp1 = getIntent().getExtras().get("Prop_Op1").toString();
        }

        if (getIntent().getExtras().get("Prop_Op2") != null){
            mOp2 = getIntent().getExtras().get("Prop_Op2").toString();
        }

        if (getIntent().getExtras().get("Prop_Op3") != null){
            mOp3 = getIntent().getExtras().get("Prop_Op3").toString();
        }

        SharedPreferences prefs = getSharedPreferences("reviewData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("revTitle", mTitle);
        editor.putString("revGender", mGender);
        editor.putString("revCity", mCity);
        editor.putString("revType", mType);
        editor.putString("revPrice", mPrice);
        editor.putString("revOwner", mUID);
        editor.commit();

        btn_rev= findViewById(R.id.revButtonProp);
        btn_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent revIntent = new Intent(PropertyActivity.this, ReviewsActivity.class);
                //revIntent.putExtra("Prop_Title2", mTitle);
                startActivity(revIntent);
            }
        });

        btn_book = findViewById(R.id.propBookButton);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookIntent = new Intent(PropertyActivity.this, BookingActivity.class);
                startActivity(bookIntent);
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
        pImage = findViewById(R.id.propImg35);

        Picasso.get()
                .load(mImage)
                .fit()
                .centerCrop()
                .into(pImage);

        hPhone = findViewById(R.id.prop_view_call);

        hPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mPhone));
                startActivity(callIntent);
            }
        });


        if (mGender.equals("Male")){
            pFemale.setVisibility(View.GONE);
        } else {
            pMale.setVisibility(View.GONE);
        }

        if(mOp1 !=null){
            cons1.setVisibility(View.VISIBLE);
        } else {
            cons1.setVisibility(View.GONE);
        }

        if(mOp2 !=null){
            cons2.setVisibility(View.VISIBLE);
        } else {
            cons2.setVisibility(View.GONE);
        }

        if(mOp3 !=null){
            cons3.setVisibility(View.VISIBLE);
        } else {
            cons3.setVisibility(View.GONE);
        }

    }
}
