package com.example.myrealestateagency.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.myrealestateagency.bo.PropertyStatus;
import com.example.myrealestateagency.bo.PropertyType;
import com.example.myrealestateagency.database.RealEstateAgencyDatabase;

import java.util.List;

public class PropertyTypeRepository {

    private static volatile PropertyTypeRepository instance;


    public static PropertyTypeRepository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (AgentRepository.class)
            {
                if (instance == null)
                {
                    instance = new PropertyTypeRepository(context);
                }
            }
        }

        return instance;
    }

    private final RealEstateAgencyDatabase agencyDatabase;

    private PropertyTypeRepository(Context context)
    {
        agencyDatabase = Room.databaseBuilder(context, RealEstateAgencyDatabase.class, "agency-database").allowMainThreadQueries().build();
    }

    public List<PropertyType> getType()
    {
        return agencyDatabase.appDao().getType();
    }

    public void addType(PropertyType pType)
    {
        agencyDatabase.appDao().addType(pType);
    }




}
