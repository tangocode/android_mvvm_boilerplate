package com.boiler_plate.mobile;

import android.content.Context;

import com.boiler_plate.mobile.managers.DummyManager;
import com.boiler_plate.mobile.managers.Network.NetworkApiClient;
import com.boiler_plate.mobile.managers.PreferenceHelper.EncryptionStrategy;
import com.boiler_plate.mobile.managers.PreferencesHelper;
import com.boiler_plate.mobile.managers.SessionManager;
import com.boiler_plate.mobile.view.mvvmSample.DummyViewModel;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.observers.TestSubscriber;
import static org.mockito.Mockito.*;
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DummyUnitTest {
    @Mock
    Context fakeContext;
    @Mock
    EncryptionStrategy fakeEncryptionStrategy;
    @Mock
    NetworkApiClient networkApiClient;
    @Mock
    SessionManager sessionManager;
    PreferencesHelper preferencesHelper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        preferencesHelper = new PreferencesHelper(fakeContext, fakeEncryptionStrategy);
    }


    @Test
    public void observableEmitsData() {
        DummyManager dummyManager = Mockito.mock(DummyManager.class);
        when(dummyManager.dummyString()).thenReturn("Hello World");
        DummyViewModel dummyViewModel = new DummyViewModel(dummyManager, preferencesHelper, networkApiClient, sessionManager);

        Observable<String> obs = dummyViewModel.dummyObservable();
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        obs.subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
    }
}