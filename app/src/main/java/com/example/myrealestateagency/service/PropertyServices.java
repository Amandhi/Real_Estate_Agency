package com.example.myrealestateagency.service;

import com.example.myrealestateagency.bo.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final public class PropertyServices implements IPropertyService {

    private final List<Property> properties = new ArrayList<>();

    @Override
    public List<Property> getProperties()
    {
        return properties;
    }

    @Override
    public void deleteProperty(Property property)
    {
        properties.remove(property);
    }

    @Override
    public void addProperty(Property property)
    {
        properties.add(property);
    }

    /*
    @Override
    public List<Property> updateProperty()
    {
        //implementer
    }*/
}
