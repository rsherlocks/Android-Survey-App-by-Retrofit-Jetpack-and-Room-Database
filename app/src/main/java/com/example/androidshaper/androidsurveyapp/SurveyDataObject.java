package com.example.androidshaper.androidsurveyapp;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SurveyDataObject {

    @PrimaryKey(autoGenerate = true)
    int id;
    String multipleChoice;
    String checkbox;
    String number;
    String address;
    String dropDown;

    public SurveyDataObject(String multipleChoice, String checkbox, String number, String address, String dropDown) {
        this.multipleChoice = multipleChoice;
        this.checkbox = checkbox;
        this.number = number;
        this.address = address;
        this.dropDown = dropDown;
    }

    public String getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(String multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDropDown() {
        return dropDown;
    }

    public void setDropDown(String dropDown) {
        this.dropDown = dropDown;
    }
}
