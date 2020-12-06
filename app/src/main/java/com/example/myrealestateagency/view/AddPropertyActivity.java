package com.example.myrealestateagency.view;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel.Event;

final public class AddPropertyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AddPropertyActivity.class.getSimpleName();

    private Integer propertyType;

    private EditText propertySurface;

    private EditText propertyRooms;

    private EditText propertyAddress;

   private RadioGroup radiogrp;

    private RadioButton radioHouse;

    private RadioButton radioAprmt;

    private RadioButton radioOffice;

    private AddPropertyActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_add_property);

        //Then we retrieved the widget we will need to manipulate into the

        propertySurface = findViewById(R.id.surface);
        propertyRooms = findViewById(R.id.rooms);
        propertyAddress = findViewById(R.id.address);
        radiogrp = findViewById(R.id.radio_grp);
        radioHouse = findViewById(R.id.radio_house);
        radioAprmt = findViewById(R.id.radio_apt);
        radioOffice = findViewById(R.id.radio_office);


        //We configure the click on the save button
        findViewById(R.id.save).setOnClickListener(this);
        //radiogrp.setOnClickListener(this);


        viewModel = new ViewModelProvider(this).get(AddPropertyActivityViewModel.class);

        observeEvent();
    }

    private void observeEvent() {
        viewModel.event.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                if (event == Event.ResetForm) {
                    resetForm();
                } else if (event == Event.DisplayError) {
                    displayError();
                }
            }
        });
    }

    private void displayError() {
        Toast.makeText(this, R.string.cannot_add_property, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //we first retrieve user's entries
        final Integer pSurface = Integer.parseInt(propertySurface.getText().toString());
        final Integer pRooms = Integer.parseInt(propertyRooms.getText().toString());
        final String pAddress = propertyAddress.getEditableText().toString();
        final Integer pType = viewModel.checkRadioButtonClick(radioHouse, radioAprmt, radioOffice);

        viewModel.saveProperty(pType,pSurface, pRooms, pAddress);
    }

    private void resetForm() {

        propertySurface.setText(null);
        propertyRooms.setText(null);
        propertyAddress.setText(null);
    }


    /*public void onRadioButtonClicked(View view) {

        viewModel.checkRadioButtonClick(radioHouse, radioAprmt, radioOffice);
    }*/

}
