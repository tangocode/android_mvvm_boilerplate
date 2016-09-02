package com.boiler_plate.mobile.managers.PreferenceHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This Class represents the Encryption strategy based on AES. The private key generated to encrypt the strings values
 * is stored in a SharedPreferences files under Context.MODE_PRIVATE. This may be a security flaw for rooted phones
 * where the attacker can have access to the private key an then decrypt the encrypted strings stored in this file.
 *
 * This strategy is created in order to support API<18 phones.
 *
 * @author      Juan Garcia
 * @created 	2016-02-11
 */
public class SharedPrefEncryption implements EncryptionStrategy {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
    private static final String CHARSET = "UTF-8";
    private SharedPreferences mPref;
    public static final String PREF_FILE_NAME = "android_log_pef";

    private final boolean encryptKeys;
    private final Cipher writer;
    private final Cipher reader;
    private final Cipher keyWriter;
    private final SharedPreferences preferences;
    private static SharedPrefEncryption instance = null;


    public SharedPrefEncryption(Context context, String preferenceName, String secureKey, boolean encryptKeys) throws SecurePreferencesException {
        try {
            this.writer = Cipher.getInstance(TRANSFORMATION);
            this.reader = Cipher.getInstance(TRANSFORMATION);
            this.keyWriter = Cipher.getInstance(KEY_TRANSFORMATION);
            this.mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

            initCiphers(secureKey);

            this.preferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            this.encryptKeys = encryptKeys;


        } catch (GeneralSecurityException e) {
            throw new SecurePreferencesException(e);
        } catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        }
    }

    protected void initCiphers(String secureKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        IvParameterSpec ivSpec = getIv();
        SecretKey secretKey = getSecretKey(secureKey);

        writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    public static SharedPrefEncryption getInstance(Context contxt, String prefName) {
        if (instance == null) {
            instance = new SharedPrefEncryption(contxt, prefName, prefName + "Key", true);
        }
        return instance;
    }

    protected IvParameterSpec getIv() {
        byte[] iv = new byte[writer.getBlockSize()];
        System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0,
                iv, 0, writer.getBlockSize());
        return new IvParameterSpec(iv);
    }

    protected SecretKey getSecretKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String stringKey = mPref.getString(key, "");
        if (stringKey.equals("")) {
            byte[] keyBytes = createKeyBytes(key);
            String keytoStore = Base64.encodeToString(keyBytes, Base64.DEFAULT);
            mPref.edit().putString(key, keytoStore).apply();
            return new SecretKeySpec(keyBytes, TRANSFORMATION);
        } else {
            byte[] encodedKey = Base64.decode(stringKey, Base64.DEFAULT);
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
            return originalKey;
        }
    }

    protected byte[] createKeyBytes(String key)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest
                .getInstance(SECRET_KEY_HASH_TRANSFORMATION);
        md.reset();
        byte[] keyBytes = md.digest(key.getBytes(CHARSET));
        return keyBytes;
    }

    protected String encrypt(String value, Cipher writer) throws SecurePreferencesException {
        byte[] secureValue;
        try {
            secureValue = convert(writer, value.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        } catch (NullPointerException e) {
            throw new SecurePreferencesException(e);
        }
        String secureValueEncoded = Base64.encodeToString(secureValue,
                Base64.NO_WRAP);
        return secureValueEncoded;
    }

    private static byte[] convert(Cipher cipher, byte[] bs) throws SecurePreferencesException {
        try {
            return cipher.doFinal(bs);
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
    }

    @Override
    public String encryptString(String clearValue) throws SecurePreferencesException {
        try {
            return encrypt(clearValue, writer);
        } catch (SecurePreferencesException e) {
            throw new SecurePreferencesException(e);
        }
    }

    @Override
    public String decryptString(String encryptedValue) throws SecurePreferencesException {
        byte[] securedValue = Base64.decode(encryptedValue, Base64.NO_WRAP);
        byte[] value = convert(reader, securedValue);
        try {
            return new String(value, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        }
    }


    public static class SecurePreferencesException extends RuntimeException {

        public SecurePreferencesException(Throwable e) {
            super(e);
        }

    }

}
