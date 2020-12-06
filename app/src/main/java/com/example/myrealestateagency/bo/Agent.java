package com.example.myrealestateagency.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//This class is used in order to represent a real estate agent
//This is an Entity
@Entity(tableName = "Agent")
public class Agent implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AgentID")
    public int AgentID;

    @NonNull
    @ColumnInfo(name = "Agent_Name")
    public final String AgentName;


    public Agent(@NonNull String AgentName) {
        AgentID=0;
        this.AgentName = AgentName;
    }






}
