package edu.wmich.cs.weather;


import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {

    private static final String PREF_SEARCH = "search";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SEARCH, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_SEARCH, query).apply();
    }
}
