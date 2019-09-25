package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madprojectx.R;
import com.example.madprojectx.holder.MyPropertyViewHolder;
import com.example.madprojectx.holder.ReviewViewHolder;
import com.example.madprojectx.model.Property;
import com.example.madprojectx.model.Reviews;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReviewsActivity extends AppCompatActivity {

    private Query propRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        SharedPreferences prefs = getSharedPreferences("reviewData", MODE_PRIVATE);

        final String string1 = prefs.getString("revTitle", "");

        propRef = FirebaseDatabase.getInstance().getReference().child("Reviews").orderByChild("reviewPropName").equalTo(string1);

        recyclerView = findViewById(R.id.reviewRC);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Reviews> options = new FirebaseRecyclerOptions.Builder<Reviews>().setQuery(propRef, Reviews.class).build();

        FirebaseRecyclerAdapter<Reviews, ReviewViewHolder> adapter = new FirebaseRecyclerAdapter<Reviews, ReviewViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ReviewViewHolder holder, int position, @NonNull final Reviews model) {
                holder.reviewText.setText(model.getReviewWords());
                holder.reviewName.setText(model.getReviewFname() + " " + model.getReviewLname());
            }

            @NonNull
            @Override
            public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_reviews,parent,false);
                ReviewViewHolder holder = new ReviewViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addrev, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_rev) {
            Intent add_rev = new Intent(ReviewsActivity.this, AddReviewsActivity.class);
            startActivity(add_rev);
        }

        return super.onOptionsItemSelected(item);
    }
}
