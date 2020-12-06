package com.example.myrealestateagency.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.bo.PropertyStatus;
import com.example.myrealestateagency.bo.PropertyType;
import com.example.myrealestateagency.dao.AppDao;

@Database(entities = {Agent.class,
                      Property.class,
                      PropertyStatus.class,
                      PropertyType.class}, version = 1)

public abstract class RealEstateAgencyDatabase extends RoomDatabase {
    public abstract AppDao appDao();
}
