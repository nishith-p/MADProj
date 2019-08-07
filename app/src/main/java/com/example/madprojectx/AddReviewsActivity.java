package com.example.madprojectx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AddReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
