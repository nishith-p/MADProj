package com.example.madprojectx.model;

public class Upload {
    private String mImageUrl;

    public Upload() {
        //empty constructor
    }

    public Upload(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
