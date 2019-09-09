package com.example.madprojectx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tv_log;
    private Button btn_reg;

    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent LoginTo = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(LoginTo);
            finish();
        }

        tv_log = findViewById(R.id.textView18);
        tv_log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        btn_reg = findViewById(R.id.button6);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });
    }
}
