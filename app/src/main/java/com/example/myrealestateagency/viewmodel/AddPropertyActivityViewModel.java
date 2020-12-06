package com.example.myrealestateagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.view.AddPropertyActivity;

public final class AddPropertyActivityViewModel extends AndroidViewModel {

    public enum Event
    {
        ResetForm, DisplayError
    }

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public RadioGroup pType;
    private RadioButton radioHouse;

    private RadioButton radioAprmt;

    private RadioButton radioOffice;

    public AddPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void saveProperty(Integer propertyType, Integer propertySurface, Integer propertyRooms,
                         String propertyAddress)
    {


        //We check if all entries are valid (not null and not empty)
        /*final boolean canAddProperty = checkFormEntries(pType, propertyType, propertySurface, propertyRooms, propertyAddress);


        if (canAddProperty == true)
        {*/
            //We add the property to the list and we reset the form
            persistProperty(propertyType, propertySurface, propertyRooms, propertyAddress);;
            event.postValue(Event.ResetForm);
       /* }
        else
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Cannot add the property");
            event.postValue(Event.DisplayError);
        }*/
    }

    private void persistProperty(Integer propertyType, Integer propertySurface, Integer propertyRooms,
                                 String propertyAddress)
    {
        PropertyRepository.getInstance(getApplication()).addProperty(new Property(propertyType, propertySurface, propertyRooms, propertyAddress));
    }

    /*private boolean checkFormEntries(RadioGroup pType, Integer propertyType, Integer propertySurface, Integer propertyRooms,
                                     String propertyAddress)
    {
        boolean result= true;
        if (pType.getCheckedRadioButtonId() == -1){
            result = propertyType == null && propertySurface == null && propertyRooms == null && TextUtils.isEmpty(propertyAddress) == false;
            return result;
        }


        return result;
    }*/



    public Integer checkRadioButtonClick(RadioButton house, RadioButton aprmt, RadioButton office){
        Integer userpTypeInput = 0;
            if(house.isChecked()){
                userpTypeInput = 1;
                return userpTypeInput;
            }
            if(aprmt.isChecked()){
                userpTypeInput = 2;
                return userpTypeInput;
            }
            if(office.isChecked()){
                userpTypeInput = 3;
                return userpTypeInput;
            }

        return userpTypeInput;
    }

}
