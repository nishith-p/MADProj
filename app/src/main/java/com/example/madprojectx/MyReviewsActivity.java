package com.example.madprojectx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
