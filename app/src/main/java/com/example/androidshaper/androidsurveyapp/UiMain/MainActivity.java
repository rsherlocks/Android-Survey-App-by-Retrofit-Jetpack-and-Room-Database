package com.example.androidshaper.androidsurveyapp.UiMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.androidshaper.androidsurveyapp.R;
import com.example.androidshaper.androidsurveyapp.RetrofitModel.ObjectData;
import com.example.androidshaper.androidsurveyapp.RetrofitModel.ObjectDataList;
import com.example.androidshaper.androidsurveyapp.RetrofitService.OurRetrofitClient;
import com.example.androidshaper.androidsurveyapp.RetrofitService.RetrofitInstance;
import com.example.androidshaper.androidsurveyapp.SurveyDataObject;
import com.example.androidshaper.androidsurveyapp.Room.SurveyViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<ObjectData> objectDataList=new ArrayList<>();
    private ArrayList<String> optionDropdown=new ArrayList<>();
    private ArrayList<String> optionCheckBox=new ArrayList<>();
    private ArrayList<String> optionMultipleChoice=new ArrayList<>();
    public OurRetrofitClient ourRetrofitClient;

    SurveyViewModel surveyViewModel;

    ArrayAdapter arrayAdapterMultipleChoice,arrayAdapterChecked,arrayAdapterSpinner;

    ProgressDialog progressDialog;

    ContentLoadingProgressBar contentLoadingProgressBar;



    ViewFlipper viewFlipper;
    Button buttonBack,buttonNext,buttonSubmit;
    ListView listViewMultipleChoice;
    ListView listViewChecked;
    Spinner spinner;


    static String name;
    TextView textViewQuestionFlipperOne,textViewQuestionFlipperTwo,textViewQuestionFlipperThree,textViewQuestionFlipperFour,textViewQuestionFlipperFive;
    TextView textViewPage;

    EditText editTextNumber,editTextAddress;

    String numberForm,addressForm,multipleChoiceForm,checkboxForm,dropDownForm=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper=findViewById(R.id.viewFlipperSurvey);
        listViewMultipleChoice=findViewById(R.id.flipperOneListView);
        listViewChecked=findViewById(R.id.flipperTwoListView);
        spinner=findViewById(R.id.flipperFourSpinner);
        textViewQuestionFlipperOne=findViewById(R.id.flipperOneTextView);
        textViewQuestionFlipperTwo=findViewById(R.id.flipperTwoTextView);
        textViewQuestionFlipperThree=findViewById(R.id.flipperThreeTextView);
        textViewQuestionFlipperFour=findViewById(R.id.flipperFourTextView);
        textViewQuestionFlipperFive=findViewById(R.id.flipperFiveTextView);
        textViewPage=findViewById(R.id.textViewPage);

        editTextNumber=findViewById(R.id.flipperThreeEditText);
        editTextAddress=findViewById(R.id.flipperFiveEditText);



        contentLoadingProgressBar=findViewById(R.id.loadingProgressBar);
        contentLoadingProgressBar.setProgress(viewFlipper.getDisplayedChild()+1);









        buttonBack=findViewById(R.id.buttonBack);
        buttonNext=findViewById(R.id.buttonNext);
        buttonSubmit=findViewById(R.id.flipperSixButton);
        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loooding...");
        buttonNext.setVisibility(View.VISIBLE);





        fetchDataFromServer();



    }

    //here i make arrayAdapter and set adapter in listView and spinner
    private void surveyFlipping() {


        Log.d("Flipping", "surveyFlipping: "+String.valueOf(optionMultipleChoice.size()));


        arrayAdapterMultipleChoice=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,optionMultipleChoice);
       listViewMultipleChoice.setAdapter(arrayAdapterMultipleChoice);
       listViewMultipleChoice.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

       listViewMultipleChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               multipleChoiceForm=adapterView.getItemAtPosition(i).toString();

           }
       });

        arrayAdapterChecked=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,optionCheckBox);
        listViewChecked.setAdapter(arrayAdapterChecked);
        listViewChecked.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewChecked.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkboxForm=adapterView.getItemAtPosition(i).toString();
            }
        });

        arrayAdapterSpinner=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,optionDropdown);
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownForm=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dropDownForm=null;

            }
        });

        numberForm=editTextNumber.getText().toString();
        addressForm=editTextAddress.getText().toString();


        progressDialog.dismiss();


    }

    //Here I fetching all data from  Restful api and convert option string to arrayList
    private void fetchDataFromServer() {
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        progressDialog.show();
        ourRetrofitClient= RetrofitInstance.getService();
        Call<JsonArray> call=ourRetrofitClient.getAllQuestion(timestamp);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful())

                {
                    Log.d("Response", "onResponse: Successe");
                    String objectString=response.body().toString();

                    Type listType= new TypeToken<List<ObjectData>>(){}.getType();
                    objectDataList=getTeamListFromJson(objectString,listType);
                    for (ObjectData objectData:objectDataList)
                    {
                        Log.d("Question", "onResponse: "+objectData.getQuestion());

                        String typeObject=objectData.getType();
                        String question=objectData.getQuestion();

                        Log.d("Type", typeObject);
                        String option=objectData.getOptions();
                        ObjectDataList objectDataList=  new  ObjectDataList(option);


                        if (typeObject.equals("multiple choice"))
                        {
                            textViewQuestionFlipperOne.setText(question);



                            optionMultipleChoice=objectDataList.getOptionArrayList();

                        }
                        else if (typeObject.equals("Checkbox"))
                        {
                            textViewQuestionFlipperTwo.setText(question);

                            optionCheckBox=objectDataList.getOptionArrayList();

                        }
                        else if (typeObject.equals("number"))
                        {
                            textViewQuestionFlipperThree.setText(question);

                        }
                        else if (typeObject.equals("dropdown"))
                        {
                            textViewQuestionFlipperFour.setText(question);

                            optionDropdown=objectDataList.getOptionArrayList();

                        }
                        else if (typeObject.equals("text"))
                        {
                            textViewQuestionFlipperFive.setText(question);

                        }



                    }
                    Log.d("Multiple Choice", String.valueOf(optionMultipleChoice.size()));
                    Log.d("Checkbox", String.valueOf(optionCheckBox.size()));
                    Log.d("Dropdown", String.valueOf(optionDropdown.size()));

                    surveyFlipping();


                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Error", "onFailure: message"+t.getMessage());
                progressDialog.dismiss();

            }
        });

    }

    public static <T> List<T> getTeamListFromJson(String jsonString, Type type) {
        if (!isValid(jsonString)) {
            return null;
        }
        return new Gson().fromJson(jsonString, type);
    }

    public static boolean isValid(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException jse) {
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.buttonNext)
        {

            GoNextFlipper();
        }
        else if (view.getId()==R.id.buttonBack)
        {

            GoBackFlipper();
        }
        else if(view.getId()==R.id.flipperSixButton)
        {
            buttonNext.setVisibility(View.VISIBLE);

            insertDataToRoom();

            allReset();
        }

    }

    //here reset all data and components after inserting data to room database cause we again submit survey form

    private void allReset() {
        listViewMultipleChoice.clearChoices();
        listViewChecked.clearChoices();
        editTextNumber.setText("");
        editTextAddress.setText("");

        numberForm=null;
        addressForm=null;
        multipleChoiceForm=null;
        checkboxForm=null;

    }

    //here is insert data part.

    private void insertDataToRoom() {
        numberForm=editTextNumber.getText().toString();
        addressForm=editTextAddress.getText().toString();
        surveyViewModel= ViewModelProviders.of(this).get(SurveyViewModel.class);
        SurveyDataObject surveyDataObject=new SurveyDataObject(multipleChoiceForm,checkboxForm,numberForm,addressForm,dropDownForm);

        surveyViewModel.insertSurveyData(surveyDataObject);


        Intent intent=new Intent(this,SurveyShowActivity.class);
        startActivity(intent);




    }

    @Override
    protected void onStart() {
        super.onStart();
        viewFlipper.setDisplayedChild(0);
        buttonNext.setVisibility(View.VISIBLE);
        contentLoadingProgressBar.setProgress(viewFlipper.getDisplayedChild()+1);
        textViewPage.setText("Page 1");

    }

    //here go  previous displayChild

    private void GoBackFlipper() {
        buttonNext.setVisibility(View.VISIBLE);
        if (viewFlipper.getDisplayedChild()>0)
        {
            textViewPage.setText("Page "+String.valueOf(viewFlipper.getDisplayedChild()));
            if (viewFlipper.getDisplayedChild() >1) {
                contentLoadingProgressBar.setProgress(viewFlipper.getDisplayedChild());

            }
            else {
                contentLoadingProgressBar.setProgress(1);

            }
            viewFlipper.showPrevious();

        }
    }

    //here go next displayChild and checking form validation.

    private void GoNextFlipper() {

        contentLoadingProgressBar.setProgress(viewFlipper.getDisplayedChild()+2);


        if (viewFlipper.getDisplayedChild()<viewFlipper.getChildCount()-1)
        {


            addressForm=editTextAddress.getText().toString();

            if(viewFlipper.getDisplayedChild()==0 && multipleChoiceForm!=null)
            {




                    textViewPage.setText("Page "+String.valueOf(viewFlipper.getDisplayedChild()+2));


                    viewFlipper.showNext();


            }
            else if(viewFlipper.getDisplayedChild()==1 && checkboxForm!=null)
            {



                textViewPage.setText("Page "+String.valueOf(viewFlipper.getDisplayedChild()+2));


                viewFlipper.showNext();

            }
            else if(viewFlipper.getDisplayedChild()==2)
            {


                textViewPage.setText("Page "+String.valueOf(viewFlipper.getDisplayedChild()+2));


                viewFlipper.showNext();

            }
            else if(viewFlipper.getDisplayedChild()==3 && dropDownForm!=null)
            {



                textViewPage.setText("Page "+String.valueOf(viewFlipper.getDisplayedChild()+2));


                viewFlipper.showNext();

            }
            else if(viewFlipper.getDisplayedChild()==4 && !addressForm.isEmpty())
            {


                textViewPage.setText("Page "+String.valueOf(viewFlipper.getDisplayedChild()+2));


                viewFlipper.showNext();
                buttonNext.setVisibility(View.INVISIBLE);

            }


            else{


                Toast.makeText(getApplicationContext(),"Please at first fill the form",Toast.LENGTH_SHORT).show();

            }

        }
        else {
            Toast.makeText(getApplicationContext(),"Please press submit button",Toast.LENGTH_SHORT).show();

        }
    }
}