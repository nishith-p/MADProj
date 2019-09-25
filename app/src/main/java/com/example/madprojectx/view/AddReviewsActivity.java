package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.model.Reviews;
import com.example.madprojectx.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddReviewsActivity extends AppCompatActivity {

    EditText mReviewWords;
    Button mSubmitReview;
    Reviews rev;

    FirebaseUser firebaseUser;
    DatabaseReference revRef;
    DatabaseReference dbRef2;

    String bFname;
    String bLname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        revRef = FirebaseDatabase.getInstance().getReference().child("Reviews");

        mSubmitReview = findViewById(R.id.reviewSub);
        mReviewWords = findViewById(R.id.writeReview);

        SharedPreferences prefs = getSharedPreferences("reviewData", MODE_PRIVATE);

        final String string1 = prefs.getString("revTitle", "");

        rev = new Reviews();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef2 = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                bFname = user.getFname();
                bLname = user.getLname();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rev.setReviewGiverID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                rev.setReviewWords(mReviewWords.getText().toString().trim());
                rev.setReviewPropName(string1);
                rev.setReviewFname(bFname);
                rev.setReviewLname(bLname);

                revRef.push().setValue(rev);

                Toast.makeText(AddReviewsActivity.this, "Review saved successfully.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddReviewsActivity.this, ReviewsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
