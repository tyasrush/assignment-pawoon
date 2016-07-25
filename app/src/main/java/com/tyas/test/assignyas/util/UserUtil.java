package com.tyas.test.assignyas.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by tyasrus on 04/06/16.
 *
 * UserUtil : utilities for user, saving some parameter which app needed
 */
public class UserUtil {

    public final String USER_ID = "id";
    public final String USER_FIRST_NAME = "first_name";
    public final String USER_IMAGE = "image";
    public final String LOGIN_STATUS = "login_status";
    public final String CREDIT_CARD_SET = "credit_card";
    public final String TOKEN = "token";
    public final String PROVIDER = "provider";

    private Context context;
    private SharedPreferences sharedPreferences;

    public UserUtil(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserCredential(HashMap<String, String> credential) throws NullPointerException {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(USER_ID, credential.get(USER_ID)).apply();
            sharedPreferences.edit().putString(USER_FIRST_NAME, credential.get(USER_FIRST_NAME)).apply();
            sharedPreferences.edit().putString(USER_IMAGE, credential.get(USER_IMAGE) != null ? credential.get(USER_IMAGE) : "").apply();
            sharedPreferences.edit().putBoolean(LOGIN_STATUS, true).apply();
        }
    }

    public void setToken(String token, Provider provider) {
        sharedPreferences.edit().putString(TOKEN, token).apply();
        sharedPreferences.edit().putString(PROVIDER, provider.name()).apply();
    }

    public String getTokenUser() {
        return sharedPreferences.getString(TOKEN, null);
    }

    public String getProvider() {
        return sharedPreferences.getString(PROVIDER, null);
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void logout() {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(USER_ID, null).apply();
            sharedPreferences.edit().putString(USER_FIRST_NAME, null).apply();
            sharedPreferences.edit().putString(USER_IMAGE, null).apply();
            sharedPreferences.edit().putBoolean(LOGIN_STATUS, true).apply();
            sharedPreferences.edit().putString(TOKEN, null).apply();
        }
    }

    public enum Provider {
        FACEBOOK, GOOGLE;
    }
}
