package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.holder.MyPropertyViewHolder;
import com.example.madprojectx.model.Book;
import com.example.madprojectx.model.Reviews;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyBookingsActivity extends AppCompatActivity {

    private Query propRef;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String currentUserID, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        propRef = FirebaseDatabase.getInstance().getReference().child("Bookings").orderByChild("bookBookerID").equalTo(currentUserID);

        recyclerView = findViewById(R.id.booking_review);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Book> options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(propRef, Book.class).build();

        FirebaseRecyclerAdapter<Book, MyPropertyViewHolder> adapter = new FirebaseRecyclerAdapter<Book, MyPropertyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyPropertyViewHolder holder, int position, @NonNull final Book model) {
                holder.propName.setText(model.getBookHostName());
                holder.propCity.setText("Booking ID: " + model.getBookBookerID());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        deleteEntry(model.getBookHostName());
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
    private void deleteEntry(final String currentTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyBookingsActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to cancel your booking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mFirebaseDatabase.getReference("Bookings");

                Query mQuery = mRef.orderByChild("bookHostName").equalTo(currentTitle);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(MyBookingsActivity.this, "Entry deleted successfully.",Toast.LENGTH_SHORT).show();
                        /*Intent myIntent = new Intent(MyBookingsActivity.this, MyPropertiesActivity.class);
                        startActivity(myIntent);*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MyBookingsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
