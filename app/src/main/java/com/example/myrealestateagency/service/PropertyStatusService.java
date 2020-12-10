package com.example.myrealestateagency.service;


import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.bo.PropertyStatus;

import java.util.ArrayList;
import java.util.List;

final public class PropertyStatusService implements IPropertyStatusService {
    private final List<PropertyStatus> propertyStatus = new ArrayList<>() ;

    @Override
    public List<PropertyStatus> getStatus()
    {
        return propertyStatus;
    }

    @Override
    public void addStatus(PropertyStatus pStatus)
    {
        propertyStatus.add(pStatus);
    }
}
