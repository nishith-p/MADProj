package com.example.madprojectx.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.madprojectx.R;

public class BookingActivity extends AppCompatActivity {

    private Button btn_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_book = findViewById(R.id.button14);
        btn_book.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(BookingActivity.this, PaymentActivity.class));
            }
        });
    }
}
