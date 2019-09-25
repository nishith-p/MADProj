package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.madprojectx.R;
import com.example.madprojectx.holder.MyPropertyViewHolder;
import com.example.madprojectx.model.Property;
import com.example.madprojectx.model.Reviews;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyReviewsActivity extends AppCompatActivity {

    private Query propRef;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String currentUserID, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        propRef = FirebaseDatabase.getInstance().getReference().child("Reviews").orderByChild("reviewGiverID").equalTo(currentUserID);

        recyclerView = findViewById(R.id.my_review_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Reviews> options = new FirebaseRecyclerOptions.Builder<Reviews>().setQuery(propRef, Reviews.class).build();

        FirebaseRecyclerAdapter<Reviews, MyPropertyViewHolder> adapter = new FirebaseRecyclerAdapter<Reviews, MyPropertyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyPropertyViewHolder holder, int position, @NonNull final Reviews model) {
                holder.propName.setText(model.getReviewWords());
                holder.propCity.setText(model.getReviewPropName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(MyReviewsActivity.this, UpReviewActivity.class);
                        intent.putExtra("Prop_Title", model.getReviewPropName());
                        intent.putExtra("Prop_Review", model.getReviewWords());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyPropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_my_prop, parent, false);
                MyPropertyViewHolder holder = new MyPropertyViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
