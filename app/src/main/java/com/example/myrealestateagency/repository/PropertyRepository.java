package com.example.myrealestateagency.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.database.RealEstateAgencyDatabase;

import java.util.List;

public final class PropertyRepository {

    private static volatile PropertyRepository instance;


    public static PropertyRepository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (PropertyRepository.class)
            {
                if (instance == null)
                {
                    instance = new PropertyRepository(context);
                }
            }
        }

        return instance;
    }

    private final RealEstateAgencyDatabase agencyDatabase;

    private PropertyRepository(Context context)
    {
        agencyDatabase = Room.databaseBuilder(context, RealEstateAgencyDatabase.class, "property-database").allowMainThreadQueries().build();
    }

    public List<Property> getProperties()
    {
        return agencyDatabase.appDao().getProperties();
    }

    public void deleteProperty(Property property)
    {
        agencyDatabase.appDao().deleteProperty(property);
    }

    public void addProperty(Property property)
    {
        agencyDatabase.appDao().addProperty(property);
    }

    //method to update property
}
