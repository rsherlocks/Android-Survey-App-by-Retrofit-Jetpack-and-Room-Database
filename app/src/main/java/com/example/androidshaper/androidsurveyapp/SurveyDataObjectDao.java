package com.example.androidshaper.androidsurveyapp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SurveyDataObjectDao {


    @Insert
    public void surveyDataObjectInsert(SurveyDataObject surveyDataObject);

    @Query("select * From SurveyDataObject")
    LiveData<List<SurveyDataObject>> getAllData();
}
