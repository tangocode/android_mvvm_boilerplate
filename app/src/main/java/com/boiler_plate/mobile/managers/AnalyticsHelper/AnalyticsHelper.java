package com.boiler_plate.mobile.managers.AnalyticsHelper;

import com.boiler_plate.mobile.Application;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Helper to report analytics data
 *
 * @author      Daniel Coellar
 * @created 	2016-02-16
 */
public class AnalyticsHelper {

    private Tracker mTracker;

    /**
     * Constructor takes the current application, should be executed once
     *
     * @author      Daniel Coellar
     * @created 	2016-02-16
     */
    public AnalyticsHelper(Application application){
        mTracker = application.getDefaultTracker();
    }

    /**
     * Report screen opened
     *
     * @author      Daniel Coellar
     * @created 	2016-02-16
     */
    public void reportScreen(String name){
        mTracker.setScreenName(name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Report an event
     *
     * @author      Daniel Coellar
     * @created 	2016-02-16
     */
    public void reportEvent(String category, String action){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }

}
