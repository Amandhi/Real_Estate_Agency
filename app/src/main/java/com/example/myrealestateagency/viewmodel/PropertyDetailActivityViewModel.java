package com.example.myrealestateagency.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.view.PropertyDetailActivity;

public final class PropertyDetailActivityViewModel extends ViewModel {

    public MutableLiveData<Property> property = new MutableLiveData<>();

    public PropertyDetailActivityViewModel(SavedStateHandle savedStateHandle) {
        final Property propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }
}
