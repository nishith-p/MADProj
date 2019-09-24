package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class UpdateActivity extends AppCompatActivity {

    private EditText etName, etPhone, etAdd1, etAdd2, etCity, etPrice, etRule;
    private ImageView etImage;
    private Button etUpdate, etDelete, etUpImg, etChImg;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    String Prop_Key, Prop_Title, Prop_Add1, Prop_Add2;
    String Prop_City, Prop_Desc, Prop_Type, Prop_Price;
    String Prop_Gender, Prop_Op1, Prop_Op2, Prop_Op3;
    String Prop_Img, Prop_Phone, Prop_District;

    ProgressDialog mProgressDialog;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //GET FROM THE INTENT
        Prop_Key = getIntent().getStringExtra("Prop_Key");
        Prop_Title = getIntent().getStringExtra("Prop_Title");
        Prop_Add1 = getIntent().getStringExtra("Prop_Add1");
        Prop_Add2 = getIntent().getStringExtra("Prop_Add2");
        Prop_City = getIntent().getStringExtra("Prop_City");
        Prop_Desc = getIntent().getStringExtra("Prop_Desc");
        Prop_Type = getIntent().getStringExtra("Prop_Type");
        Prop_Price = getIntent().getStringExtra("Prop_Price");
        Prop_Gender = getIntent().getStringExtra("Prop_Gender");
        Prop_Op1 = getIntent().getStringExtra("Prop_Op1");
        Prop_Op2 = getIntent().getStringExtra("Prop_Op2");
        Prop_Op3 = getIntent().getStringExtra("Prop_Op3");
        Prop_Img = getIntent().getStringExtra("Prop_Img");
        Prop_Phone = getIntent().getStringExtra("Prop_Phone");
        Prop_District = getIntent().getStringExtra("Prop_District");

        //FIND VIEWS
        etName = findViewById(R.id.aeFirstName);
        etPhone = findViewById(R.id.uphostPhone);
        etAdd1 = findViewById(R.id.uphostAdd1);
        etAdd2 = findViewById(R.id.uphostAdd2);
        etCity = findViewById(R.id.uphostCity);
        etPrice = findViewById(R.id.uphostRoomPrice);
        etRule = findViewById(R.id.uphostRule);
        etUpdate = findViewById(R.id.up_hostSubmit);
        etDelete = findViewById(R.id.up_hostDel);
        etUpImg = findViewById(R.id.upHostImage);
        etChImg = findViewById(R.id.upHostChoose);
        etImage = findViewById(R.id.uppreview_img);

        //SET FIELDS
        etName.setText(Prop_Title);
        etPhone.setText(Prop_Phone);
        etAdd1.setText(Prop_Add1);
        etAdd2.setText(Prop_Add2);
        etCity.setText(Prop_City);
        etPrice.setText(Prop_Price);
        etRule.setText(Prop_Desc);
        Picasso.get().load(Prop_Img).into(etImage);

        //CHOOSE IMAGE BUTTON
        etChImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openFileChooser();
            }
        });

        //UPLOAD IMAGE BUTTON
        etUpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginUpdate();
            }
        });

        //UPDATE BUTTON
        etUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase(Prop_Img);
            }
        });

        //DELETE BUTTON
        etDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEntry(Prop_Title);
            }
        });
    }

    //OPEN GALLERY
    private void openFileChooser(){
        Intent imgIntent = new Intent();
        imgIntent.setType("image/*");
        imgIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imgIntent, PICK_IMAGE_REQUEST);
    }

    //BEGIN UPDATE
    private void beginUpdate(){
        /*mProgressDialog.setMessage("Updating..");
        mProgressDialog.show();*/
        deleteOldImage();
    }

    //DELETE OLD IMAGE
    private void deleteOldImage(){
        mStorageReference = getInstance().getReferenceFromUrl(Prop_Img);
        mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateActivity.this, "Previous image deleted.", Toast.LENGTH_SHORT).show();
                uploadNewImage(); 
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressDialog.dismiss();
            }
        });
    }

    private void deleteOldImage2(){
        mStorageReference = getInstance().getReferenceFromUrl(Prop_Img);
        mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateActivity.this, "Image data deleted.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressDialog.dismiss();
            }
        });
    }

    //IMAGE VERIFICATION
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(etImage);
        }
    }

    //UPLOAD NEW IMAGE
    private void uploadNewImage() {
        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calender.getTime());

        String productKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = mStorageReference.child(mImageUri.getLastPathSegment() + productKey);
        final UploadTask uploadTask = filePath.putFile(mImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(UpdateActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UpdateActivity.this, "Uploading image data.", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        Prop_Img = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Prop_Img = task.getResult().toString();
                            Toast.makeText(UpdateActivity.this, "Image has been saved to database.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //UPDATE DATABASE
    private void updateDatabase(final String s){
        final String title = etName.getText().toString().trim();
        final String add1 = etAdd1.getText().toString().trim();
        final String add2 = etAdd2.getText().toString().trim();
        final String city = etCity.getText().toString().trim();
        final String price = etPrice.getText().toString().trim();
        final String phone = etPhone.getText().toString().trim();
        final String rules = etRule.getText().toString().trim();

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference("Properties");

        Query query = mRef.orderByChild("hName").equalTo(Prop_Title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().child("hName").setValue(title);
                    ds.getRef().child("hAdd1").setValue(add1);
                    ds.getRef().child("hAdd2").setValue(add2);
                    ds.getRef().child("hCity").setValue(city);
                    ds.getRef().child("hRoomPrice").setValue(price);
                    ds.getRef().child("hPhone").setValue(phone);
                    ds.getRef().child("hostRule").setValue(rules);
                    ds.getRef().child("hImage").setValue(s);
                }
                //mProgressDialog.dismiss();
                Toast.makeText(UpdateActivity.this, "Database updated.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateActivity.this, MyPropertiesActivity.class));
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void deleteEntry(final String currentTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mFirebaseDatabase.getReference("Properties");

                Query mQuery = mRef.orderByChild("hName").equalTo(currentTitle);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(UpdateActivity.this, "Entry deleted successfully.",Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(UpdateActivity.this, MyPropertiesActivity.class);
                        startActivity(myIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UpdateActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                deleteOldImage2();
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
