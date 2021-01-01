package com.example.myrealestateagency.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.retrofit.RetrofitBuilder;
import com.example.myrealestateagency.retrofit.RetrofitInterface;
import com.example.myrealestateagency.viewmodel.PropertyDetailActivityViewModel;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

final public class PropertyDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PROPERTY_EXTRA = "propertyExtra";

    public static final String AGENT_EXTRA = "agentExtra";

    //The tag used into this screen for the logs
    public static final String TAG = PropertyDetailActivity.class.getSimpleName();

    private ConstraintLayout propertyLayout;

    private ImageView propertyIcon;

    private TextView agentLoggedIn;

    private TextView propertyID;

    private TextView propertyType;

    private TextView propertyPrice;

    private TextView convertedPrice;

    private TextView propertySurface;

    private TextView propertyRooms;

    private TextView propertyAddress;

    private TextView propertyLatLong;

    private TextView propertyDescription;

    private TextView propertyStatus;

    private TextView agentInCharge;

    private TextView propertyCreationDate;

    private TextView propertyUpdateDate;

    private ImageView agentInChargeIcon;





    private PropertyDetailActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_property_detail);

        //Then we retrieved the widget we will need to manipulate into the
        propertyLayout = findViewById(R.id.propertyLayout);
        propertyIcon=findViewById(R.id.avatar);
        agentLoggedIn = findViewById(R.id.agentLoggedIn);
        propertyID = findViewById(R.id.propertyID);
        propertyType = findViewById(R.id.property_type);
        propertyPrice=findViewById(R.id.property_price);
        convertedPrice = findViewById(R.id.property_converted_price);
        propertySurface = findViewById(R.id.surface);
        propertyRooms = findViewById(R.id.rooms);
        propertyAddress = findViewById(R.id.address);
        propertyLatLong = findViewById(R.id.property_lat_long);
        propertyDescription = findViewById(R.id.property_description);
        propertyStatus = findViewById(R.id.property_status);
        agentInCharge = findViewById(R.id.agentInCharge);
        propertyCreationDate = findViewById(R.id.creation_date);
        propertyUpdateDate = findViewById(R.id.update_date);
        agentInChargeIcon = findViewById(R.id.agentInCharge_icon);


        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(PropertyDetailActivityViewModel.class);

        findViewById(R.id.btn_price_converter).setOnClickListener(this);
        findViewById(R.id.btn_simulator).setOnClickListener(this);


        observeProperty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //We add the "menu_property_management_functionalities" to the current activity
        getMenuInflater().inflate(R.menu.menu_property_management_functionalities, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //We handle the click on a menu item
        //If the agent wants to delete a property, the app first checks the identity of the agent.
        // If the agent trying to delete the property is the one in charge of it, then we display a deletion confirmation pop window
        //Otherwise we display an deletion error message, saying the property can only be deleted by its agent in charge.
        if (item.getItemId() == R.id.delete_property) {
            String agentLoggedInName = agentLoggedIn.getText().toString().substring(15);
            String agentInChargeName = agentInCharge.getText().toString().substring(18);
            String propertyToDelete = propertyType.getText().toString().substring(7);
            String IDPropertyToDelete = propertyID.getText().toString().substring(19);
            boolean deleteRequest = viewModel.deletePropertyRequest(agentLoggedInName, agentInChargeName);
            if (deleteRequest==true) {
                // Code for the delete alert dialog, when the agent logged in (also the one in charge of the property) tries to delete a property
                    AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Warning !")
                        .setMessage("Are you sure you want to delete " + propertyToDelete +" n° "+ IDPropertyToDelete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // if agent clicks on yes, the property will be deleted from the app's database
                                final Property property = (Property) getIntent().getSerializableExtra(PropertyDetailActivity.PROPERTY_EXTRA);
                                DeleteProperty(property);

                                //and finally display success delete notif
                                DisplayDeleteNotification();
                            }
                        })
                        .setNegativeButton("No", null) // Otherwise nothing happens
                        .show();
            }else{

                //display error pop up if the agent trying to delete is not the one in charge
                // Code for the delete alert dialog
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Ouups !")
                        .setMessage("Seems like you are not the agent in charge, cannot execute deletion")
                        .show();

            }



            //If agent wants to update a property, a new page opens up with an updation form already filled and that can be editable
        } if (item.getItemId() == R.id.update_property) {
            //viewModel.updateProperty();
            String ID = propertyID.getText().toString().substring(19);
            String typeUpdate = propertyType.getText().toString().substring(7);
            String priceUpdate = propertyPrice.getText().toString().replaceAll("[^0-9]", "");
            String surface = propertySurface.getText().toString().substring(10);
            String surfaceUpdate = surface.substring(0, surface.length() - 3);
            String roomsUpdate= propertyRooms.getText().toString().substring(18);
            String addressUpdate = propertyAddress.getText().toString().substring(10);
            String descriptionUpdate = propertyDescription.getText().toString().substring(14);
            String statusUpdate = propertyStatus.getText().toString().substring(9);
            String agentInChargeUpdate = agentInCharge.getText().toString().substring(18);

            final Intent intent = new Intent(this, UpdatePropertyActivity.class);
            intent.putExtra("UpdatedPropertyID", ID);
            intent.putExtra("TypeUpdate", typeUpdate);
            intent.putExtra("PriceUpdate", priceUpdate);
            intent.putExtra("SurfaceUpdate", surfaceUpdate);
            intent.putExtra("RoomsUpdate", roomsUpdate);
            intent.putExtra("AddressUpdate", addressUpdate);
            intent.putExtra("DescriptionUpdate", descriptionUpdate);
            intent.putExtra("StatusUpdate", statusUpdate);
            intent.putExtra("AgentInChargeUpdate", agentInChargeUpdate);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    //Display a property detail on "Detail Page" of the app, that opens up when an agent clicks on a property from the "Home Page"
    private void observeProperty() {
        viewModel.property.observe(this, new Observer<Property>() {
            @Override
            public void onChanged(Property property) {
                //Then we bind the User and the UI
                displayAgentProfileName();
                propertyID.setText("Reference Number : "+property.PropertyID);
                propertyType.setText("Type : "+property.PropertyType);
                propertyPrice.setText("Price : "+ property.Price+" €");
                propertySurface.setText("Surface : "+property.PropertySurface + " m2");
                propertyRooms.setText("Number of rooms : "+property.Number_of_Rooms);
                propertyAddress.setText("Address : "+property.Address);
                propertyDescription.setText("Description : "+property.Description);
                propertyStatus.setText("Status : "+property.PropertyStatus);
                agentInCharge.setText("Agent in charge : "+property.Agent_in_charge);
                setPropertyIconLayout(property.PropertyType);
                setAgentInChargeIcon(property.Agent_in_charge);

                String address = propertyAddress.getText().toString().substring(10);
                ArrayList<String> results = viewModel.getLatitudeLongitude(address, getApplicationContext());
                propertyLatLong.setText("Latitude : " + results.get(0) + "\nLongitude : " +results.get(1));

               propertyCreationDate.setText("Created on : "+ AppPreferences.getCreationDateTime(getApplication()));
               propertyUpdateDate.setText("Updated on : "+ getUpdateDate());
               //AppPreferences.removeCreationInfoFromPreferences(getApplication());




            }
        });
    }

    //Code for deletion confirmation notification
    public void DisplayDeleteNotification() {
            final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            final String notificationChannelId = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? "MyChannel" : null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_HIGH);
                notificationManagerCompat.createNotificationChannel(notificationChannel);
            }

            final Intent intent = new Intent(PropertyDetailActivity.this, PropertyListActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(PropertyDetailActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            String propertyRef = propertyID.getText().toString().replaceAll("[^0-9]", "");

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(PropertyDetailActivity.this, notificationChannelId);
            notificationBuilder.setContentTitle(getString(R.string.notification_title));
            notificationBuilder.setContentText(propertyType.getText().toString().substring(7)+" n° "+propertyRef+ " "+getString(R.string.delete_notification_message));
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_done_24);
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setChannelId(notificationChannelId);
            notificationBuilder.setContentIntent(pendingIntent);

            notificationManagerCompat.notify(1, notificationBuilder.build());
    }





    //We display the name of the agent logged in on "Details Page"
    private void displayAgentProfileName()
    {
        //We retrieve the name store into the shared preferences
        final String agentProfileName = AppPreferences.getAgentSelection(this);

        //if the name is not null we restore it
        if (TextUtils.isEmpty(agentProfileName) == false)
        {
            agentLoggedIn.setText("Logged in as : "+agentProfileName);
        }
    }

    //Deletion of a property from the app's database when the agent in charge decides to delete on
    public void DeleteProperty(Property property){
        PropertyRepository.getInstance(this).deleteProperty(property);
        onBackPressed();

    }

    //Display icon on "Details Page" according to the type of the property
    public void setPropertyIconLayout(String propertyType){
        if(propertyType.equals("Apartment")){
            propertyIcon.setImageResource(R.drawable.building);
            //propertyLayout.setBackgroundResource(R.drawable.apartment_layout);
        }
        if(propertyType.equals("House")){
            propertyIcon.setImageResource(R.drawable.house);
            //propertyLayout.setBackgroundResource(R.drawable.house_layout);
        }
        if(propertyType.equals("Office")){
            propertyIcon.setImageResource(R.drawable.briefcase);
            //propertyLayout.setBackgroundResource(R.drawable.office_layout);
        }
    }

    //Display icon on "Details Page" at detail line "Agent in charge" according to the agent in charge
    public void setAgentInChargeIcon(String agentInCharge){
        if(agentInCharge.equals("Noah")){
            agentInChargeIcon.setImageResource(R.drawable.avatar1);

        }
        if(agentInCharge.equals("Emma")){
            agentInChargeIcon.setImageResource(R.drawable.avatar2);

        }
        if(agentInCharge.equals("Sasha")){
            agentInChargeIcon.setImageResource(R.drawable.avatar3);

        }
        if(agentInCharge.equals("Ivy")){
            agentInChargeIcon.setImageResource(R.drawable.avatar4);

        }
    }

    //Display no updation message at line "Updation Date" on "Details Pages" if the property has not been updated yet.
    // Otherwise, if it was updated, we display the updation date and time
    public String getUpdateDate(){
        //String update_date_message = null;
        String updateDate = AppPreferences.getUpdateDateTime(getApplication());
        if(updateDate == null){
           String update_date_message = "No updation registered for this property";
            AppPreferences.removeUpdateInfoFromPreferences(getApplication());
           return update_date_message;


        }else{
            AppPreferences.removeUpdateInfoFromPreferences(getApplication());
            return updateDate;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_price_converter:
                //final String propertyPrice€ = propertyPrice.getText().toString().substring(8);
                final String propertyPrice€ = propertyPrice.getText().toString().replaceAll("[^0-9]", "");
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getPriceConversion();
                call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    JsonObject rates = res.getAsJsonObject("rates");
                    double price€ = Double.valueOf(propertyPrice€);
                    double multiplier = Double.valueOf(rates.get("USD").toString());
                    double price$ = price€*multiplier;
                    convertedPrice.setText(new DecimalFormat("##.##").format(price$) + " $");

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
                 break;

            case R.id.btn_simulator:
                final String pType = propertyType.getText().toString().substring(7);
                final String pPrice = propertyPrice.getText().toString().replaceAll("[^0-9]", "");
                final String pID = propertyID.getText().toString();

                final Intent intent = new Intent(this, LoanSimulatorActivity.class);
                intent.putExtra("pID", pID);
                intent.putExtra("pType", pType);
                intent.putExtra("pPrice", pPrice);
                startActivity(intent);
                break;
        }



    }
}
