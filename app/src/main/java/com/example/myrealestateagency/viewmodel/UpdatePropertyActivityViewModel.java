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
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.view.AddPropertyActivity;
import com.example.myrealestateagency.view.UpdatePropertyActivity;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class UpdatePropertyActivityViewModel extends AndroidViewModel {

    public enum Event
    {
        DisplayTypeError, DisplayPriceError, DisplaySurfaceError, DisplayRoomError, DisplayAddressError, DisplayDescriptionError, DisplayAgentError, DisplayStatusError, DisplayBlankFieldError
    }

    public MutableLiveData<UpdatePropertyActivityViewModel.Event> event = new MutableLiveData<>();




    public UpdatePropertyActivityViewModel(@NonNull Application application)
    {
        super(application);

    }

    //Here we check the agent's entries on the "Update property form", if correctly filled we update the property in the app's database,
    // else we display message on screen to correctly fill the form.
    public void updateProperty(int propertyID, String propertyType, Double propertyPrice, Integer propertySurface, Integer propertyRooms, String propertyAddress, String propertyDescription, String propertyStatus, String propertyAgent) {
       PropertyRepository.getInstance(getApplication()).updateProperty(propertyID, propertyType, propertyPrice, propertySurface, propertyRooms, propertyAddress, propertyDescription, propertyStatus, propertyAgent);
    }

    public void saveChanges(int propertyID, String propertyType, Double propertyPrice, Integer propertySurface, Integer propertyRooms,
                             String propertyAddress, String propertyDescription, String propertyStatus, RadioGroup statusSelection, String propertyAgent, RadioGroup agentInChargeSelection)
    {


        //We check if all entries are valid (not null and not empty)
        final String canAddProperty = checkFormEntries(propertyPrice, propertySurface, propertyRooms, propertyAddress, propertyDescription);


        if (canAddProperty.equals("valid"))
        {
            //We add the property to the list and we reset the form
            updateProperty(propertyID, propertyType, propertyPrice, propertySurface, propertyRooms, propertyAddress, propertyDescription, propertyStatus, propertyAgent);
            //event.postValue(AddPropertyActivityViewModel.Event.ResetForm);

        }
       /* if(canAddProperty.equals("Please enter a valid type"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a valid type");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayTypeError);
        }
        if(canAddProperty.equals("Please enter a valid price"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a valid price");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayPriceError);
        }
        if(canAddProperty.equals("Please enter a surface value"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a surface value");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplaySurfaceError);
        }
        if(canAddProperty.equals("Please enter number of rooms"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter number of rooms");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayRoomError);
        }
        if(canAddProperty.equals("Please enter a valid address"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a valid address");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayAddressError);
        }
        if(canAddProperty.equals("Please enter a description"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please enter a description");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayDescriptionError);
        }
        if(canAddProperty.equals("Please select a status"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please select a status");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayStatusError);
        }
        if(canAddProperty.equals("Please choose an agent in charge"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please choose an agent in charge");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayAgentError);
        }*/
        if(canAddProperty.equals("Please fill in all the fields"))
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Please fill in all the fields");
            event.postValue(UpdatePropertyActivityViewModel.Event.DisplayBlankFieldError);
        }

    }


    public String checkFormEntries(Double propertyPrice, Integer propertySurface, Integer propertyRooms, String propertyAddress, String propertyDescription)
    {
        String entrieschecked = "valid";

       /* if (propertyPrice==null) {
            //Log.w(AddPropertyActivity.TAG, "Please enter a surface value");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a valid price";
            //event.postValue(UpdatePropertyActivityViewModel.Event.DisplaySurfaceError);
        }

        if (propertySurface==null) {
            //Log.w(AddPropertyActivity.TAG, "Please enter a surface value");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a surface value";
            //event.postValue(UpdatePropertyActivityViewModel.Event.DisplaySurfaceError);
        }

        if (propertyRooms==null) {
            //Log.w(AddPropertyActivity.TAG, "Please enter number of rooms");
            // event.postValue(Event.DisplayError);
            entrieschecked = "Please enter number of rooms";
            //event.postValue(UpdatePropertyActivityViewModel.Event.DisplayRoomError);
        }

        if (TextUtils.isEmpty(propertyAddress)){
            //Log.w(AddPropertyActivity.TAG, "Please enter a valid address");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a valid address";
            //event.postValue(UpdatePropertyActivityViewModel.Event.DisplayAddressError);
        }

        if (TextUtils.isEmpty(propertyDescription)){
            //Log.w(AddPropertyActivity.TAG, "Please enter a valid address");
            //event.postValue(Event.DisplayError);
            entrieschecked = "Please enter a description";
            //event.postValue(UpdatePropertyActivityViewModel.Event.DisplayAddressError);
        }*/

        if (propertySurface == null || propertyPrice == null  || propertyRooms == null || TextUtils.isEmpty(propertyAddress) ){

            entrieschecked = "Please fill in all the fields";

        }

        if (propertyPrice==null && propertySurface==null && propertyRooms==null && TextUtils.isEmpty(propertyAddress) && TextUtils.isEmpty(propertyDescription)  ){

            entrieschecked = "Please fill in all the fields";
            //event.postValue(UpdatePropertyActivityViewModel.Event.DisplayBlankFieldError);

            //Log.i("allowed types : ", String.valueOf(allowed_property_types));
            //Log.i("entered type : ", String.valueOf(userpType));
            //return entrieschecked;
        }




        return entrieschecked;
    }

    //We retrieve here, similarly to the add activity, the name of the agent according to the radio button clicked while filling the updation property form
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

    //Again, similarly to the add activity, we retrieve here the status of the property (sold or not) according to the radion button clicked
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



    //Create date and time of when the updation of the property was committed
    public void saveUpdateDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());

        Log.i("Creation : ", dateTime);
        AppPreferences.saveUpdateDateTime(getApplication(), dateTime);
    }


}

