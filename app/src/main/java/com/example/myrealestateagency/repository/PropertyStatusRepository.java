package com.example.myrealestateagency.repository;

import android.content.Context;

import androidx.room.Room;


import com.example.myrealestateagency.bo.PropertyStatus;
import com.example.myrealestateagency.database.RealEstateAgencyDatabase;

import java.util.List;

public class PropertyStatusRepository {


    private static volatile PropertyStatusRepository instance;


    public static PropertyStatusRepository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (AgentRepository.class)
            {
                if (instance == null)
                {
                    instance = new PropertyStatusRepository(context);
                }
            }
        }

        return instance;
    }

    private final RealEstateAgencyDatabase agencyDatabase;

    private PropertyStatusRepository(Context context)
    {
        agencyDatabase = Room.databaseBuilder(context, RealEstateAgencyDatabase.class, "agency-database").allowMainThreadQueries().build();
    }

    public List<PropertyStatus> getStatus()
    {
        return agencyDatabase.appDao().getStatus();
    }

    public void addStatus(PropertyStatus pStatus)
    {
        agencyDatabase.appDao().addStatus(pStatus);
    }





}
