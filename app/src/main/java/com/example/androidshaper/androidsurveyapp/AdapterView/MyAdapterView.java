package com.example.androidshaper.androidsurveyapp.AdapterView;

import android.view.View;
import android.widget.TextView;

import com.example.androidshaper.androidsurveyapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyAdapterView extends RecyclerView.ViewHolder {

    TextView textViewMultipleChoice,textViewCheckBox,textViewDropDown,textViewNumber,textViewAddress;
    public MyAdapterView(@NonNull View itemView) {
        super(itemView);
        textViewMultipleChoice=itemView.findViewById(R.id.multipleChoice);
        textViewCheckBox=itemView.findViewById(R.id.checkbox);
        textViewDropDown=itemView.findViewById(R.id.dropDown);
        textViewNumber=itemView.findViewById(R.id.number);
        textViewAddress=itemView.findViewById(R.id.address);

    }
}
