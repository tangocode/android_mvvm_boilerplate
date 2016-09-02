package com.boiler_plate.mobile;

import android.util.Log;

import com.boiler_plate.mobile.di.modules.ApplicationModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.boiler_plate.mobile.di.components.ApplicationComponent;
import com.boiler_plate.mobile.di.components.DaggerApplicationComponent;

/**
 * Application Class
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class Application extends android.app.Application {
    private ApplicationComponent applicationComponent;
    private Tracker mTracker;

    @Override public void onCreate() {
        super.onCreate();
        Log.d("Application"," onCrate Application");
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public String getBaseURL() {
        return Constants.SERVICES_HOST;
    }

    /**
     * Gets the default {@link Tracker} for this {@link android.app.Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
