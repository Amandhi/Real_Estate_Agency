package com.example.myrealestateagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.bo.PropertyType;
import com.example.myrealestateagency.repository.AgentRepository;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.repository.PropertyTypeRepository;
import com.example.myrealestateagency.view.AddPropertyActivity;

import java.util.List;

public final class AddPropertyActivityViewModel extends AndroidViewModel {



    public MutableLiveData<List<PropertyType>> types = new MutableLiveData<>();


    PropertyType aptmt = new PropertyType("apartment");
    PropertyType house = new PropertyType("house");




    public enum Event
    {
        ResetForm, DisplayError
    }


    public MutableLiveData<Event> event = new MutableLiveData<>();

    /*public RadioGroup pType;
    private RadioButton radioHouse;

    private RadioButton radioAprmt;

    private RadioButton radioOffice;*/

    public AddPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
        PropertyTypeListCreation();
    }

    public void saveProperty(int propertyType, int propertySurface, int propertyRooms,
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

    private void persistProperty(int propertyType, int propertySurface, int propertyRooms,
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



    //According to the radio button clicked by the agent when fillin the form to add a property, we associated each type (each radio button to a number
    //So that we can add the property in Property table (as the type attribute is a int and not a String
    public int checkRadioButtonClick(RadioButton house, RadioButton aprmt, RadioButton office){
        int userpTypeInput = 0;
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


    /*public int CheckSpinnerSelection(Spinner spinner){
        int user_input;
        String type1 = house.toString();
        String type2 = aptmt.toString();


        String text = spinner.getSelectedItem().toString();


        if (text.equals(type1)){
            user_input=1;
        }else{
            user_input=2;
        }
        return user_input;
    }*/

    //Populating PropertyType table with data
    public void PropertyTypeListCreation(){
        PropertyTypeRepository.getInstance(getApplication()).addType(aptmt);
        PropertyTypeRepository.getInstance(getApplication()).addType(house);

    }

}
