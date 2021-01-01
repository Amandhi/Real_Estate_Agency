package com.example.myrealestateagency.bo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//This class is used in order to represent the type of a property(house, appartment, office etc.)
//This is an Entity
@Entity(tableName = "PropertyType")
public class PropertyType {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TypeID")
    public Integer TypeID;


    @ColumnInfo(name = "Type")
    public final String Type;

    public PropertyType(String Type) {
        TypeID=0;
        this.Type = Type;}



  /* @Override
    public String toString() { return Type; }*/
}
