package com.boiler_plate.mobile.managers.PreferenceHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

/**
 * This Class represents the Encryption strategy based on RAS. This implementation creates a private/public
 * key pai that is stored in the Android KeyStore.
 * This strategy is created in order to support API>=18 phones.
 *
 * @author      Juan Garcia
 * @created 	2016-02-11
 */
public class KeyStoreEncryption implements EncryptionStrategy {

    static final String TAG = "KeyStoreEncryption";
    static final String CIPHER_TYPE = "RSA/ECB/PKCS1Padding";
    static final String CIPHER_PROVIDER = "AndroidOpenSSL";

    private String alias = "pls_key_alias";
    private Context context;

    KeyStore keyStore;

    public KeyStoreEncryption(Context context) throws SharedPrefEncryption.SecurePreferencesException {
        this.context = context;
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            this.createNewKeys();
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage());
            throw new SharedPrefEncryption.SecurePreferencesException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)

    public void createNewKeys() throws SharedPrefEncryption.SecurePreferencesException {
        try {
            // Create new key if needed
            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=Pls App, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new SharedPrefEncryption.SecurePreferencesException(e);
        }
    }


    @Override
    public String encryptString(String clearValue) throws SharedPrefEncryption.SecurePreferencesException {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            Cipher inCipher = Cipher.getInstance(CIPHER_TYPE, CIPHER_PROVIDER);
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inCipher);
            cipherOutputStream.write(clearValue.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] vals = outputStream.toByteArray();
            String encryptedValue = Base64.encodeToString(vals, Base64.DEFAULT);
            return encryptedValue;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new SharedPrefEncryption.SecurePreferencesException(e);
        }
    }

    @Override
    public String decryptString(String encryptedValue) throws SharedPrefEncryption.SecurePreferencesException {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);

            Cipher output = Cipher.getInstance(CIPHER_TYPE);
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(encryptedValue, Base64.DEFAULT)), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            String finalText = new String(bytes, 0, bytes.length, "UTF-8");
            return finalText;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new SharedPrefEncryption.SecurePreferencesException(e);
        }
    }
}
