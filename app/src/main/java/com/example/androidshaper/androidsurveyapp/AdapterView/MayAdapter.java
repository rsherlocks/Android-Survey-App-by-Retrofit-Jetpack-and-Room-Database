package com.example.androidshaper.androidsurveyapp.AdapterView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidshaper.androidsurveyapp.R;
import com.example.androidshaper.androidsurveyapp.SurveyDataObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MayAdapter extends RecyclerView.Adapter<MyAdapterView> {
   public Context context;
    List<SurveyDataObject> surveyDataObjectList;

    public MayAdapter(Context context, List<SurveyDataObject> surveyDataObjectList) {
        this.context = context;
        this.surveyDataObjectList = surveyDataObjectList;
    }

    @NonNull
    @Override
    public MyAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_layout,parent,false);

        return new MyAdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterView holder, int position) {

        SurveyDataObject surveyDataObject=surveyDataObjectList.get(position);
        if (!surveyDataObjectList.isEmpty())
        {
           holder.textViewMultipleChoice.setText("Multiple Choice: "+surveyDataObject.getMultipleChoice());
           holder.textViewCheckBox.setText("Checkbox: "+surveyDataObject.getCheckbox());
           holder.textViewDropDown.setText("Dropdown: "+surveyDataObject.getDropDown());
           holder.textViewAddress.setText("Address: "+surveyDataObject.getAddress());
           holder.textViewNumber.setText("Number: "+surveyDataObject.getNumber());
        }

    }

    @Override
    public int getItemCount() {
        return surveyDataObjectList.size();
    }
}
