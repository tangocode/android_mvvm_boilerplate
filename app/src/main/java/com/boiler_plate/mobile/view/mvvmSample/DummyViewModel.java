package com.boiler_plate.mobile.view.mvvmSample;

import android.util.Log;

import com.boiler_plate.mobile.managers.SessionManager;
import com.google.gson.Gson;
import com.boiler_plate.mobile.managers.DummyManager;
import com.boiler_plate.mobile.managers.Network.NetworkApiClient;
import com.boiler_plate.mobile.managers.Network.ApiDefinition;
import com.boiler_plate.mobile.managers.PreferencesHelper;

import java.util.Properties;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ViewModel that contains the implementations for the MVVM proof of concept
 *
 * @author Juan Garcia
 * @created 2016-01-26
 */
public class DummyViewModel {
    private final DummyManager testManager;
    private PreferencesHelper preferencesHelper;
    private Subscription testingSubscription;
    private NetworkApiClient networkApiClient;
    private SessionManager sessionManager;

    @Inject
    public DummyViewModel(DummyManager testManager, PreferencesHelper preferencesHelper, NetworkApiClient networkApiClient, SessionManager sessionManager) {
        this.testManager = testManager;
        this.preferencesHelper = preferencesHelper;
        this.networkApiClient = networkApiClient;
        this.sessionManager = sessionManager;
    }

    public Observable<String> dummyObservable() {
        Observable createdObservable = Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object subscriber) {
                final Subscriber mySubscriber = (Subscriber) subscriber;
                new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(3000);
                                if (!mySubscriber.isUnsubscribed()) {
                                    mySubscriber.onNext(testManager.dummyString());
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!mySubscriber.isUnsubscribed()) {
                            mySubscriber.onCompleted();
                        }
                    }
                }).start();
            }
        });
        return createdObservable;
    }

    public Boolean networkRequest() {
        testingSubscription = networkApiClient.makeObservableRequest(ApiDefinition.dummyApiRequest(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("DummyViewModel", "accessToken onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("DummyViewModel", "accessToken onError");
                    }

                    @Override
                    public void onNext(String jsonData) {
                        Log.d("DummyViewModel", "accessToken onNext");
                    }
                });

        return true;
    }

    public void unsubscribe() {
        Log.d("DummyViewModel", "unsubscribe...");
        testingSubscription.unsubscribe();
    }

}
