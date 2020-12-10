package com.example.myrealestateagency.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//This class is used in order to represent the status of a property(sold or not)
//This is an Entity
@Entity(tableName = "PropertyStatus")
public class PropertyStatus {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "StatusID")
    public int StatusID;


    @NonNull
    @ColumnInfo(name = "Status")
    public final String Status;


    public PropertyStatus(@NonNull String Status) {
        StatusID=0;
        this.Status = Status;}

    @Override
    public String toString() {
        return Status;
    }


}
