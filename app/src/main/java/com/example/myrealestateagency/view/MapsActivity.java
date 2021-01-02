package com.example.myrealestateagency.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.viewmodel.MapsActivityViewModel;
import com.example.myrealestateagency.viewmodel.PropertyDetailActivityViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PropertyDetailActivityViewModel viewModel;
    private MapsActivityViewModel mapsviewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(PropertyDetailActivityViewModel.class);
        mapsviewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(MapsActivityViewModel.class);
    }

    //Display of pins on Google Maps for each property saved on the app's database : blue if property is not sold, red otherwise
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        List<Property> properties = mapsviewModel.getProperties();
        //List<String> addresses = mapsviewModel.getPropertyAddresses();

        //if(!addresses.isEmpty()){
            for(Property prop: properties){
                //for(String address: addresses){
                    if(prop.getPropertyStatus().equals("Sold")){
                        //Log.i("status 1", prop.getPropertyStatus());
                        ArrayList<Double> addressLatLng = viewModel.getLatitudeLongitude(prop.getAddress(), getApplicationContext());
                        LatLng property = new LatLng(addressLatLng.get(0), addressLatLng.get(1));

                        mMap.addMarker(new MarkerOptions()
                                .position(property))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(property));
                    }
                    if(prop.getPropertyStatus().equals("Not Sold")){
                        //Log.i("status 2", prop.getPropertyStatus());
                        ArrayList<Double> addressLatLng = viewModel.getLatitudeLongitude(prop.getAddress(), getApplicationContext());
                        LatLng property = new LatLng(addressLatLng.get(0), addressLatLng.get(1));

                        mMap.addMarker(new MarkerOptions()
                                .position(property))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(property));
                    }

                //}

            }

        //}



    }
}
