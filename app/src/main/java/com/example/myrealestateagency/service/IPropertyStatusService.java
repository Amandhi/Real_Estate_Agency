package com.example.myrealestateagency.service;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.PropertyStatus;

import java.util.List;

public interface IPropertyStatusService {


    /**
     * Get status of property
     *
     */
    List<PropertyStatus> getStatus();

    /**
     * Add a property status
     *
     * @param pStatus
     */
    void addStatus(PropertyStatus pStatus);
}
