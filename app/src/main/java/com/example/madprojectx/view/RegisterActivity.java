package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public EditText firstName, lastName, emailId, password, cpassword;
    private Button btn_create;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.regFname);
        lastName = findViewById(R.id.regLname);
        emailId = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPass);
        cpassword = findViewById(R.id.regCpass);
        btn_create = findViewById(R.id.regButton);

        btn_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String email = emailId.getText().toString();
                String pass = password.getText().toString();
                final String fname = firstName.getText().toString();
                final String lname = lastName.getText().toString();
                String cPass = cpassword.getText().toString();

                if (fname.isEmpty()) {
                    firstName.setError("First name field cannot be empty.");
                    firstName.requestFocus();
                } else if (lname.isEmpty()) {
                    lastName.setError("Email field cannot be empty.");
                    lastName.requestFocus();
                } else if (email.isEmpty()) {
                    emailId.setError("Email field cannot be empty.");
                    emailId.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("Password field cannot be empty.");
                    password.requestFocus();
                } else if (!(pass.equals(cPass))){
                    Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                } else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Fields cannot be empty!", Toast.LENGTH_LONG).show();
                } else if (!(email.isEmpty() && pass.isEmpty() && fname.isEmpty() && lname.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration unsuccessful.", Toast.LENGTH_LONG).show();
                            } else {
                                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                                User user = new User(fname, lname, email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Registration unsuccessful", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
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

    @Override
    protected void onStart() {
        super.onStart();

        if(mFirebaseAuth.getCurrentUser() != null){
            //
        }
    }
}
