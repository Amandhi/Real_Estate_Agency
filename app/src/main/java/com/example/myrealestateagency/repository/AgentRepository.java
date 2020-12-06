package com.example.myrealestateagency.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.database.RealEstateAgencyDatabase;

import java.util.List;

public final class AgentRepository {

    private static volatile AgentRepository instance;


    public static AgentRepository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (AgentRepository.class)
            {
                if (instance == null)
                {
                    instance = new AgentRepository(context);
                }
            }
        }

        return instance;
    }

    private final RealEstateAgencyDatabase agencyDatabase;

    private AgentRepository(Context context)
    {
        agencyDatabase = Room.databaseBuilder(context, RealEstateAgencyDatabase.class, "agency-database").allowMainThreadQueries().build();
    }

    public List<Agent> getAgents()
    {
        return agencyDatabase.appDao().getAgents();
    }

    public void addAgent(Agent agent)
    {
        agencyDatabase.appDao().addAgent(agent);
    }




}
