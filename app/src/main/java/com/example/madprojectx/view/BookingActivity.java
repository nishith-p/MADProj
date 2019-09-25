package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.model.Book;
import com.example.madprojectx.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class BookingActivity extends AppCompatActivity {

    private TextView ab_HostName, ab_Location, ab_Gender, ab_Type, ab_Price;
    private EditText ab_Phone;
    private Button ab_Book;

    FirebaseUser firebaseUser;
    DatabaseReference dbRef;
    DatabaseReference dbRef2;

    String bFname, bLname;

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences("reviewData", MODE_PRIVATE);

        final String hostTitle = prefs.getString("revTitle", "");
        final String hostGender = prefs.getString("revGender", "");
        final String hostCity = prefs.getString("revCity", "");
        final String hostType = prefs.getString("revType", "");
        final String hostPrice = prefs.getString("revPrice", "");
        final String hostUID = prefs.getString("revOwner", "");

        book = new Book();

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

        ab_HostName = findViewById(R.id.abHostelName);
        ab_Location = findViewById(R.id.abLocation);
        ab_Gender = findViewById(R.id.abGender);
        ab_Type = findViewById(R.id.abType);
        ab_Price = findViewById(R.id.abPrice);
        ab_Phone = findViewById(R.id.abPhone);

        ab_HostName.setText(hostTitle);
        ab_Gender.setText(hostGender);
        ab_Location.setText(hostCity);
        ab_Type.setText(hostType);
        ab_Price.setText(hostPrice);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Bookings");

        ab_Book = findViewById(R.id.abBook);
        ab_Book.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                book.setBookBookerID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                book.setBookHostName(hostTitle);
                book.setBookOwnerID(hostUID);
                book.setBookPhone(ab_Phone.getText().toString().trim());
                book.setBookFirstName(bFname);
                book.setBookLastName(bLname);

                dbRef.push().setValue(book);

                Toast.makeText(BookingActivity.this, "Booking saved successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookingActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
