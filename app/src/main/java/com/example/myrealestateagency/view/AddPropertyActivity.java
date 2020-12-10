package com.example.myrealestateagency.view;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.adapter.AgentsAdapter;
import com.example.myrealestateagency.adapter.PropertiesAdapter;
import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.bo.PropertyType;
import com.example.myrealestateagency.repository.PropertyTypeRepository;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel.Event;

import java.util.List;

final public class AddPropertyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AddPropertyActivity.class.getSimpleName();

    //private Integer propertyType;


    private Spinner propertyType;
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

        //propertyType = findViewById(R.id.propertyTpe_spinner);
        propertySurface = findViewById(R.id.surface);
        propertyRooms = findViewById(R.id.rooms);
        propertyAddress = findViewById(R.id.address);
        radiogrp = findViewById(R.id.radio_grp);
        radioHouse = findViewById(R.id.radio_house);
        radioAprmt = findViewById(R.id.radio_apt);
        radioOffice = findViewById(R.id.radio_office);

        //fill the spinner in add property page with property types
        /*List<PropertyType> listTypes = PropertyTypeRepository.getInstance(this).getType();
        if(listTypes != null) {

            ArrayAdapter<PropertyType> adapter = new ArrayAdapter<PropertyType>(this, R.layout.support_simple_spinner_dropdown_item, listTypes);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            propertyType.setAdapter(adapter);
        }*/


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
        final int pSurface = Integer.parseInt(propertySurface.getText().toString());
        final int pRooms = Integer.parseInt(propertyRooms.getText().toString());
        final String pAddress = propertyAddress.getEditableText().toString();
        //final int pType = viewModel.CheckSpinnerSelection(propertyType);
        final int pType = viewModel.checkRadioButtonClick(radioHouse,radioAprmt,radioOffice); // We retrieve here the number (TypeID) associated to the property type chosen by the agent when adding a property

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
