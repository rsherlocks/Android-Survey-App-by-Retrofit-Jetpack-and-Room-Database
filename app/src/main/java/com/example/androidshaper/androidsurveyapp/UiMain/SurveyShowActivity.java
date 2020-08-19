package com.example.androidshaper.androidsurveyapp.UiMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidshaper.androidsurveyapp.AdapterView.MayAdapter;
import com.example.androidshaper.androidsurveyapp.R;
import com.example.androidshaper.androidsurveyapp.SurveyDataObject;
import com.example.androidshaper.androidsurveyapp.Room.SurveyViewModel;

import java.util.List;

public class SurveyShowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SurveyViewModel surveyViewModel;

  MayAdapter mayAdapter;

    List<SurveyDataObject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_show);
        recyclerView=findViewById(R.id.surveyRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadSurvey();
    }

    //here insert dataList to room database.

    private void loadSurvey() {
        surveyViewModel= ViewModelProviders.of(this).get(SurveyViewModel.class);
        surveyViewModel.getListLiveData().observe(this, new Observer<List<SurveyDataObject>>() {
            @Override
            public void onChanged(List<SurveyDataObject> surveyDataObjects) {
                list=surveyDataObjects;
                if(!list.isEmpty())
                {
                    mayAdapter=new MayAdapter(getApplicationContext(),list);
                    recyclerView.setAdapter(mayAdapter);

                }

            }
        });
    }
}