package com.example.myrealestateagency.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.view.PropertyDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public final class PropertyDetailActivityViewModel extends ViewModel {

    public MutableLiveData<Property> property = new MutableLiveData<>();


    public PropertyDetailActivityViewModel(SavedStateHandle savedStateHandle) {
        final Property propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }

    //Check needed to make sure if the agent trying to delete the property if the one in charges
    public boolean deletePropertyRequest(String agentLoggedIn, String agentInCharge){
        boolean delete_request=true;
        if(!agentLoggedIn.equals(agentInCharge)){
            delete_request =false;

        }
        return delete_request;
    }

    //Method to get latitude et longitude of a property according to its address
    public ArrayList<String>  getLatitudeLongitude(final String locationAddress, final Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        ArrayList<String> results = new ArrayList<>();
        String resultLat = null;
        String resultLong = null;
        try {
            List addressList = geocoder.getFromLocationName(locationAddress, 1);
            if (addressList != null & addressList.size() > 0) {
                Address address = (Address) addressList.get(0);
                StringBuilder stringBuilderLat = new StringBuilder();
                StringBuilder stringBuilderLong = new StringBuilder();
                stringBuilderLat.append(address.getLatitude());
                stringBuilderLong.append(address.getLongitude());
                resultLat = stringBuilderLat.toString();
                resultLong = stringBuilderLong.toString();
                results.add(resultLat);
                results.add(resultLong);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }




}
