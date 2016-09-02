package com.boiler_plate.mobile.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.boiler_plate.mobile.managers.PreferenceHelper.EncryptionStrategy;
import com.boiler_plate.mobile.managers.PreferenceHelper.SharedPrefEncryption;

/**
 * This class implements de functionalities to interact with the user preferences file
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class PreferencesHelper {

    private SharedPreferences mPref;
    private EncryptionStrategy encryptionStrategy;

    public static final String PREF_FILE_NAME = "android_pls_pref_file";



    public PreferencesHelper(Context context, EncryptionStrategy encryptionStrategy) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        this.encryptionStrategy = encryptionStrategy;
    }

    /**
     * This method gets a value from the user preferences file
     *
     * @author Juan Garcia
     * @return Expected value
     */
    public String getString(String key){
        return mPref.getString(key, "");
    }

    /**
     * This method upserts a boolean value in the preferences file
     *
     * @author Juan Garcia
     */
    public void putBoolean(String key, Boolean value){
        mPref.edit().putBoolean(key, value).apply();
    }

    /**
     * This method gets a boolean value from the user preferences file
     *
     * @author Juan Garcia
     * @return Expected value
     */
    public Boolean getBoolean(String key){
        return mPref.getBoolean(key, false);
    }

    /**
     * This method upserts a string value in the preferences file
     *
     * @author Juan Garcia
     */
    public void putString(String key, String value){
        mPref.edit().putString(key,value).apply();
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putEncryptedString(String key, String value) {
        try {
            String encryptedValue = encryptionStrategy.encryptString(value);
            this.putString(key,encryptedValue);
        } catch (SharedPrefEncryption.SecurePreferencesException exc) {
            Log.e("putEncryptedString",exc.getMessage());
        }
    }

    public String getDecryptedString(String key) {
        try {
            String encryptedValue = this.getString(key);
            String decryptedValue = encryptionStrategy.decryptString(encryptedValue);
            return decryptedValue;
        } catch (SharedPrefEncryption.SecurePreferencesException exc) {
            Log.e("putEncryptedString",exc.getMessage());
            return "";
        }
    }

    public enum PreferencesKeys {
        QUICK_BALANCE_ENABLED  ("quick_balance"),
        USER_ACCESED_SIGN_IN_FORM("user_sign_in_form");

        private final String prefkey;

        PreferencesKeys(String prefKey) {
            this.prefkey = prefKey;
        }

        public String getKeyString() {
            return  prefkey;
        }
    }

}
