package com.example.madprojectx.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.madprojectx.R;
import com.example.madprojectx.holder.PropertyViewHolder;
import com.example.madprojectx.model.Property;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private Button btn;

    private DatabaseReference proRef;
    private RecyclerView propReView;
    /*private ImageView imgView;
    private Button btn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;*/
    private static long back_pressed;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        proRef = FirebaseDatabase.getInstance().getReference().child("Properties");

        propReView = findViewById(R.id.propReView);
        propReView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        propReView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Property> options =
                new FirebaseRecyclerOptions.Builder<Property>().setQuery(proRef, Property.class).build();

        FirebaseRecyclerAdapter<Property, PropertyViewHolder> adapter = new FirebaseRecyclerAdapter<Property, PropertyViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull PropertyViewHolder holder, int position, @NonNull final Property model) {

                        holder.propName.setText(model.gethName());
                        holder.propLoc.setText(model.gethDistrict());
                        holder.propType.setText(model.gethRoomType());
                        holder.propRent.setText("Rs." + model.gethRoomPrice());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent propIntent = new Intent(MainActivity.this, PropertyActivity.class);
                                propIntent.putExtra("Prop_Title", model.gethName());
                                propIntent.putExtra("Prop_Add1", model.gethAdd1());
                                propIntent.putExtra("Prop_Add2", model.gethAdd2());
                                propIntent.putExtra("Prop_City", model.gethCity());
                                propIntent.putExtra("Prop_Desc", model.getHostRule());
                                propIntent.putExtra("Prop_Type", model.gethRoomType());
                                propIntent.putExtra("Prop_Price", model.gethRoomPrice());

                                startActivity(propIntent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_prop, parent, false);
                        PropertyViewHolder holder = new PropertyViewHolder(view);
                        return holder;
                    }
                };

        propReView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press BACK again to exit", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            Intent nav_book = new Intent(MainActivity.this, MyBookingsActivity.class);
            startActivity(nav_book);
        } else if (id == R.id.nav_prop) {
            Intent nav_prop = new Intent(MainActivity.this, MyPropertiesActivity.class);
            startActivity(nav_prop);
        } else if (id == R.id.nav_reviews) {
            Intent nav_reviews = new Intent(MainActivity.this, MyReviewsActivity.class);
            startActivity(nav_reviews);
        } else if (id == R.id.nav_save) {
            Intent nav_save = new Intent(MainActivity.this, MySavedActivity.class);
            startActivity(nav_save);
        } else if (id == R.id.nav_settings) {
            Intent nav_save = new Intent(MainActivity.this, EditUserActivity.class);
            startActivity(nav_save);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intToMain = new Intent(MainActivity.this, WelcomeActivity.class);
            intToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intToMain);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}