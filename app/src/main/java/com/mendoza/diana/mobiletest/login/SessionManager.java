package com.mendoza.diana.mobiletest.login;

import android.content.Context;
import android.content.SharedPreferences;
import com.mendoza.diana.mobiletest.models.UserModel;

public class SessionManager {

    private static final String IS_LOGGED = "is_logged";
    private static final String PREFERENCES_FILE = "mobile_test_session";
    private static final String USERNAME = "username";
    private static final String TOKEN = "token";

    private SharedPreferences sessionData;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sessionData = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public boolean isLogin() {
        return sessionData.getBoolean(IS_LOGGED, false);
    }

    public String getToken(){
        return sessionData.getString(TOKEN, "");
    }

    public String getUsername(){
        return sessionData.getString(USERNAME, "");
    }

    void createSession(UserModel user) {
        editor = sessionData.edit();
        editor.putString(USERNAME, user.getUsername());
        editor.putString(TOKEN, user.getToken());
        editor.putBoolean(IS_LOGGED, true);
        editor.apply();
    }

    public void logoutSession() {
        editor = sessionData.edit();
        editor.clear();
        editor.apply();
    }
}
