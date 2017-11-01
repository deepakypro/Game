package com.thelosers.game.SharedPreferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by deepak on 27/09/17.
 */

public class PlatformSharedPref extends Application {
    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "IsChooseClass";

    // All Shared Preferences Keys
    private static final String IS_DETAILS = "IsChooseIn";




    public static final String KEY_NAME = "name";



    public PlatformSharedPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createSession(String name) {
        // Storing login value as TRUE
        editor.putBoolean(IS_DETAILS, true);


        // Storing email in pref
        editor.putString(KEY_NAME, name);

        // Storing name in pref


        // Storing email in pref


        // Storing name in pref


        // Storing email in pref


        editor.commit();

    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity

            return false;

        }

        return true;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_DETAILS, false);
    }
}
