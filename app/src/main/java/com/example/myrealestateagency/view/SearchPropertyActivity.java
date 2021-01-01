package com.example.myrealestateagency.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.adapter.PropertiesAdapter;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.viewmodel.AddPropertyActivityViewModel;
import com.example.myrealestateagency.viewmodel.PropertyListActivityViewModel;
import com.example.myrealestateagency.viewmodel.SearchPropertyActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchPropertyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SearchPropertyActivity.class.getSimpleName();

    public static  ArrayList<Property> propertiesList = new ArrayList<>();


    private CheckBox checkBoxAptmt;
    private CheckBox checkBoxHouse;
    private CheckBox checkBoxOffice;
    private CheckBox checkBoxAllPrice;
    private EditText editTextMinPrice;
    private EditText editTextMaxPrice;
    private CheckBox checkBoxAllSurface;
    private EditText editTextMinSurface;
    private EditText editTextMaxSurface;
    private CheckBox checkBoxAllRooms;
    private EditText editTextRooms;
   // private RadioButton radioButtonSold;
   // private RadioButton radioButtonNotSold;





    private RecyclerView recyclerView;



    private SearchPropertyActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_search_property);


        checkBoxAptmt = findViewById(R.id.checkbox_aptmt);
        checkBoxHouse = findViewById(R.id.checkbox_house);
        checkBoxOffice = findViewById(R.id.checkbox_office);
        checkBoxAllPrice = findViewById(R.id.checkbox_all_prices);
        editTextMinPrice = findViewById(R.id.search_price_min);
        editTextMaxPrice = findViewById(R.id.search_price_max);
        checkBoxAllSurface = findViewById(R.id.checkbox_all_surfaces);
        editTextMinSurface = findViewById(R.id.search_surface_min);
        editTextMaxSurface = findViewById(R.id.search_surface_max);
        checkBoxAllRooms = findViewById(R.id.checkbox_all_rooms);
        editTextRooms = findViewById(R.id.search_rooms);
        //radioButtonSold = findViewById(R.id.radio_sold);
        //radioButtonNotSold = findViewById(R.id.radio_not_sold);

        findViewById(R.id.search).setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);

        viewModel = new ViewModelProvider(this).get(SearchPropertyActivityViewModel.class);

        observeEvent();



    }


    //Display properties looked for on "Search Page" under the form.
    // If no results, display no result message on screen
   private void loadFilteredProperties(){
       List<Property> filteredPropertiesList = viewModel.SearchFunction(checkBoxAptmt, checkBoxHouse, checkBoxOffice, checkBoxAllPrice, editTextMinPrice, editTextMaxPrice, checkBoxAllSurface, editTextMinSurface, editTextMaxSurface, checkBoxAllRooms, editTextRooms);
       if(filteredPropertiesList.isEmpty()){
           Toast.makeText(this, R.string.no_results, Toast.LENGTH_SHORT).show();
           recyclerView.setAdapter(null);
       }else{
           final PropertiesAdapter propertiesAdapter = new PropertiesAdapter(filteredPropertiesList);
           recyclerView.setAdapter(propertiesAdapter);

       }



   }


    @Override
    public void onClick(View view) {
        loadFilteredProperties();
        resetForm();


    }

    public void resetForm(){

       checkBoxAptmt.setChecked(false);
       checkBoxHouse.setChecked(false);
       checkBoxOffice.setChecked(false);
       checkBoxAllPrice.setChecked(false);
       editTextMinPrice.setText(null);
       editTextMaxPrice.setText(null);
       checkBoxAllSurface.setChecked(false);
       editTextMinSurface.setText(null);
       editTextMaxSurface.setText(null);
       checkBoxAllRooms.setChecked(false);
       editTextRooms.setText(null);
       //recyclerView.setAdapter(null);

    }

    private void observeEvent() {
        viewModel.event.observe(this, new Observer<SearchPropertyActivityViewModel.Event>() {
            @Override
            public void onChanged(SearchPropertyActivityViewModel.Event event) {
                if (event == SearchPropertyActivityViewModel.Event.DisplaySearchError) {
                    displaySearchError();
                }

            }
        });
    }

    private void displaySearchError() {
        Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
    }



}
