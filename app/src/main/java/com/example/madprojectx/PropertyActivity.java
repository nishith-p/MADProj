package com.example.madprojectx;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
