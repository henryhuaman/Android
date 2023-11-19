package com.example.homecourseandroid.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private static final String KEY_USER_ID = "user_id";

    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }
    public void loginUser(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        // Otros campos relacionados con la sesión
        editor.apply();
    }

    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        // Limpiar otros campos relacionados con la sesión
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getString(KEY_USER_ID, null) != null;
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

}
