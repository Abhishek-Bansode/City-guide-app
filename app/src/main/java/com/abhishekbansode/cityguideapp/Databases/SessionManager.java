package com.abhishekbansode.cityguideapp.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    // Variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    // Session Names
    public static final String SESSION_USER_SESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    /* User Session Variables */
    private static final String IS_LOGIN = "IsLogIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    /* Remember-Me Session Variables */
    public static final String IS_REMEMBER = "IsRememberMe";
    public static final String KEY_SESSION_PHONENUMBER = "phoneNumber";
    public static final String KEY_SESSION_PASSWORD = "password";

    // Constructor
    public SessionManager(Context _context, String sessionName) {
        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    /* User-login Session Functions */
    public void createLoginSession(String fullName, String username, String email, String phoneNo, String password, String age, String gender) {
      editor.putBoolean(IS_LOGIN, true);

      editor.putString(KEY_FULLNAME, fullName);
      editor.putString(KEY_USERNAME, username);
      editor.putString(KEY_EMAIL, email);
      editor.putString(KEY_PHONENUMBER, phoneNo);
      editor.putString(KEY_PASSWORD, password);
      editor.putString(KEY_DATE, age);
      editor.putString(KEY_GENDER, gender);

      editor.commit();
    }

    public HashMap<String, String> getUserDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;
    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    /* Remember-Me Session Functions */
    public void createRememberMeSession(String phoneNo, String password) {
        editor.putBoolean(IS_REMEMBER, true);

        editor.putString(KEY_SESSION_PHONENUMBER, phoneNo);
        editor.putString(KEY_SESSION_PASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_SESSION_PHONENUMBER, userSession.getString(KEY_SESSION_PHONENUMBER, null));
        userData.put(KEY_SESSION_PASSWORD, userSession.getString(KEY_SESSION_PASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe() {
        if (userSession.getBoolean(IS_REMEMBER, false)) {
            return true;
        } else {
            return false;
        }
    }

}
