package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpReviewActivity extends AppCompatActivity {

    private EditText uReview;

    private Button uButtonSubmit;
    private Button uButtonDelete;

    String Prop_Title;
    String Prop_Review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_review);

        //GET FROM THE INTENT
        Prop_Title = getIntent().getStringExtra("Prop_Title");
        Prop_Review = getIntent().getStringExtra("Prop_Review");

        //FIND VIEWS
        uReview = findViewById(R.id.writeReview);
        uButtonSubmit = findViewById(R.id.reviewSub);
        uButtonDelete = findViewById(R.id.reviewDel);

        //SET FIELDS
        uReview.setText(Prop_Review);

        //UPDATE
        uButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateReview();
            }
        });

        //DELETE
        uButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReview();
            }
        });
    }

    private void updateReview() {
        final String newReview = uReview.getText().toString().trim();

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference("Reviews");

        Query query = mRef.orderByChild("reviewPropName").equalTo(Prop_Title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().child("reviewWords").setValue(newReview);
                }
                //mProgressDialog.dismiss();
                Toast.makeText(UpReviewActivity.this, "Database updated.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpReviewActivity.this, MyReviewsActivity.class));
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void deleteReview(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpReviewActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mFirebaseDatabase.getReference("Reviews");

                Query mQuery = mRef.orderByChild("reviewPropName").equalTo(Prop_Title);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(UpReviewActivity.this, "Entry deleted successfully.",Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(UpReviewActivity.this, MyReviewsActivity.class);
                        startActivity(myIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UpReviewActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
