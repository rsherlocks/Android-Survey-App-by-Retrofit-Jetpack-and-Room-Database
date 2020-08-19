package com.example.androidshaper.androidsurveyapp.Room;

import android.app.Application;
import android.os.AsyncTask;

import com.example.androidshaper.androidsurveyapp.SurveyDataObject;
import com.example.androidshaper.androidsurveyapp.SurveyDataObjectDao;
import com.example.androidshaper.androidsurveyapp.SurveyDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {

    public SurveyDataObjectDao surveyDataObjectDao;
    LiveData<List<SurveyDataObject>> allSurveyList;

    Repository(Application application)
    {
        SurveyDatabase surveyDatabase=SurveyDatabase.getDatabase(application);
        surveyDataObjectDao=surveyDatabase.surveyDataObjectDao();
        allSurveyList=surveyDataObjectDao.getAllData();

    }

    public LiveData<List<SurveyDataObject>> getAllSurveyList() {

        return allSurveyList;
    }
    public void insertSurveyData(SurveyDataObject surveyDataObject)
    {
        new InsertTask(surveyDataObjectDao).execute(surveyDataObject);


    }
    public class InsertTask extends AsyncTask<SurveyDataObject,Void,Void>
    {

        private SurveyDataObjectDao surveyDataObjectDao;

        public InsertTask(SurveyDataObjectDao surveyDataObjectDao) {
            this.surveyDataObjectDao = surveyDataObjectDao;
        }

        @Override
        protected Void doInBackground(SurveyDataObject... surveyDataObjects) {
            surveyDataObjectDao.surveyDataObjectInsert(surveyDataObjects[0]);
            return null;
        }
    }
}
