package com.boiler_plate.mobile.managers.Network;

import android.util.Log;

import com.boiler_plate.mobile.managers.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by juanpablogarcia on 3/24/16.
 */
public class NetworkApiClient {
    private String BASE_URL;
    private String TAG = "NetworkApiClient";
    private SessionManager sessionManager;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkApiClient(String baseURL, SessionManager sessionManager) {
        this.BASE_URL = baseURL;
        this.sessionManager = sessionManager;
    }

    public Observable<String> makeObservableRequest(final NetworkApiDefinition networkApiDefinition) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Call call = client.newCall(getRequest(networkApiDefinition));
                    subscriber.add(new MainThreadSubscription() {
                        @Override
                        protected void onUnsubscribe() {
                            Log.d(TAG, "Unsubscribed..");
                            call.cancel();
                        }
                    });
                    Response response = call.execute();
                    //TODO - Map Exceptions with Http error codes and Internet connection errors.
                    if (!response.isSuccessful()) {
                        subscriber.onError(new Exception("Network API request error"));
                    } else {
                        String jsonData = response.body().string();
                        Log.d(TAG, "Response: " + jsonData);
                        subscriber.onNext(jsonData);
                        subscriber.onCompleted();
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Request getRequest(NetworkApiDefinition networkApiDefinition) {
        String finalURL = BASE_URL + networkApiDefinition.getServicePath();
        Log.d(TAG, "URL: " + finalURL);
        Request.Builder request = new Request.Builder().url(finalURL);
        if (networkApiDefinition.getServiceSecurity().equals(ServiceSecurity.AUTHORIZATION)) {
            request.header("Content-Type", "application/json");
            request.addHeader("Authorization", sessionManager.OAUTH_TOKEN);
        }
        if (networkApiDefinition.getMethod().equals(HttpMethod.POST)) {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(networkApiDefinition.getBody());
            request.post(RequestBody.create(JSON, json));
        }
        return request.build();
    }
}
