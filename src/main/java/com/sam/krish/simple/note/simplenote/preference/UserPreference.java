package com.sam.krish.simple.note.simplenote.preference;

import java.util.prefs.Preferences;

public class UserPreference {

    private final Preferences preferences;

    public UserPreference(){
        preferences = Preferences.userNodeForPackage(UserPreference.class);
    }

    public void setUsername(String username){
        preferences.put(UserPref.USERNAME.name(), username);
    }

    public String getUserName(){
        return preferences.get(UserPref.USERNAME.name(), "");
    }

    public void setPassword(String password){
        preferences.put(UserPref.PASSWORD.name(), password);
    }

    public String getPassword(){
        return preferences.get(UserPref.PASSWORD.name(), "");
    }

    public void setChildHoodName(String name){
        preferences.put(UserPref.CHILDHOOD_NAME.name(), name);
    }

    public String getChildHoodName(){
        return preferences.get(UserPref.CHILDHOOD_NAME.name(), "");
    }

}
