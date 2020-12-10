package com.example.myrealestateagency.service;


import com.example.myrealestateagency.bo.PropertyStatus;
import com.example.myrealestateagency.bo.PropertyType;

import java.util.ArrayList;
import java.util.List;

final public class PropertyTypeService implements IPropertyTypeService {

    private final List<PropertyType> propertyType = new ArrayList<>() ;

    @Override
    public List<PropertyType> getType()
    {
        return propertyType;
    }

    @Override
    public void addType(PropertyType pType)
    {
        propertyType.add(pType);
    }


}
