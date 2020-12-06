package com.example.myrealestateagency.service;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;

import java.util.ArrayList;
import java.util.List;

final public class AgentService implements IAgentService {

    private final List<Agent> agents = new ArrayList<>();

    @Override
    public List<Agent> getAgents()
    {
        return agents;
    }

    @Override
    public void addAgent(Agent agent)
    {
        agents.add(agent);
    }


}
