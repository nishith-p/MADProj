package com.example.madprojectx.model;

public class Reviews {
    public String reviewGiverID;
    public String reviewPropID;
    public String reviewPropName;
    public String reviewWords;
    public String reviewFname;
    public String reviewLname;

    public Reviews() {
    }

    public String getReviewFname() {
        return reviewFname;
    }

    public void setReviewFname(String reviewFname) {
        this.reviewFname = reviewFname;
    }

    public String getReviewLname() {
        return reviewLname;
    }

    public void setReviewLname(String reviewLname) {
        this.reviewLname = reviewLname;
    }

    public String getReviewWords() {
        return reviewWords;
    }

    public void setReviewWords(String reviewWords) {
        this.reviewWords = reviewWords;
    }

    public String getReviewPropName() {
        return reviewPropName;
    }

    public void setReviewPropName(String reviewPropName) {
        this.reviewPropName = reviewPropName;
    }

    public String getReviewGiverID() {
        return reviewGiverID;
    }

    public void setReviewGiverID(String reviewGiverID) {
        this.reviewGiverID = reviewGiverID;
    }

    public String getReviewPropID() {
        return reviewPropID;
    }

    public void setReviewPropID(String reviewPropID) {
        this.reviewPropID = reviewPropID;
    }
}


