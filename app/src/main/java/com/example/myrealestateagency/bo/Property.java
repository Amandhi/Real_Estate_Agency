package com.example.myrealestateagency.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//This class is used in order to represent a property
//This is an Entity
@Entity(tableName = "Property",
        foreignKeys={
       /* @ForeignKey(entity = Agent.class,
                   parentColumns = "AgentID",
                    childColumns = "Agent_in_charge"),*/
                @ForeignKey(entity = PropertyType.class,
                     parentColumns = "TypeID",
                     childColumns = "PropertyType"),

                /*@ForeignKey(entity= PropertyStatus.class,
                            parentColumns = "StatusID",
                            childColumns = "PropertyStatus")*/

        })

public class Property implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PropertyID")
    public int PropertyID;


    @ColumnInfo(name = "PropertyType")
    public Integer PropertyType;

    /*@NonNull
    @ColumnInfo(name = "Price")
    public float Price;*/

    @NonNull
    @ColumnInfo(name = "PropertySurface")
    public int PropertySurface;

    @NonNull
    @ColumnInfo(name = "Number_of_Rooms")
    public int Number_of_Rooms;

    @NonNull
    @ColumnInfo(name = "Address")
    public String Address;

    /*@NonNull
    @ColumnInfo(name = "Description")
    public String Description;*/


    /*@ColumnInfo(name = "PropertyStatus")
    public Integer PropertyStatus;

    @NonNull
    @ColumnInfo(name = "Agent_in_charge")
    public String Agent_in_charge;*/



    /*public void setAgent_in_charge(@NonNull String agent_in_charge) {
        Agent_in_charge = agent_in_charge;
    }
*/
    public Property(Integer PropertyType, int PropertySurface, int Number_of_Rooms, @NonNull String Address) {
        PropertyID=0;
        this.PropertyType=PropertyType;
        this.PropertySurface = PropertySurface;
        this.Number_of_Rooms = Number_of_Rooms;
        this.Address = Address;
    }

}
