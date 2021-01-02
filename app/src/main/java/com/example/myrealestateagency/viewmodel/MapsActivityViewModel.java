package com.example.myrealestateagency.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.repository.AgentRepository;
import com.example.myrealestateagency.repository.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityViewModel extends AndroidViewModel {

    public MapsActivityViewModel(@NonNull Application application)
    {

        super(application);

    }

    public List<Property> getProperties(){
        List<Property> properties = PropertyRepository.getInstance(getApplication()).getProperties();
        return properties;
    }

    /*
    public List<String> getPropertyAddresses(){
        List<Property> properties = getProperties();
        ArrayList<String> addresses = new ArrayList<>();
        if(!properties.isEmpty()){
            for(Property property: properties){
                addresses.add(property.getAddress());
            }
        }
        return addresses;
    }*/



}
