package com.example.myrealestateagency.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.example.myrealestateagency.viewmodel.AgentListActivityViewModel;
import com.example.myrealestateagency.viewmodel.PropertyListActivityViewModel;

import java.util.List;

final public class PropertyListActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String AGENT_EXTRA = "agentExtra";

    public static final String TAG = ListActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private PropertyListActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_property_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //We configure the click on all agent profile
        //We configure the click on the fab
        findViewById(R.id.fab).setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(PropertyListActivityViewModel.class);

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
        //We handle the click on a menu item
        if (item.getItemId() == R.id.disconnect)
        {
            final Intent intent = new Intent(this, AgentListActivity.class);
            startActivity(intent);

            //viewModel.DisconnectResetPref();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {
        //We open the AddUserActivity screen when the user clicks on the FAB
        final Intent intent = new Intent(this, AddPropertyActivity.class);
        startActivity(intent);
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


}
