package com.example.myrealestateagency.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.AgentRepository;
import com.example.myrealestateagency.repository.PropertyRepository;

import java.util.List;

public final class PropertyListActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();



    public PropertyListActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void updateOrder()
    {


        loadProperties();
    }



    public void loadProperties()

    {
        properties.postValue(PropertyRepository.getInstance(getApplication()).getProperties());
    }

    /*public void DisconnectResetPref(){
        AppPreferences.removeDataFromPref(getApplication());
    }*/
}
