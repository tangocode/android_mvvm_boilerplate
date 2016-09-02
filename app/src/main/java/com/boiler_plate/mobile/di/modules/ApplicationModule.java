package com.boiler_plate.mobile.di.modules;

import android.content.Context;
import android.os.Build;

import com.boiler_plate.mobile.managers.SessionManager;
import com.boiler_plate.mobile.Application;
import com.boiler_plate.mobile.managers.DocumentDownloader;
import com.boiler_plate.mobile.managers.DummyManager;
import com.boiler_plate.mobile.managers.Network.NetworkApiClient;
import com.boiler_plate.mobile.managers.PreferenceHelper.EncryptionStrategy;
import com.boiler_plate.mobile.managers.PreferenceHelper.KeyStoreEncryption;
import com.boiler_plate.mobile.managers.PreferenceHelper.SharedPrefEncryption;
import com.boiler_plate.mobile.managers.PreferencesHelper;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
/**
 * Dagger module that provides objects which will live during the application lifecycle.
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@Module
public class ApplicationModule {
  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  DummyManager provideTestManager() {
    return new DummyManager();
  }

  @Provides
  @Singleton
  EncryptionStrategy provideEncryptionStrategy(Context context) {
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR2){
      return new KeyStoreEncryption(context);
    } else{
      return SharedPrefEncryption.getInstance(context, "plsEnc_Prefs");
    }
  }

  @Provides
  @Singleton
  PreferencesHelper providesPreferencesHelper(Context context, EncryptionStrategy encryptionStrategy) {
    return new PreferencesHelper(context, encryptionStrategy);
  }

  @Provides
  @Singleton
  SessionManager providesSessionManager() {
    return new SessionManager();
  }

  @Provides
  @Singleton
  NetworkApiClient providesNetworkApiClient(Context context, SessionManager sessionManager) {
    return new NetworkApiClient(((Application)context).getBaseURL(), sessionManager);
  }


  @Provides
  @Singleton
  DocumentDownloader providesDocumentDownloader() {
    return new DocumentDownloader();
  }

}
