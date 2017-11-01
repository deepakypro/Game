package com.thelosers.game.SharedPreferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by deepak on 24/09/17.
 */

public class sharedPref extends Application {
    // Shared Preferences
    SharedPreferences pref;
    //public boolean log=false;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "IsDataClass";

    // All Shared Preferences Keys
    private static final String IS_DETAILS = "IsDataIn";

    // User name (make variable public to access from outside)


    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    //  public static final String KEY_LOCK = "lock";
    // User name (make variable public to access from outside)
    public static final String KEY_IMAGE_URL = "image";

    //  public static final String KEY_LOCK = "lock";


    // Constructor
    public sharedPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createSession(String name, String image, String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_DETAILS, true);


        // Storing email in pref
        editor.putString(KEY_NAME, name);

        // Storing name in pref


        // Storing email in pref
        editor.putString(KEY_IMAGE_URL, image);

        editor.putString(KEY_EMAIL, email);

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
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_IMAGE_URL, pref.getString(KEY_IMAGE_URL, null));
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
