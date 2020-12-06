package com.example.myrealestateagency.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

public abstract class AppPreferences {

    private static final String AGENT_PREFERENCES_KEY = "agentPreferencesKey";

    public static void saveAgentSelection(@NonNull Context context, @NonNull String login)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putString(AppPreferences.AGENT_PREFERENCES_KEY, login);
        editor.apply();
    }

    @Nullable
    public static String getAgentSelection(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.AGENT_PREFERENCES_KEY, null);
    }

    /*public static void removeDataFromPref(Context context) {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.remove(AGENT_PREFERENCES_KEY);
        editor.apply();
    }*/

    //private constructor in order to avoid any instance creation
    private AppPreferences()
    {

    }
}
