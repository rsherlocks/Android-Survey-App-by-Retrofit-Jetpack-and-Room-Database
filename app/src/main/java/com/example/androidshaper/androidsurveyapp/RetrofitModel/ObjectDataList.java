package com.example.androidshaper.androidsurveyapp.RetrofitModel;

import java.util.ArrayList;
import java.util.List;

public class ObjectDataList {
   public String optionTotalString;
    ArrayList<String> optionArrayList=new ArrayList<>();
    String[] optionArray=null;

    public ArrayList<String> getOptionArrayList() {

        String[] optionArray=optionTotalString.split(",");
        for(int i=0;i<optionArray.length;i++)
        {
            optionArrayList.add(optionArray[i]);
        }

        return optionArrayList;
    }

    public ObjectDataList(String optionTotalString) {
        this.optionTotalString = optionTotalString;
    }
}
