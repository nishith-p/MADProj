package com.example.madprojectx.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.madprojectx.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PropertyActivity extends AppCompatActivity {

    TextView book_tv;
    Button btn_rev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        /*setContentView(R.layout.activity_property);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);*/

        book_tv = findViewById(R.id.textView12);
        book_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PropertyActivity.this, BookingActivity.class));
            }
        });

        btn_rev= findViewById(R.id.button3);
        btn_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PropertyActivity.this, ReviewsActivity.class));
            }
        });
    }
}
