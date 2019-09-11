package com.example.madprojectx.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.madprojectx.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditPropertyActivity extends AppCompatActivity {

    private ImageView imgView;

    private Button nSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference nRef = FirebaseDatabase.getInstance().getReference();

        nSendData = (Button) findViewById(R.id.sendData);

        nSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
