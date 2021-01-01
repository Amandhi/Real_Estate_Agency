package com.example.myrealestateagency.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel;
import com.example.myrealestateagency.viewmodel.UpdatePropertyActivityViewModel;

final public class UpdatePropertyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView propertyID;

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

    private UpdatePropertyActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_update_property);


        //Then we retrieved the widget we will need to manipulate into the

        //propertyType = findViewById(R.id.propertyTpe_spinner);
        propertyID = findViewById(R.id.propertyID);
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


        //We configure the click on the save button
        findViewById(R.id.save).setOnClickListener(this);


        viewModel = new ViewModelProvider(this).get(UpdatePropertyActivityViewModel.class);

        restorePropertyDetails();
        observeEvent();

    }

    //The updation form is pre filled with the property's detail from "Details Page"
    public void restorePropertyDetails() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            propertyID.setText("Reference Number : " + extras.getString("UpdatedPropertyID"));
            propertyType.setText(extras.getString("TypeUpdate"));
            propertyPrice.setText(extras.getString("PriceUpdate"));
            propertySurface.setText(extras.getString("SurfaceUpdate"));
            propertyRooms.setText(extras.getString("RoomsUpdate"));
            propertyAddress.setText(extras.getString("AddressUpdate"));
            propertyDescription.setText(extras.getString("DescriptionUpdate"));
            restoreStatusSelection();
            restoreAgentInCharge();

        }
    }

    public void restoreAgentInCharge() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String agentInCharge = extras.getString("AgentInChargeUpdate");

            if (agentInCharge.equals("Noah")) {
                radioNoah.setChecked(true);
            }
            if (agentInCharge.equals("Emma")) {
                radioEmma.setChecked(true);
            }
            if (agentInCharge.equals("Sasha")) {
                radioSasha.setChecked(true);
            }
            if (agentInCharge.equals("Ivy")) {
                radioIvy.setChecked(true);
            }
        }
    }

    public void restoreStatusSelection() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String statusSelection = extras.getString("StatusUpdate");

            if (statusSelection.equals("Sold")) {
                radioSold.setChecked(true);
            }
            if (statusSelection.equals("Not Sold")) {
                radioNotSold.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int ID = Integer.parseInt(propertyID.getText().toString().substring(19));
        String typeUpdate = propertyType.getText().toString();
        Integer priceUpdate = Integer.parseInt(propertyPrice.getText().toString());
        Integer surfaceUpdate = Integer.parseInt(propertySurface.getText().toString());
        Integer roomsUpdate = Integer.parseInt(propertyRooms.getText().toString());
        String addressUpdate = propertyAddress.getText().toString();
        String descriptionUpdate = propertyDescription.getText().toString();
        //String agentInChargeUpdate = propertyAgentInCharge.getText().toString();
        String statusSelection = viewModel.checkPropertyStatusClick(radioSold, radioNotSold);
        String agentInChargeUpdate = viewModel.checkRadioButtonClick(radioNoah, radioEmma, radioSasha, radioIvy);

        //FAIRE UN CHECK UPDATE FORM ENTRIES
        //String updatedEntries = viewModel.checkFormEntries(typeUpdate, surfaceUpdate, roomsUpdate, addressUpdate);


        //IF GOOD
        saveUpdateDateTime();
        viewModel.saveChanges(ID, typeUpdate,priceUpdate,surfaceUpdate, roomsUpdate, addressUpdate, descriptionUpdate, statusSelection, radiogrpStatus,  agentInChargeUpdate, radiogrp);
        //viewModel.updateProperty(ID, typeUpdate, surfaceUpdate, roomsUpdate, addressUpdate, agentInChargeUpdate);
        DisplayUpdateNotification();
        //Intent intent = new Intent(this, PropertyListActivity.class);
        //startActivity(intent);


    }


    private void observeEvent() {
        viewModel.event.observe(this, new Observer<UpdatePropertyActivityViewModel.Event>() {
            @Override
            public void onChanged(UpdatePropertyActivityViewModel.Event event) {
                if (event == UpdatePropertyActivityViewModel.Event.DisplayTypeError) {
                    displayTypeError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayPriceError) {
                    displayPriceError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplaySurfaceError) {
                    displaySurfaceError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayRoomError) {
                    displayRoomError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayAddressError) {
                    displayAddressError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayDescriptionError) {
                    displayDescriptionError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayStatusError) {
                    displayStatusError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayAgentError) {
                    displayAgentError();
                }
                if (event == UpdatePropertyActivityViewModel.Event.DisplayBlankFieldError) {
                    displayBlankFieldError();
                }
            }
        });
    }

    private void displayTypeError() {
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
    }

    private void displayBlankFieldError() {
        Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
    }


    //Successful updation notification when an agent correctly updated a property
    public void DisplayUpdateNotification() {
        String canAdd = viewModel.checkFormEntries(propertyType.getText().toString(), Integer.parseInt(propertyPrice.getText().toString()), Integer.parseInt(propertySurface.getText().toString()), Integer.parseInt(propertyRooms.getText().toString()), propertyAddress.getEditableText().toString(), propertyDescription.getEditableText().toString());
        if (canAdd.equals("valid")) {
            final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            final String notificationChannelId = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? "MyChannel" : null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_HIGH);
                notificationManagerCompat.createNotificationChannel(notificationChannel);
            }

            final Intent intent = new Intent(UpdatePropertyActivity.this, PropertyListActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(UpdatePropertyActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            String propertyRef = propertyID.getText().toString().replaceAll("[^0-9]", "");
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(UpdatePropertyActivity.this, notificationChannelId);
            notificationBuilder.setContentTitle(getString(R.string.notification_title));
            notificationBuilder.setContentText(propertyType.getText().toString()+" n° "+ propertyRef+ " "+ getString(R.string.update_notification_message));
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_done_24);
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setChannelId(notificationChannelId);
            notificationBuilder.setContentIntent(pendingIntent);

            notificationManagerCompat.notify(1, notificationBuilder.build());
        }
    }

    //Save time and date of updation
    public void saveUpdateDateTime(){
        viewModel.saveUpdateDateTime();
    }
}
