package com.boiler_plate.mobile.managers.PreferenceHelper;

/**
 * Created by juanpablogarcia on 2/11/16.
 */
public interface EncryptionStrategy {
    String encryptString(String clearValue);
    String decryptString(String encryptedValue);
}
