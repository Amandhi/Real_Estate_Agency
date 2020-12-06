package com.example.myrealestateagency.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.viewmodel.PropertyDetailActivityViewModel;

final public class PropertyDetailActivity extends AppCompatActivity {

    public static final String PROPERTY_EXTRA = "propertyExtra";

    //The tag used into this screen for the logs
    public static final String TAG = PropertyDetailActivity.class.getSimpleName();

    private TextView propertyType;

    private TextView propertySurface;

    private TextView propertyRooms;

    private TextView propertyAddress;

    private PropertyDetailActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_property_detail);

        //Then we retrieved the widget we will need to manipulate into the
        propertyType = findViewById(R.id.property_type);
        propertySurface = findViewById(R.id.surface);
        propertyRooms = findViewById(R.id.rooms);
        propertyAddress = findViewById(R.id.address);

        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(PropertyDetailActivityViewModel.class);

        observeProperty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //We add the "menu_property_management_functionalities" to the current activity
        getMenuInflater().inflate(R.menu.menu_property_management_functionalities, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //We handle the click on a menu item
        if (item.getItemId() == R.id.delete_property)
        {

            //viewModel.deleteProperty();
        }
        else if (item.getItemId() == R.id.update_property)
        {
            //viewModel.updateProperty();
        }

        return super.onOptionsItemSelected(item);
    }

    private void observeProperty()
    {
        viewModel.property.observe(this, new Observer<Property>()
        {
            @Override
            public void onChanged(Property property)
            {
                //Then we bind the User and the UI
                propertyType.setText(property.PropertyType);
                propertySurface.setText(property.PropertySurface);
                propertyRooms.setText(property.Number_of_Rooms);
                propertyAddress.setText(property.Address);


            }
        });
    }


}
