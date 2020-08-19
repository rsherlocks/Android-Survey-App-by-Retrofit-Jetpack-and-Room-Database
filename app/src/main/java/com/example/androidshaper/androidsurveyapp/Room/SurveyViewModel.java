package com.example.androidshaper.androidsurveyapp.Room;

import android.app.Application;

import com.example.androidshaper.androidsurveyapp.SurveyDataObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SurveyViewModel extends AndroidViewModel {

    public Repository repository;
    LiveData<List<SurveyDataObject>> listLiveData;
    public SurveyViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        this.listLiveData=repository.allSurveyList;
    }

    public LiveData<List<SurveyDataObject>> getListLiveData() {
        return listLiveData;
    }
    public void insertSurveyData(SurveyDataObject surveyDataObject)
    {
        repository.insertSurveyData(surveyDataObject);
    }
}
