package com.example.madprojectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public EditText emailId, password;
    private Button btn_create;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPass);
        btn_create = findViewById(R.id.regButton);

        btn_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pass =  password.getText().toString();

                if (email.isEmpty()){
                    emailId.setError("Email field cannot be empty.");
                    emailId.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("Password field cannot be empty.");
                    password.requestFocus();
                } else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Fields cannot be empty!", Toast.LENGTH_LONG).show();
                } else if (!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration unsuccessful.", Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Unexpected error occurred.", Toast.LENGTH_LONG).show();
                }

                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
