package com.boiler_plate.mobile.di.modules;

import com.boiler_plate.mobile.managers.SessionManager;
import com.boiler_plate.mobile.di.PerActivity;
import com.boiler_plate.mobile.managers.DummyManager;
import com.boiler_plate.mobile.managers.Network.NetworkApiClient;
import com.boiler_plate.mobile.managers.PreferencesHelper;
import com.boiler_plate.mobile.view.mvvmSample.DummyViewModel;
import dagger.Module;
import dagger.Provides;
/**
 * Dummy module that provides objects which will live during the activity that initialized the component
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@Module
public class DummyTestingModule {
    public DummyTestingModule() {
    }

    @Provides
    @PerActivity
    DummyViewModel provideDummyViewModel(DummyManager dummyManager, PreferencesHelper preferencesHelper, NetworkApiClient networkApiClient, SessionManager sessionManager) {
        return new DummyViewModel(dummyManager, preferencesHelper, networkApiClient, sessionManager);
    }
}
