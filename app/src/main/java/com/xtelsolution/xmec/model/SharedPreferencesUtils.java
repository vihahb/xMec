package com.xtelsolution.xmec.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.MyApplication;


public class SharedPreferencesUtils {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    static SharedPreferencesUtils instance = new SharedPreferencesUtils();

    public static SharedPreferencesUtils getInstance() {
        if (instance == null)
            instance = new SharedPreferencesUtils();
        return instance;

    }

    private SharedPreferencesUtils() {
        sharedPreferences = MyApplication.context.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    private void prepair() {
        editor = sharedPreferences.edit();
    }

    public void putLongValue(String name, long value) {
        if (editor == null)
            prepair();
        editor.putLong(name, value);
        editor.commit();
    }

    public void putFloatValue(String name,float value){
        if (editor == null)
            prepair();
        editor.putFloat(name, value);
        editor.commit();
    }
    public float getFloatValue(String name){
        return sharedPreferences.getFloat(name,-1);
    }

    public long getLongValue(String name) {
        return sharedPreferences.getLong(name, -1);
    }

    public void putStringValue(String name, String value) {
        if (editor == null)
            prepair();
        editor.putString(name, value);
        editor.commit();
    }

    public String getStringValue(String name) {
        return sharedPreferences.getString(name, null);
    }

    public void putIntValue(String name, int value) {
        if (editor == null)
            prepair();
        editor.putInt(name, value);
        editor.commit();
    }

    public int getIntValue(String name) {
        return sharedPreferences.getInt(name, -1);
    }

    public void putBooleanValue(String name, boolean value) {
        if (editor == null)
            prepair();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public boolean getBooleanValue(String name) {
        return sharedPreferences.getBoolean(name, false);
    }

    public void clearData() {
        if (editor == null)
            prepair();
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        if (editor == null)
            prepair();
        editor.remove(key);
        editor.commit();
    }

    public void saveUser(RESP_User user) {
        if (editor==null)
            prepair();
        editor.putString(Constant.USER_FULL_NAME, user.getFullname());
        editor.putInt(Constant.USER_GENDER, user.getGender());
        editor.putLong(Constant.USER_BIRTHDAY, user.getBirthday());
        editor.putString(Constant.USER_PHONE_NUMBER, user.getPhonenumber());
        editor.putString(Constant.USER_ADDRESS, user.getAddress());
        editor.putString(Constant.USER_AVATAR, user.getAvatar());
        editor.putFloat(Constant.USER_WEIGHT, (float) user.getWeight());
        editor.putFloat(Constant.USER_HEIGHT, (float) user.getHeight());
        editor.commit();
    }

    public void setLogined(){
        if (editor==null)
            prepair();
        editor.putBoolean(Constant.IS_LOGINED,true);
        editor.commit();
    }
    public void setLogout(){
        if (editor==null)
            prepair();
        editor.clear();
        editor.commit();
    }

    public boolean isLogined(){
        return sharedPreferences.getBoolean(Constant.IS_LOGINED,false);
    }

    public RESP_User getUser() {
        String fullname = sharedPreferences.getString(Constant.USER_FULL_NAME, "");
        int gender = sharedPreferences.getInt(Constant.USER_GENDER, 0);
        long birthday = sharedPreferences.getLong(Constant.USER_BIRTHDAY, 0);
        String numberphone = sharedPreferences.getString(Constant.USER_PHONE_NUMBER, "");
        String address = sharedPreferences.getString(Constant.USER_ADDRESS, "");
        String avatar = sharedPreferences.getString(Constant.USER_AVATAR, "");
        double weight = (double) sharedPreferences.getFloat(Constant.USER_WEIGHT, 0);
        double height =(double) sharedPreferences.getFloat(Constant.USER_HEIGHT, 0);
        return new RESP_User(fullname,gender,birthday,numberphone,address,avatar,weight,height);
    }
}
