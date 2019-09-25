package com.example.madprojectx.view;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReviewsActivity extends AppCompatActivity {

    EditText mReviewWords;
    Button mSubmitReview;
    Reviews rev;

    DatabaseReference revRef;

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

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rev.setReviewGiverID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                rev.setReviewWords(mReviewWords.getText().toString().trim());
                rev.setReviewPropName(string1);

                revRef.push().setValue(rev);

                Toast.makeText(AddReviewsActivity.this, "Review saved successfully.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddReviewsActivity.this, ReviewsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
