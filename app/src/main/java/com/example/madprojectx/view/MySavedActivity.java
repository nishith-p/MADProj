package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.holder.MyPropertyViewHolder;
import com.example.madprojectx.holder.ResViewHolder;
import com.example.madprojectx.model.Book;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MySavedActivity extends AppCompatActivity {

    private Query propRef;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String currentUserID, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saved);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        propRef = FirebaseDatabase.getInstance().getReference().child("Bookings").orderByChild("bookOwnerID").equalTo(currentUserID);

        recyclerView = findViewById(R.id.res_recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Book> options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(propRef, Book.class).build();

        FirebaseRecyclerAdapter<Book, ResViewHolder> adapter = new FirebaseRecyclerAdapter<Book, ResViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ResViewHolder holder, int position, @NonNull final Book model) {
                holder.res_hostel.setText(model.getBookHostName());
                holder.res_book_id.setText("Booking ID: " + model.getBookBookerID());
                holder.res_name.setText("Name: " + model.getBookFirstName() + " " + model.getBookLastName());
                holder.res_phone.setText("Phone: " + model.getBookPhone());
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
            public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_res, parent, false);
                ResViewHolder holder = new ResViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteEntry(final String currentTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(MySavedActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to cancel this booking?");
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
                        Toast.makeText(MySavedActivity.this, "Entry deleted successfully.",Toast.LENGTH_SHORT).show();
                        /*Intent myIntent = new Intent(MyBookingsActivity.this, MyPropertiesActivity.class);
                        startActivity(myIntent);*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MySavedActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
