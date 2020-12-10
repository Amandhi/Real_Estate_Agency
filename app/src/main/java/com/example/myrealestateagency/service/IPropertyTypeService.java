package com.example.myrealestateagency.service;



import com.example.myrealestateagency.bo.PropertyType;

import java.util.List;

public interface IPropertyTypeService {

    /**
     * Get type of property
     *
     */
    List<PropertyType> getType();

    /**
     * Add a property status
     *
     * @param pType
     */
    void addType(PropertyType pType);
}
