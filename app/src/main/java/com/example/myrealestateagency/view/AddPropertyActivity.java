package com.example.myrealestateagency.view;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.PropertyTypeRepository;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

final public class AddPropertyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AddPropertyActivity.class.getSimpleName();



    private TextView propertyType;

    private EditText propertyPrice;

    //private Spinner propertyType;
    private EditText propertySurface;

    private EditText propertyRooms;

    private EditText propertyAddress;

    private EditText propertyDescription;

    private RadioGroup radiogrpStatus;

    private RadioButton radioSold;

    private RadioButton radioNotSold;

    private RadioGroup radiogrp;

    private RadioButton radioNoah;

    private RadioButton radioEmma;

    private RadioButton radioSasha;

    private RadioButton radioIvy;

    private AddPropertyActivityViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_add_property);


        propertyType = findViewById(R.id.type);
        propertyPrice = findViewById(R.id.price);
        propertySurface = findViewById(R.id.surface);
        propertyRooms = findViewById(R.id.rooms);
        propertyAddress = findViewById(R.id.address);
        propertyDescription = findViewById(R.id.description);
        radiogrpStatus = findViewById(R.id.radio_grp_status);
        radioSold = findViewById(R.id.radio_sold);
        radioNotSold = findViewById(R.id.radio_not_sold);
        radiogrp = findViewById(R.id.radio_grp);
        radioNoah = findViewById(R.id.radio_Noah);
        radioEmma = findViewById(R.id.radio_Emma);
        radioSasha = findViewById(R.id.radio_Sasha);
        radioIvy = findViewById(R.id.radio_Ivy);

        //fill the spinner in add property page with property types
        /*List<PropertyType> listTypes = PropertyTypeRepository.getInstance(this).getType();
        if(listTypes != null) {

            ArrayAdapter<PropertyType> adapter = new ArrayAdapter<PropertyType>(this, R.layout.support_simple_spinner_dropdown_item, listTypes);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            propertyType.setAdapter(adapter);
        }*/


        //We configure the click on the save button
        findViewById(R.id.save).setOnClickListener(this);



        viewModel = new ViewModelProvider(this).get(AddPropertyActivityViewModel.class);

        observeEvent();
        StorePropertyType();

    }






    private void observeEvent() {
        viewModel.event.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                if (event == Event.ResetForm) {
                    resetForm();
                }
                /*if (event == Event.DisplayTypeError) {
                    displayTypeError();
                }
                if (event == Event.DisplayPriceError) {
                    displayPriceError();
                }
                if (event == Event.DisplaySurfaceError) {
                    displaySurfaceError();
                }
                if (event == Event.DisplayRoomError) {
                    displayRoomError();
                }
                if (event == Event.DisplayAddressError) {
                    displayAddressError();
                }
                if (event == Event.DisplayDescriptionError) {
                    displayDescriptionError();
                }
                if (event == Event.DisplayStatusError) {
                    displayStatusError();
                }
                if (event == Event.DisplayAgentError) {
                    displayAgentError();
                }*/
                if (event == Event.DisplayBlankFieldError) {
                    displayBlankFieldError();
                }
            }
        });
    }

    /*private void displayTypeError() {
        Toast.makeText(this, R.string.enter_valid_type, Toast.LENGTH_SHORT).show();
    }

    private void displayPriceError() {
        Toast.makeText(this, R.string.enter_valid_price, Toast.LENGTH_SHORT).show();
    }

    private void displaySurfaceError() {
        Toast.makeText(this, R.string.enter_valid_surface, Toast.LENGTH_SHORT).show();
    }

    private void displayRoomError() {
        Toast.makeText(this, R.string.enter_valid_room, Toast.LENGTH_SHORT).show();
    }

    private void displayAddressError() {
        Toast.makeText(this, R.string.enter_valid_address, Toast.LENGTH_SHORT).show();
    }

    private void displayDescriptionError() {
        Toast.makeText(this, R.string.enter_valid_description, Toast.LENGTH_SHORT).show();
    }

    private void displayStatusError() {
        Toast.makeText(this, R.string.enter_valid_status, Toast.LENGTH_SHORT).show();
    }

    private void displayAgentError() {
        Toast.makeText(this, R.string.enter_valid_agent, Toast.LENGTH_SHORT).show();
    }*/

    private void displayBlankFieldError() {
        Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //we first retrieve user's entries

        final Double pPrice = viewModel.CheckDouble(propertyPrice.getText().toString());
        final Integer pSurface = viewModel.CheckInteger(propertySurface.getText().toString());
        final Integer pRooms = viewModel.CheckInteger(propertyRooms.getText().toString());

        final String pAddress = propertyAddress.getText().toString();
        final String pDescription = propertyDescription.getText().toString();
        //final int pType = viewModel.CheckSpinnerSelection(propertyType);
        //final Integer pType = viewModel.checkRadioButtonClick(radioHouse, radioAprmt, radioOffice); // We retrieve here the number (TypeID) associated to the property type chosen by the agent when adding a property
        final String pType = propertyType.getText().toString();
        //final String pAgent = propertyAgentInCharge.getEditableText().toString();
        final String pAgent = viewModel.checkRadioButtonClick(radioNoah, radioEmma, radioSasha, radioIvy);
        final String pStatus = viewModel.checkPropertyStatusClick(radioSold, radioNotSold);

        saveCreationDateTime();
        viewModel.saveProperty(pType,pPrice,pSurface, pRooms, pAddress,pDescription,pAgent,radiogrp,pStatus, radiogrpStatus);

        //onBackPressed();

        DisplayAddNotification();
    }

    private void resetForm() {

        propertyPrice.setText(null);
        propertySurface.setText(null);
        propertyRooms.setText(null);
        propertyAddress.setText(null);
        propertyDescription.setText(null);
        //propertyAgentInCharge.setText(null);
        radioSold.setChecked(false);
        radioNotSold.setChecked(false);
        radioNoah.setChecked(false);
        radioEmma.setChecked(false);
        radioSasha.setChecked(false);
        radioIvy.setChecked(false);
    }

    //Method to display notification when agent creates a property
    public void DisplayAddNotification(){
        String canAdd = viewModel.checkFormEntries(viewModel.CheckDouble(propertyPrice.getText().toString()), viewModel.CheckInteger(propertySurface.getText().toString()), viewModel.CheckInteger(propertyRooms.getText().toString()), propertyAddress.getEditableText().toString(), propertyDescription.getEditableText().toString(), radiogrp, radiogrpStatus);
        if(canAdd.equals("valid")){
            final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            final String notificationChannelId = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? "MyChannel" : null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                final NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_HIGH);
                notificationManagerCompat.createNotificationChannel(notificationChannel);
            }

            final Intent intent = new Intent(AddPropertyActivity.this, PropertyListActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(AddPropertyActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(AddPropertyActivity.this, notificationChannelId);
            notificationBuilder.setContentTitle(getString(R.string.notification_title));
            notificationBuilder.setContentText(propertyType.getText().toString()+ " "+getString(R.string.add_notification_message));
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_done_24);
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setChannelId(notificationChannelId);
            notificationBuilder.setContentIntent(pendingIntent);

            notificationManagerCompat.notify(1, notificationBuilder.build());
        }


    }

    public void StorePropertyType(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            propertyType.setText(extras.getString("SelectedType"));
        }

    }

    //Method to retrieve current date & time of a property's creation
    public void saveCreationDateTime(){
        viewModel.saveCreationDateTime();
    }









    /*public void onRadioButtonClicked(View view) {

        viewModel.checkRadioButtonClick(radioHouse, radioAprmt, radioOffice);
    }*/

}
