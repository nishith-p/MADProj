package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.madprojectx.R;
import com.example.madprojectx.holder.MyPropertyViewHolder;
import com.example.madprojectx.interfaces.ItemClickListener;
import com.example.madprojectx.model.Property;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyPropertiesActivity extends AppCompatActivity {

    private ImageView imgView;
    private Query propRef;
    private DatabaseReference propKey;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String currentUserID, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_properties);

        /*imgView = findViewById(R.id.myprop_edit);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPropertiesActivity.this, EditPropertyActivity.class));
            }
        });*/

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        propRef = FirebaseDatabase.getInstance().getReference().child("Properties").orderByChild("uid").equalTo(currentUserID);

        recyclerView = findViewById(R.id.myprop_rev);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    //HANDLING THE OPTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myprop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_prop) {
            Intent add_prop = new Intent(MyPropertiesActivity.this, EditPropertyActivity.class);
            startActivity(add_prop);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Property> options = new FirebaseRecyclerOptions.Builder<Property>().setQuery(propRef, Property.class).build();

        FirebaseRecyclerAdapter<Property, MyPropertyViewHolder> adapter = new FirebaseRecyclerAdapter<Property, MyPropertyViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyPropertyViewHolder holder, int position, @NonNull final Property model) {
                        holder.propName.setText(model.gethName());
                        holder.propCity.setText(model.gethCity());

                        //NEW ADDITION
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent intent = new Intent(MyPropertiesActivity.this, UpdateActivity.class);
                                //intent.putExtra("Prop_Key", key);
                                intent.putExtra("Prop_Title", model.gethName());
                                intent.putExtra("Prop_Add1", model.gethAdd1());
                                intent.putExtra("Prop_Add2", model.gethAdd2());
                                intent.putExtra("Prop_City", model.gethCity());
                                intent.putExtra("Prop_Desc", model.getHostRule());
                                intent.putExtra("Prop_Type", model.gethRoomType());
                                intent.putExtra("Prop_Price", model.gethRoomPrice());
                                intent.putExtra("Prop_Gender", model.gethGender());
                                intent.putExtra("Prop_Op1", model.gethOpt1());
                                intent.putExtra("Prop_Op2", model.gethOpt2());
                                intent.putExtra("Prop_Op3", model.gethOpt3());
                                intent.putExtra("Prop_Img", model.gethImage());
                                intent.putExtra("Prop_Phone", model.gethPhone());
                                intent.putExtra("Prop_District", model.gethDistrict());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public MyPropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_my_prop,parent,false);
                        MyPropertyViewHolder holder = new MyPropertyViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
