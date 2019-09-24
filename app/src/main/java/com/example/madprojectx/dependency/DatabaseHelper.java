package com.example.madprojectx.dependency;

import com.example.madprojectx.model.Property;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceProp;
    private List<Property> properties = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Property> properties, List<String> keys);
        void DaraIsInserted();
        void DataIsUpdated();
        void DataisDeleted();
    }

    public DatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceProp = mDatabase.getReference("Properties");
    }

    public void updateProp(String key, Property property, final DataStatus dataStatus){
        mReferenceProp.child(key).setValue(property)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deleteProp(String key, final DataStatus dataStatus){
        mReferenceProp.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataisDeleted();
                    }
                });
    }
}
