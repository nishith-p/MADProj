package com.example.madprojectx.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.HashMap;


public class EditPropertyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String pid, uid, gender, text, text2;
    private Button nSendData;
    private EditText hostName, hostPhone, hostAdd1, hostAdd2, hostCity, hostRoomPrice, hostRule;
    private DatabaseReference propertyRef;
    private RadioGroup hostGender;
    private RadioButton hostGenderOpt;
    private CheckBox hostOpt1, hostOpt2, hostOpt3, hostOpt4;
    Property prop;

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

        nSendData = findViewById(R.id.hostSubmit);

        hostName = findViewById(R.id.hostName);
        hostPhone = findViewById(R.id.hostPhone);
        hostAdd1 = findViewById(R.id.hostAdd1);
        hostAdd2 = findViewById(R.id.hostAdd2);
        hostCity = findViewById(R.id.hostCity);

        Spinner hostDistrictSpinner = findViewById(R.id.hostDistrict);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.districts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostDistrictSpinner.setAdapter(adapter);
        hostDistrictSpinner.setOnItemSelectedListener(this);

        Spinner hostRoomTypeSpinner = findViewById(R.id.hostRoomType);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.roomtypes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostRoomTypeSpinner.setAdapter(adapter2);
        hostRoomTypeSpinner.setOnItemSelectedListener(this);

        hostOpt1 = findViewById(R.id.hostOpt1);
        hostOpt2 = findViewById(R.id.hostOpt2);
        hostOpt3 = findViewById(R.id.hostOpt3);
        hostOpt4 = findViewById(R.id.hostOpt4);
        hostRoomPrice = findViewById(R.id.hostRoomPrice);

        hostRule = findViewById(R.id.hostRule);

        mButtonChooseImage = findViewById(R.id.hostUpImg);
        mButtonUpload = findViewById(R.id.hostUp2);
        mImageView = findViewById(R.id.preview_img);
        mProgressBar = findViewById(R.id.progressBarImg);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        hostGender = findViewById(R.id.radioGender);
        prop = new Property();

        hostGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                hostGenderOpt = hostGender.findViewById(i);

                switch (i){
                    case R.id.radioMale:
                        gender = hostGenderOpt.getText().toString();

                        break;
                    case R.id.radioFemale:
                        gender = hostGenderOpt.getText().toString();
                        break;
                    default:
                }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.hostDistrict:
                text = parent.getItemAtPosition(i).toString();
                break;
            case R.id.hostRoomType:
                text2 = parent.getItemAtPosition(i).toString();
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
