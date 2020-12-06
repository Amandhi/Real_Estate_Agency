package com.example.myrealestateagency.service;

import com.example.myrealestateagency.bo.Property;

import java.util.List;

public interface IPropertyService {

    /**
     * Get all properties
     *
     * @return {@link List}
     */
    List<Property> getProperties();

    /**
     * Deletes a property
     *
     * @param property
     */
    void deleteProperty(Property property);

    /**
     * Add a property
     *
     * @param property
     */
    void addProperty(Property property);

    /**
     * Update a property
     *
     * @return {@link List}
     */
    //List<Property> updateProperty();
}
