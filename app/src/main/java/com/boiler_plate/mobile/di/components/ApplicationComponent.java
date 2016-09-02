package com.boiler_plate.mobile.di.components;

import android.content.Context;

import com.boiler_plate.mobile.di.modules.ApplicationModule;
import com.boiler_plate.mobile.managers.DummyManager;
import com.boiler_plate.mobile.managers.Network.NetworkApiClient;
import com.boiler_plate.mobile.managers.PreferenceHelper.EncryptionStrategy;
import com.boiler_plate.mobile.managers.PreferencesHelper;
import com.boiler_plate.mobile.managers.SessionManager;
import com.boiler_plate.mobile.managers.DocumentDownloader;

import javax.inject.Singleton;
import dagger.Component;
/**
 * A component whose lifetime is the life of the application.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  Context context();
  DummyManager dummyManager();
  PreferencesHelper preferencesHelper();
  EncryptionStrategy encryptionStrategy();
  NetworkApiClient NetworkApiClient();
  SessionManager sessionManager();
  DocumentDownloader documentDownloader();
}