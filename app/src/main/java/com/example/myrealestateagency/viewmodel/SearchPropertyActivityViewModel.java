package com.example.myrealestateagency.viewmodel;

import android.app.Application;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.repository.PropertyRepository;
import com.example.myrealestateagency.view.AddPropertyActivity;
import com.example.myrealestateagency.view.SearchPropertyActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class SearchPropertyActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    public SearchPropertyActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public enum Event
    {
        DisplaySearchError,
    }

    public MutableLiveData<SearchPropertyActivityViewModel.Event> event = new MutableLiveData<>();


    public boolean CheckSearchFormEntries(CheckBox checkBoxAptmt, CheckBox checkBoxHouse, CheckBox checkBoxOffice, CheckBox checkBoxAnyPrice, EditText priceMin, EditText priceMax, CheckBox checkBoxAnySurface, EditText surfaceMin, EditText surfaceMax, CheckBox checkBoxAnyRooms, EditText numberOfRooms){
       boolean check = true;
        if(!checkBoxAptmt.isChecked() && !checkBoxHouse.isChecked() && !checkBoxOffice.isChecked()){
            check = false;
            return check;
        }
        if (!checkBoxAnyPrice.isChecked() && priceMin.getText().toString().equals("") && priceMax.getText().toString().equals("")){
            check = false;
            return check;
        }
        if(!checkBoxAnySurface.isChecked() && surfaceMin.getText().toString().equals("") && surfaceMax.getText().toString().equals("")){
            check = false;
            return check;
        }
        if(!checkBoxAnyRooms.isChecked() && numberOfRooms.getText().toString().equals("") ){
            check = false;
            return check;
        }
        /*if(!checkBoxSold.isChecked() && !checkBoxNotSold.isChecked() ){
            check = false;
            return check;
        }*/

        return  check;
    }

    //Method that filters the app's database according to the search made by the agent and retrieve the matching (or empty if no results) list
    //Here I details every possible case when filling the search form in order to display a matching result
    public List<Property> SearchFunction(CheckBox checkBoxAptmt, CheckBox checkBoxHouse, CheckBox checkBoxOffice, CheckBox checkBoxAnyPrice, EditText priceMin, EditText priceMax, CheckBox checkBoxAnySurface, EditText surfaceMin, EditText surfaceMax, CheckBox checkBoxAnyRooms, EditText numberOfRooms) {
        List<Property> filteredProperties = new ArrayList<>();
        List<Property> wholePropertyList = PropertyRepository.getInstance(getApplication()).getProperties();

        boolean check = CheckSearchFormEntries(checkBoxAptmt, checkBoxHouse, checkBoxOffice, checkBoxAnyPrice, priceMin, priceMax, checkBoxAnySurface, surfaceMin, surfaceMax, checkBoxAnyRooms, numberOfRooms);
        if (check == true) {

            //Search by House
            if (!checkBoxAptmt.isChecked() && checkBoxHouse.isChecked() && !checkBoxOffice.isChecked()) {

                //Search by House and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by House, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by House, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House")) {
                                    filteredProperties.add(property);
                                }

                            }

                        }

                        //Search by House, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            //Search by House, any price, any surface, a specific number of rooms, status sold

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }
                    }

                    //Search by House any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPropertySurface() >= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                }

                //Search by House and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by House, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                }

                //Search by House, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }
                }

                //Search by House, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);

                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("House") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }


                }

            }

            //Search by Apartment
            if (checkBoxAptmt.isChecked() && !checkBoxHouse.isChecked() && !checkBoxOffice.isChecked()) {

                //Search by Apartment and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by Apartment, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Apartment, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by Apartment, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment")) {
                                    filteredProperties.add(property);
                                }

                            }

                        }

                        //Search by Apartment, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            //Search by House, any price, any surface, a specific number of rooms, status sold

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }
                    }

                    //Search by Apartment any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPropertySurface() >= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                }

                //Search by Apartment and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by Apartment, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Apartment, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                }

                //Search by Apartment, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by Apartment, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Apartment, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }
                }

                //Search by Apartment, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by Apartment, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Apartment, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);

                                }
                            }


                        }

                    }

                    //Search by Apartment, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Apartment, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Apartment, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Apartment, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Apartment") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }


                }

            }

            //Search by Office
            if (!checkBoxAptmt.isChecked() && !checkBoxHouse.isChecked() && checkBoxOffice.isChecked()) {

                //Search by Office and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by Office, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Office, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by Office, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") ) {
                                    filteredProperties.add(property);
                                }

                            }

                        }

                        //Search by Office, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getNumber_of_Rooms() == rooms) {
                                    filteredProperties.add(property);
                                }
                            }


                        }
                    }

                    //Search by Office any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPropertySurface() >= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                }

                //Search by Office and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by Office, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Office, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Office, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= sMax) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                }

                //Search by Office, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by Office, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Office, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Office, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }
                }

                //Search by Office, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by Office, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by Office, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Office, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);

                                }
                            }


                        }

                    }

                    //Search by Office, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }

                    //Search by Office, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by Office, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                        //Search by Office, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());

                            for (Property property : wholePropertyList) {
                                if (property.getPropertyType().equals("Office") && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                    filteredProperties.add(property);
                                }
                            }


                        }

                    }


                }

            }

            //Search by Apartment & House
            if (checkBoxAptmt.isChecked() && checkBoxHouse.isChecked() && !checkBoxOffice.isChecked()) {
                List<String> selectedTypes = new ArrayList<>();
                selectedTypes.add("Apartment");
                selectedTypes.add("House");

                //Search by House and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by House, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by House, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                for (String selectedtype: selectedTypes){
                                    if (property.getPropertyType().equals(selectedtype) ) {
                                        filteredProperties.add(property);
                                    }
                                }

                            }

                        }

                        //Search by House, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }
                    }

                    //Search by House any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by House, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }
                }

                //Search by House, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);

                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }


                }

            }

            //Search by Apartment & Office
            if (checkBoxAptmt.isChecked() && !checkBoxHouse.isChecked() && checkBoxOffice.isChecked()) {
                List<String> selectedTypes = new ArrayList<>();
                selectedTypes.add("Apartment");
                selectedTypes.add("Office");

                //Search by House and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by House, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by House, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                for (String selectedtype: selectedTypes){
                                    if (property.getPropertyType().equals(selectedtype) ) {
                                        filteredProperties.add(property);
                                    }
                                }

                            }

                        }

                        //Search by House, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }
                    }

                    //Search by House any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by House, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }
                }

                //Search by House, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);

                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }


                }

            }

            //Search by House & Office
            if (!checkBoxAptmt.isChecked() && checkBoxHouse.isChecked() && checkBoxOffice.isChecked()) {
                List<String> selectedTypes = new ArrayList<>();
                selectedTypes.add("House");
                selectedTypes.add("Office");

                //Search by House and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by House, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by House, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                for (String selectedtype: selectedTypes){
                                    if (property.getPropertyType().equals(selectedtype) ) {
                                        filteredProperties.add(property);
                                    }
                                }

                            }

                        }

                        //Search by House, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }
                    }

                    //Search by House any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by House, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }
                }

                //Search by House, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);

                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }


                }

            }

            //Search by Apartment & House & Office
            if (checkBoxAptmt.isChecked() && checkBoxHouse.isChecked() && checkBoxOffice.isChecked()) {
                List<String> selectedTypes = new ArrayList<>();
                selectedTypes.add("Apartment");
                selectedTypes.add("House");
                selectedTypes.add("Office");

                //Search by House and any price
                if (checkBoxAnyPrice.isChecked()) {

                    //Search by House, any price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, any price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            //Search by House, any price, any surface, any number of rooms, status sold
                            for (Property property : wholePropertyList) {
                                for (String selectedtype: selectedTypes){
                                    if (property.getPropertyType().equals(selectedtype) ) {
                                        filteredProperties.add(property);
                                    }
                                }

                            }

                        }

                        //Search by House, any price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }
                    }

                    //Search by House any price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, any price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, any price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, any price, condition on minimum and maximum, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, any price, condition on minimum and maximum, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for (Property property : wholePropertyList) {
                                for(String selectedtype: selectedTypes) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House and condition on minimum price
                if (!priceMin.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());

                    //Search by House, condition on minimum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on min price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on minimum price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPropertySurface() >= sMin && property.getPrice() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                }

                //Search by House, condition on maximum price
                if (!priceMax.getText().toString().equals("")) {
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by Apartment, condition on maximum price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maxiumum price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on maximum price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on maximum price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }
                }

                //Search by House, condition on minimum and maximum price
                if (!priceMin.getText().toString().equals("") && !priceMax.getText().toString().equals("")) {
                    int pMin = Integer.parseInt(priceMin.getText().toString());
                    int pMax = Integer.parseInt(priceMax.getText().toString());

                    //Search by House, condition on minimum and maximum price, any surface
                    if (checkBoxAnySurface.isChecked()) {

                        //Search by House, condition on minimum and maximum price, any surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, any surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House,condition on minimum and maximum price, condition on minimum surface
                    if (!surfaceMin.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);

                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on maximum surface
                    if (!surfaceMax.getText().toString().equals("")) {
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }

                    //Search by House, condition on minimum and maximum price, condition on minimum and maximum surface
                    if (!surfaceMin.getText().toString().equals("") && !surfaceMax.getText().toString().equals("")) {
                        int sMin = Integer.parseInt(surfaceMin.getText().toString());
                        int sMax = Integer.parseInt(surfaceMax.getText().toString());

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, any number of rooms
                        if (checkBoxAnyRooms.isChecked()) {
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                        //Search by House, condition on min and max price, condition on minimum and maximum surface, a specific number of rooms
                        if (!numberOfRooms.getText().toString().equals("")) {
                            int rooms = Integer.parseInt(numberOfRooms.getText().toString());
                            for(String selectedtype: selectedTypes) {
                                for (Property property : wholePropertyList) {
                                    if (property.getPropertyType().equals(selectedtype) && property.getPrice() >= pMin && property.getPrice() <= pMax && property.getPropertySurface() >= sMin && property.getPropertySurface() <= sMax && property.getNumber_of_Rooms() == rooms ) {
                                        filteredProperties.add(property);
                                    }
                                }
                            }


                        }

                    }


                }

            }


        } else{
            Log.w(SearchPropertyActivity.TAG, "Please fill in all fields");
            event.postValue(SearchPropertyActivityViewModel.Event.DisplaySearchError);
        }


        return  filteredProperties;
    }

}