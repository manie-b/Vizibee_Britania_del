package com.mapolbs.vizibeebritannia.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by venkatesan on 3/6/2017.
 */
public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "Insurancepref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";


    public static final String IP_CONNECTIONSTRING = "connectionstring";
    public static final String KEY_USERID = "loggedUserId";
    public static final String KEY_DATETIME = "loggedDatetime";
    public static final String KEY_ISSUPERVISOR = "isSupervisor";
    public static final String KEY_SUPERVISORID = "supervisorId";
    public static final String KEY_SUPERVISORNAME = "supervisorName";
    public static final String KEY_MERCHANDISERNAME = "merchandisersname";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";



    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create ipaddress session
    public void createusersession(String username, String password){
        // Storing login value as TRUE
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }

    //Create ipaddress session
    public void createIpSession(String ipaddress){
        // Storing login value as TRUE
        editor.putString(IP_CONNECTIONSTRING, ipaddress);

        // commit changes
        editor.commit();
    }

    //Create login session
    public void createUserLoginSession(String loggedUserId, String loggedDatetime, String isSupervisor, String supervisorId, String supervisorName, String merchandisersname){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERID, loggedUserId);
        editor.putString(KEY_DATETIME, loggedDatetime);
        editor.putString(KEY_ISSUPERVISOR, isSupervisor);
        editor.putString(KEY_SUPERVISORID, supervisorId);
        editor.putString(KEY_SUPERVISORNAME, supervisorName);
        editor.putString(KEY_MERCHANDISERNAME, merchandisersname);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getipDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> ipdetails = new HashMap<String, String>();

        // user name
        ipdetails.put(IP_CONNECTIONSTRING, pref.getString(IP_CONNECTIONSTRING, null));

        // return user
        return ipdetails;
    }

    public HashMap<String, String> getusernameandpassword(){

        //Use hashmap to store user credentials
        HashMap<String, String> usernameandpassword = new HashMap<String, String>();

        // user name
        usernameandpassword.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        usernameandpassword.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // return user
        return usernameandpassword;
    }



    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
