package com.example.myrealestateagency.viewmodel;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.retrofit.RetrofitBuilder;
import com.example.myrealestateagency.retrofit.RetrofitInterface;
import com.example.myrealestateagency.view.PropertyDetailActivity;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    //Method to enable price conversion
    /*public void PriceConverter(String propertyPrice){
        RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
        Call<JsonObject> call = retrofitInterface.getPriceConversion();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                JsonObject rates = res.getAsJsonObject("rates");
                double price€ = Double.valueOf(propertyPrice);
                double multiplier = Double.valueOf(rates.get("USD").toString());
                double price$ = price€*multiplier;



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



    }*/



    //Method to get latitude et longitude of a property according to its address
    public ArrayList<Double>  getLatitudeLongitude(final String locationAddress, final Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        ArrayList<Double> results = new ArrayList<>();

        try {
            List addressList = geocoder.getFromLocationName(locationAddress, 1);
            if (addressList != null & addressList.size() > 0) {
                Address address = (Address) addressList.get(0);
                StringBuilder stringBuilderLat = new StringBuilder();
                StringBuilder stringBuilderLong = new StringBuilder();
                stringBuilderLat.append(address.getLatitude());
                stringBuilderLong.append(address.getLongitude());
                double resultLat = Double.valueOf(stringBuilderLat.toString());
                double resultLong = Double.valueOf(stringBuilderLong.toString());
                results.add(resultLat);
                results.add(resultLong);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }




}
