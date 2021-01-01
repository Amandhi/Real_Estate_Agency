package com.example.myrealestateagency.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.myrealestateagency.bo.Property;

public abstract class AppPreferences {

    private static final String AGENT_PREFERENCES_KEY = "agentPreferencesKey";
    private static final String PROPERTY_PREFERENCES_KEY = "propertyPreferencesKey";
    private static final String PROPERTY_CREATION_PREFERENCES_KEY = "propertyCreationDatePreferencesKey";
    private static final String PROPERTY_UPDATE_PREFERENCES_KEY = "propertyUpdateDatePreferencesKey";

    //Save an agent in preferences
    public static void saveAgentSelection(@NonNull Context context, @NonNull String agentName)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(AppPreferences.AGENT_PREFERENCES_KEY, agentName);
        editor.apply();
    }

    //Retrieve saved agent from preferences
    @Nullable
    public static String getAgentSelection(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.AGENT_PREFERENCES_KEY, null);
    }

    //Remove saved agent from preferences
    public static void removeFromPreferences(Context context) {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.remove(AGENT_PREFERENCES_KEY);
        editor.apply();
    }

    /*
    //Save a property in preferences
    public static void savePropertySelectionType(@NonNull Context context, String propertyType)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(AppPreferences.PROPERTY_PREFERENCES_KEY, propertyType);
        editor.apply();
    }


    //Retrieve saved property in preferences
    @Nullable
    public static String getPropertySelection(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.PROPERTY_PREFERENCES_KEY, null);
    }

    //Remove saved property from preferences
    public static void removePropertyFromPreferences(Context context) {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.remove(PROPERTY_PREFERENCES_KEY);
        editor.apply();
    }

     */

    //Save a creation date in preferences
    public static void saveCreationDateTime(@NonNull Context context, String dateTime)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(AppPreferences.PROPERTY_CREATION_PREFERENCES_KEY, dateTime);
        editor.apply();
    }


    //Retrieve saved creation date from preferences
    @Nullable
    public static String getCreationDateTime(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.PROPERTY_CREATION_PREFERENCES_KEY, null);
    }

    //Save a updation date in preferences
    public static void saveUpdateDateTime(@NonNull Context context, String dateTime)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(AppPreferences.PROPERTY_UPDATE_PREFERENCES_KEY, dateTime);
        editor.apply();
    }


    //Retrieve updation date from preferences
    @Nullable
    public static String getUpdateDateTime(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.PROPERTY_UPDATE_PREFERENCES_KEY, null);
    }

    //Remove updation date from preferences
    public static void removeUpdateInfoFromPreferences(Context context) {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.remove(PROPERTY_UPDATE_PREFERENCES_KEY);
        editor.apply();
    }

    //private constructor in order to avoid any instance creation
    private AppPreferences()
    {

    }
}
