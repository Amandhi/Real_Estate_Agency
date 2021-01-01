package com.example.myrealestateagency.view;

import android.app.ListActivity;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.adapter.AgentsAdapter;
import com.example.myrealestateagency.adapter.PropertiesAdapter;
import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel;
import com.example.myrealestateagency.viewmodel.AgentListActivityViewModel;
import com.example.myrealestateagency.viewmodel.PropertyListActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

final public class PropertyListActivity extends AppCompatActivity {

    public static final String AGENT_EXTRA = "agentExtra";

    public static final String TAG = ListActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private PropertyListActivityViewModel viewModel;

    private TextView agentLoggedIn;

    private ImageView agentLoggedInAvatar;



    int [] agentAvatars = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4};



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_property_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        agentLoggedIn=findViewById(R.id.agentProfile_Name);
        agentLoggedInAvatar = findViewById(R.id.avatar);

        //We configure the click on the Apartment fab that enables to add a property of type "Apartment"
        findViewById(R.id.fabApartment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We open the AddPropertyActivity screen when the agent clicks on the FAB
                final Intent intent = new Intent(PropertyListActivity.this, AddPropertyActivity.class);
                intent.putExtra("SelectedType", "Apartment" );
                startActivity(intent);
            }
        });

        //We configure the click on the House fab that enables to add a property of type "House"
        findViewById(R.id.fabHouse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We open the AddPropertyActivity screen when the agent clicks on the FAB
                final Intent intent = new Intent(PropertyListActivity.this, AddPropertyActivity.class);
                intent.putExtra("SelectedType", "House" );
                startActivity(intent);
            }
        });

        //We configure the click on the Office fab that enables to add a property of type "Office"
        findViewById(R.id.fabOffice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We open the AddPropertyActivity screen when the agent clicks on the FAB
                final Intent intent = new Intent(PropertyListActivity.this, AddPropertyActivity.class);
                intent.putExtra("SelectedType", "Office" );
                startActivity(intent);
            }
        });

        viewModel = new ViewModelProvider(this).get(PropertyListActivityViewModel.class);

        displayAgentProfileName();
        observeProperties();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        viewModel.loadProperties();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //We add the "menu_disconnect" to the current activity
        getMenuInflater().inflate(R.menu.menu_disconnect, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        String agentLoggedInName = agentLoggedIn.getText().toString();
        //We handle the click on a menu item
        if (item.getItemId() == R.id.disconnect)
        {
            // Code for the log out alert dialog, when the agent wants to log out
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Leaving already "+ agentLoggedInName +" ?")
                    .setMessage("Are you sure you want to log out ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // if yes, the agent will be logged out and comes back to the page with all agents' profiles
                            final Intent intent = new Intent(PropertyListActivity.this, AgentListActivity.class);
                            startActivity(intent);
                            viewModel.DisconnectResetPref();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null) // Otherwise nothing happens
                    .show();



        }
        //if the agent wants to make a research, a new page opens up with a search form
        if(item.getItemId() == R.id.search){
            Intent intent = new Intent(this, SearchPropertyActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }










    private void observeProperties()
    {
        viewModel.properties.observe(this, new Observer<List<Property>>()
        {
            @Override
            public void onChanged(List<Property> properties)
            {
                final PropertiesAdapter propertiesAdapter = new PropertiesAdapter(properties);
                recyclerView.setAdapter(propertiesAdapter);
            }
        });
    }

    //We display the name of the agent logged in on "Home Page" along with his/her profile picture
    private void displayAgentProfileName()
    {
        //We retrieve the name store into the shared preferences
        String agentProfileName = viewModel.loadAgentName();

        //if the name is not null we restore it
        if (TextUtils.isEmpty(agentProfileName) == false)
        {
            agentLoggedIn.setText(agentProfileName);
        }
        if(agentProfileName.equals("Noah")){
            agentLoggedInAvatar.setImageResource(agentAvatars[0]);
        }else if(agentProfileName.equals("Emma")){
        agentLoggedInAvatar.setImageResource(agentAvatars[1]);
        }else if(agentProfileName.equals("Sasha")){
            agentLoggedInAvatar.setImageResource(agentAvatars[2]);
        }else if(agentProfileName.equals("Ivy")){
            agentLoggedInAvatar.setImageResource(agentAvatars[3]);
        }
    }

    //Disable onBackPressed, once the agent logged in he/she cannot go back, only way to go back is to disconnect via the menu
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }



}



