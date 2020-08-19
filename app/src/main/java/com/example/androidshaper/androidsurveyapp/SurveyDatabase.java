package com.example.androidshaper.androidsurveyapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = {SurveyDataObject.class},version = 1,exportSchema = false)
public abstract class SurveyDatabase extends RoomDatabase {
   public abstract SurveyDataObjectDao surveyDataObjectDao();
    private static volatile SurveyDatabase INSTANCE;
    public static SurveyDatabase getDatabase(final Context context)
    {
        if (INSTANCE==null)
        {
            INSTANCE=Room.databaseBuilder(context.getApplicationContext(),SurveyDatabase.class,"survey_database")
                    .fallbackToDestructiveMigration()
                    .build();




        }
        return INSTANCE;
    }

}
