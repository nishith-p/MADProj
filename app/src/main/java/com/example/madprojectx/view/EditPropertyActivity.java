package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madprojectx.R;
import com.example.madprojectx.model.Property;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EditPropertyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "EditPropertyActivity";

    private String pid, uid, gender, text, text2, saveCurrentDate, saveCurrentTime, productKey, downloadImage;
    private Button nSendData;
    private EditText hostName, hostPhone, hostAdd1, hostAdd2, hostCity, hostRoomPrice, hostRule;

    private RadioGroup hostGender;
    private RadioButton hostGenderOpt;

    private CheckBox hostOpt1, hostOpt2, hostOpt3, hostOpt4;
    Property prop;

    private StorageReference propImageRef;
    private DatabaseReference propertyRef;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;


    private void clearControls(){
        hostName.setText("");
        hostPhone.setText("");
        hostAdd1.setText("");
        hostAdd2.setText("");
        hostCity.setText("");
        hostRoomPrice.setText("");
        hostRule.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //propertyImagesRef = FirebaseStorage.getInstance().getReference().child("Property Images");
        propertyRef = FirebaseDatabase.getInstance().getReference().child("Properties");
        propImageRef = FirebaseStorage.getInstance().getReference().child("Products");

        nSendData = findViewById(R.id.up_hostSubmit);

        hostName = findViewById(R.id.uphostName);
        hostPhone = findViewById(R.id.uphostPhone);
        hostAdd1 = findViewById(R.id.uphostAdd1);
        hostAdd2 = findViewById(R.id.uphostAdd2);
        hostCity = findViewById(R.id.uphostCity);

        Spinner hostDistrictSpinner = findViewById(R.id.uphostDistrict);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.districts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostDistrictSpinner.setAdapter(adapter);
        hostDistrictSpinner.setOnItemSelectedListener(this);

        Spinner hostRoomTypeSpinner = findViewById(R.id.uphostRoomType);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.roomtypes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostRoomTypeSpinner.setAdapter(adapter2);
        hostRoomTypeSpinner.setOnItemSelectedListener(this);

        hostOpt1 = findViewById(R.id.uphostOpt1);
        hostOpt2 = findViewById(R.id.uphostOpt2);
        hostOpt3 = findViewById(R.id.uphostOpt3);
        hostOpt4 = findViewById(R.id.uphostOpt4);
        hostRoomPrice = findViewById(R.id.uphostRoomPrice);

        hostRule = findViewById(R.id.uphostRule);

        mButtonChooseImage = findViewById(R.id.upHostChoose);
        mButtonUpload = findViewById(R.id.up_hostUp2);
        mImageView = findViewById(R.id.uppreview_img);
        mProgressBar = findViewById(R.id.progressBarImg);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        hostGender = findViewById(R.id.upradioGender);
        prop = new Property();

        hostGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                hostGenderOpt = hostGender.findViewById(i);

                switch (i){
                    case R.id.upradioMale:
                        gender = hostGenderOpt.getText().toString();

                        break;
                    case R.id.upradioFemale:
                        gender = hostGenderOpt.getText().toString();
                        break;
                    default:
                }
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });

        nSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prop.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                prop.sethName(hostName.getText().toString().trim());
                prop.sethAdd1(hostAdd1.getText().toString().trim());
                prop.sethAdd2(hostAdd2.getText().toString().trim());
                prop.sethCity(hostCity.getText().toString().trim());
                prop.sethDistrict(text);
                prop.sethRoomPrice(hostRoomPrice.getText().toString().trim());
                prop.sethRoomType(text2);
                prop.sethGender(gender);
                prop.sethPhone(hostPhone.getText().toString().trim());
                prop.setHostRule(hostRule.getText().toString().trim());
                prop.sethImage(downloadImage);

                if(hostOpt1.isChecked()) {
                    prop.sethOpt1("WiFi");
                }

                if(hostOpt2.isChecked()) {
                    prop.sethOpt2("AC");
                }

                if(hostOpt3.isChecked()) {
                    prop.sethOpt3("Laundry");
                }

                if(hostOpt4.isChecked()) {
                    prop.sethOpt4("Parking");
                }

                propertyRef.push().setValue(prop);

                Toast.makeText(EditPropertyActivity.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditPropertyActivity.this, MyPropertiesActivity.class);
                startActivity(i);
                clearControls();
            }
        });


    }

    private void openFileChooser(){
        Intent imgIntent = new Intent();
        imgIntent.setType("image/*");
        imgIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imgIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private void validateProductData()
    {
        if (mImageUri == null){
            Toast.makeText(this, "Product image needed.", Toast.LENGTH_SHORT).show();
        } else {
            storeImageInfo();
        }
    }

    private void storeImageInfo(){
        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calender.getTime());

        productKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = propImageRef.child(mImageUri.getLastPathSegment() + productKey);

        final UploadTask uploadTask = filePath.putFile(mImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(EditPropertyActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditPropertyActivity.this, "Image uploaded successfully.", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadImage = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            downloadImage = task.getResult().toString();

                            Toast.makeText(EditPropertyActivity.this, "Product image has been saved to database.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.uphostDistrict:
                text = parent.getItemAtPosition(i).toString();
                break;
            case R.id.uphostRoomType:
                text2 = parent.getItemAtPosition(i).toString();
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
