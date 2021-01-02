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
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.AgentRepository;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.repository.PropertyTypeRepository;
import com.example.myrealestateagency.view.AddPropertyActivity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public final class AddPropertyActivityViewModel extends AndroidViewModel {



    public MutableLiveData<List<PropertyType>> types = new MutableLiveData<>();



    /*
    PropertyType aptmt = new PropertyType("apartment");
    PropertyType house = new PropertyType("house");
    */




    public enum Event
    {
        ResetForm, DisplayTypeError, DisplayPriceError, DisplaySurfaceError, DisplayRoomError, DisplayAddressError, DisplayDescriptionError, DisplayAgentError, DisplayStatusError, DisplayBlankFieldError
    }


    public MutableLiveData<Event> event = new MutableLiveData<>();

    /*public RadioGroup pType;
    private RadioButton radioHouse;

    private RadioButton radioAprmt;

    private RadioButton radioOffice;*/


    public AddPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
        //PropertyTypeListCreation();

    }


    //Here we check the agent's entries on the "Add property form", if correctly filled we add the property into the app's database,
    // else we display message on screen to correctly fill the form.
    public void saveProperty(String propertyType, Double propertyPrice, Integer propertySurface, Integer propertyRooms,
                         String propertyAddress, String propertyDescription, String propertyAgent, RadioGroup agentInChargeSelection, String propertyStatus, RadioGroup StatusSelection)
    {


        //We check if all entries are valid (not null and not empty)
        final String canAddProperty = checkFormEntries(propertyPrice, propertySurface, propertyRooms, propertyAddress, propertyDescription, StatusSelection, agentInChargeSelection);


        if (canAddProperty.equals("valid"))
        {
            //We add the property to the list and we reset the form
            persistProperty(propertyType, propertyPrice, propertySurface, propertyRooms, propertyAddress, propertyDescription, propertyStatus, propertyAgent);;
            event.postValue(Event.ResetForm);

        }
        /*if(canAddProperty.equals("Please enter a valid type"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a valid type");
            event.postValue(Event.DisplayTypeError);
        }
        if(canAddProperty.equals("Please enter a valid price"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a valid price");
            event.postValue(Event.DisplayPriceError);
        }
        if(canAddProperty.equals("Please enter a surface value"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a surface value");
            event.postValue(Event.DisplaySurfaceError);
        }
        if(canAddProperty.equals("Please enter number of rooms"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter number of rooms");
            event.postValue(Event.DisplayRoomError);
        }
        if(canAddProperty.equals("Please enter a valid address"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a valid address");
            event.postValue(Event.DisplayAddressError);
        }
        if(canAddProperty.equals("Please enter a description"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a description");
            event.postValue(Event.DisplayDescriptionError);
        }
        if(canAddProperty.equals("Please select an agent in charge"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please select an agent in charge");
            event.postValue(Event.DisplayAgentError);
        }
        if(canAddProperty.equals("Please select a status"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please select a status");
            event.postValue(Event.DisplayStatusError);
        }*/
        if(canAddProperty.equals("Please fill in all the fields"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please fill in all the fields");
            event.postValue(Event.DisplayBlankFieldError);
        }

    }

    private void persistProperty(String propertyType, Double propertyPrice, Integer propertySurface, Integer propertyRooms,
                                 String propertyAddress, String propertyDescription, String propertyStatus, String propertyAgent)
    {
        PropertyRepository.getInstance(getApplication()).addProperty(new Property(propertyType, propertyPrice, propertySurface, propertyRooms, propertyAddress, propertyDescription, propertyStatus, propertyAgent));
    }

    public String checkFormEntries(Double propertyPrice, Integer propertySurface, Integer propertyRooms, String propertyAddress, String propertyDescription, RadioGroup agentInChargeSelection, RadioGroup StatusSelection)
    {
        String entrieschecked = "valid";
        /*ArrayList<String> allowed_property_types = new ArrayList<>();
        allowed_property_types.add("apartment");
        allowed_property_types.add("house");
        allowed_property_types.add("office");
        */

       /* ArrayList<String> allowed_agent_names = new ArrayList<>();
        allowed_agent_names.add("noah");
        allowed_agent_names.add("emma");
        allowed_agent_names.add("sasha");
        allowed_agent_names.add("ivy");*/

       //int id = agentInChargeSelection.getCheckedRadioButtonId();


        //boolean userpType = !allowed_property_types.contains(propertyType);

      /* if (TextUtils.isEmpty(propertyType) ){

            //Log.w(AddPropertyActivity.TAG, "Please enter a valid type");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a valid type";

            //Log.i("allowed types : ", String.valueOf(allowed_property_types));
            //Log.i("entered type : ", String.valueOf(userpType));
            //return entrieschecked;
        }
        if (propertyPrice==null ){

            //Log.w(AddPropertyActivity.TAG, "Please enter a valid type");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a valid price";

            //Log.i("allowed types : ", String.valueOf(allowed_property_types));
            //Log.i("entered type : ", String.valueOf(userpType));
            //return entrieschecked;
        }
        if (propertySurface==null) {
            //Log.w(AddPropertyActivity.TAG, "Please enter a surface value");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a surface value";
        }

        if (propertyRooms==null) {
            //Log.w(AddPropertyActivity.TAG, "Please enter number of rooms");
           // event.postValue(Event.DisplayError);
            entrieschecked = "Please enter number of rooms";
        }

        if (TextUtils.isEmpty(propertyAddress)){
            //Log.w(AddPropertyActivity.TAG, "Please enter a valid address");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a valid address";
        }

        if (TextUtils.isEmpty(propertyDescription)){
            //Log.w(AddPropertyActivity.TAG, "Please enter a valid address");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a description";
        }*/

       /*if (TextUtils.isEmpty(propertyAgent) || !allowed_agent_names.contains(propertyAgent.toLowerCase())){
            //Log.w(AddPropertyActivity.TAG, "Please enter a valid agent name");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a valid agent name";
        }*/

       /*if(agentInChargeSelection.getCheckedRadioButtonId()==-1){
           entrieschecked = "Please select an agent in charge";
           //String agentSelection = null;
       }

        if(StatusSelection.getCheckedRadioButtonId()==-1){
            entrieschecked = "Please select status";
            //String agentSelection = null;
        }*/

        if (propertySurface == null || propertyPrice == null  || propertyRooms == null || TextUtils.isEmpty(propertyAddress)|| agentInChargeSelection.getCheckedRadioButtonId()==-1 || StatusSelection.getCheckedRadioButtonId()==-1 ){

                entrieschecked = "Please fill in all the fields";
        }


       if (propertyPrice == null && propertySurface == null && propertyRooms == null && TextUtils.isEmpty(propertyAddress) && TextUtils.isEmpty(propertyDescription) && agentInChargeSelection.getCheckedRadioButtonId()==-1 && StatusSelection.getCheckedRadioButtonId()==-1 ){

            entrieschecked = "Please fill in all the fields";
        }




        return entrieschecked;
    }



    //According to the radio button clicked by the agent when filling the form to add a property, we associated each type (each radio button to a number) ---> this was the initial idea when working with several tables
    //However as I did not succeed in working with mulitple table, we retrieve here the name of the agent according to the radio button clicked while filling the add property form
    //So that we can add the property in Property table
  public String checkRadioButtonClick(RadioButton Noah, RadioButton Emma, RadioButton Sasha, RadioButton Ivy){
        //Integer userpTypeInput=0;
        String userpTypeInput=null;
            if(Noah.isChecked()){
                userpTypeInput = Noah.getText().toString();
                //userpTypeInput = 1;
                return userpTypeInput;
            }
            if(Emma.isChecked()){
                userpTypeInput = Emma.getText().toString();
                //userpTypeInput = 2;
                return userpTypeInput;
            }
            if(Sasha.isChecked()){
                userpTypeInput = Sasha.getText().toString();
                //userpTypeInput = 3;
                return userpTypeInput;
            }
            if(Ivy.isChecked()){
                userpTypeInput = Ivy.getText().toString();
                //userpTypeInput = 3;
                return userpTypeInput;
      }

        return userpTypeInput;
    }

    //we retrieve here the status of the property (sold or not) according to the radion button clicked
    public String checkPropertyStatusClick(RadioButton Sold, RadioButton NotSold){
        //Integer userpTypeInput=0;
        String userpTypeInput=null;
        if(Sold.isChecked()){
            userpTypeInput = Sold.getText().toString();
            //userpTypeInput = 1;
            return userpTypeInput;
        }
        if(NotSold.isChecked()){
            userpTypeInput = NotSold.getText().toString();
            //userpTypeInput = 2;
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
    /*public void PropertyTypeListCreation(){
        PropertyTypeRepository.getInstance(getApplication()).addType(aptmt);
        PropertyTypeRepository.getInstance(getApplication()).addType(house);

    }*/



    //Create date and time of when the creation of the property was committed
    public void saveCreationDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());

        Log.i("Creation : ", dateTime);
        AppPreferences.saveCreationDateTime(getApplication(), dateTime);
    }

    //Check input and catch exception is this field is empty
    public Double CheckDouble(String value){
        Double a;
       /* if(value.isEmpty()){
            return 0;
        }else {
            return 0;
        }*/
        if (value != null && value.length() > 0){
            try {
                a = Double.parseDouble(value);
                return a;
            }catch(java.lang.NumberFormatException e){
                return null;

            }
        }else{
            return null;
        }


    }


    public Integer CheckInteger(String value){
        Integer a;
       /* if(value.isEmpty()){
            return 0;
        }else {
            return 0;
        }*/
        if (value != null && value.length() > 0){
            try {
                a = Integer.parseInt(value);
                return a;
            }catch(NumberFormatException e){
                return null;

            }
        }else{
            return null;
        }


    }



}
