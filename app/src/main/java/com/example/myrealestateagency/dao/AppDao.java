package com.example.myrealestateagency.dao;

import android.widget.RadioButton;

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

    //Display registered properties
    @Override
    //@Query("Select Type, Address, PropertySurface, Number_of_Rooms, PropertyID From Property join PropertyType on PropertyType.TypeID=Property.PropertyType")
   @Query(("Select * From Property"))
    List<Property> getProperties();

    //Delete property
    @Override
    @Delete
    void deleteProperty(Property property);

    //Add properties
    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProperty(Property property);

    //Create an agent
    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAgent(Agent agent);

    //Display registered agents
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

    //Update registered property
    @Query("UPDATE Property SET PropertyType = :updatedType, Price = :updatedPrice, PropertySurface = :updatedSurface, Number_of_Rooms = :updatedRooms, Address = :updatedAddress, Description = :updatedDescription, PropertyStatus = :updatedStatus, Agent_in_charge = :updatedAgentInCharge WHERE PropertyID = :propertyID")
    void updateProperty(int propertyID, String updatedType, float updatedPrice, int updatedSurface, int updatedRooms, String updatedAddress, String updatedDescription, String updatedStatus, String updatedAgentInCharge);


}
