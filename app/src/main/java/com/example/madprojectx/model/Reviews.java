package com.example.madprojectx.model;

public class Reviews {
    public String reviewGiverID;
    public String reviewPropID;
    public String reviewPropName;
    public String reviewWords;

    public String getReviewWords() {
        return reviewWords;
    }

    public void setReviewWords(String reviewWords) {
        this.reviewWords = reviewWords;
    }

    public Reviews() {
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


