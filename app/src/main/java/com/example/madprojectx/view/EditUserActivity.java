package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class EditUserActivity extends AppCompatActivity {

    Button euSave, euDelete;
    EditText euFirst, euLast, euPass;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        euSave = findViewById(R.id.aeSave);
        euDelete = findViewById(R.id.aeDel);
        euFirst = findViewById(R.id.aeFirstName);
        euLast = findViewById(R.id.aeLastName);
        euPass = findViewById(R.id.euPass);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                euFirst.setText(user.getFname());
                euLast.setText(user.getLname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        euSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(euFirst.getText().toString(), euLast.getText().toString());
            }
        });

        euDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditUserActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("You cannot undo this action.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(EditUserActivity.this,"Account has been deleted.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditUserActivity.this, WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(EditUserActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });


    }

    private void updateProfile(String uFirst, String uSecond) {
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fname",uFirst);
        hashMap.put("lname",uSecond);

        dbRef.updateChildren(hashMap);

        Toast.makeText(EditUserActivity.this, "Profile updated.", Toast.LENGTH_SHORT).show();
        //finish();
    }
}
