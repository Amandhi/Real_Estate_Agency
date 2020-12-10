package com.example.myrealestateagency.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.bo.PropertyStatus;
import com.example.myrealestateagency.bo.PropertyType;
import com.example.myrealestateagency.service.IAgentService;
import com.example.myrealestateagency.service.IPropertyService;
import com.example.myrealestateagency.service.IPropertyStatusService;
import com.example.myrealestateagency.service.IPropertyTypeService;

import java.util.List;

@Dao
public interface AppDao extends IPropertyService, IAgentService, IPropertyStatusService, IPropertyTypeService {

    @Override
    @Query("Select * From Property inner join PropertyType on PropertyType.TypeID=Property.PropertyType")
   //@Query(("Select * From Property"))
    List<Property> getProperties();

    @Override
    @Delete
    void deleteProperty(Property property);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProperty(Property property);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAgent(Agent agent);


    /*@Override
    @Update
    void updateProperty(Property property);*/

    @Override
    @Query("SELECT * FROM Agent")
    List<Agent> getAgents();

    @Override
    @Query("SELECT * FROM PropertyStatus")
    List<PropertyStatus> getStatus();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStatus(PropertyStatus pStatus);


    @Override
    @Query("SELECT * FROM PropertyType")
    List<PropertyType> getType();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addType(PropertyType pType);



}
