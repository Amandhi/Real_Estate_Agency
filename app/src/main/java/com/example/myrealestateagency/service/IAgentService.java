package com.example.myrealestateagency.service;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;

import java.util.List;

public interface IAgentService {

    /**
     * Get all agent
     *
     * @return {@link List}
     */
    List<Agent> getAgents();

    /**
     * Add a property
     *
     *
     *
     */
    void addAgent(Agent agent);
}
