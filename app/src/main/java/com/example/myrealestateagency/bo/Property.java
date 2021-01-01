package com.example.myrealestateagency.bo;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//This class is used in order to represent a property
//This is an Entity
@Entity(tableName = "Property"/*,
        foreignKeys={
        @ForeignKey(entity = Agent.class,
                   parentColumns = "AgentID",
                    childColumns = "Agent_in_charge"),
                @ForeignKey(entity = PropertyType.class,
                     parentColumns = "TypeID",
                     childColumns = "PropertyType"),

                @ForeignKey(entity= PropertyStatus.class,
                            parentColumns = "StatusID",
                            childColumns = "PropertyStatus")

                }*/)

public class Property implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PropertyID")
    public int PropertyID;


    @NonNull
    @ColumnInfo(name = "PropertyType")
    public String PropertyType;

    @NonNull
    public String getPropertyType() {
        return PropertyType;
    }



    @NonNull
    @ColumnInfo(name = "Price")
    public Integer Price;

    @NonNull
    public Integer getPrice() {
        return Price;
    }

    @NonNull
    @ColumnInfo(name = "PropertySurface")
    public Integer PropertySurface;

    @NonNull
    public Integer getPropertySurface() {
        return PropertySurface;
    }

    @NonNull
    public Integer getNumber_of_Rooms() {
        return Number_of_Rooms;
    }

    @NonNull
    @ColumnInfo(name = "Number_of_Rooms")
    public Integer Number_of_Rooms;

    @NonNull
    @ColumnInfo(name = "Address")
    public String Address;

    @NonNull
    @ColumnInfo(name = "Description")
    public String Description;


    @ColumnInfo(name = "PropertyStatus")
    public String PropertyStatus;

    public String getPropertyStatus() {
        return PropertyStatus;
    }

    @NonNull
    @ColumnInfo(name = "Agent_in_charge")
    public String Agent_in_charge;



    /*public void setAgent_in_charge(@NonNull String agent_in_charge) {
        Agent_in_charge = agent_in_charge;
    }
*/
    public Property(String PropertyType, Integer Price, Integer PropertySurface, Integer Number_of_Rooms, @NonNull String Address, String Description, String PropertyStatus, String Agent_in_charge) {
        PropertyID=0;
        this.PropertyType=PropertyType;
        this.Price=Price;
        this.PropertySurface = PropertySurface;
        this.Number_of_Rooms = Number_of_Rooms;
        this.Address = Address;
        this.Description=Description;
        this.PropertyStatus=PropertyStatus;
        this.Agent_in_charge = Agent_in_charge;
    }

}


