package com.boiler_plate.mobile.di.modules;

import android.app.Activity;

import com.boiler_plate.mobile.Application;
import com.boiler_plate.mobile.di.PerActivity;
import com.boiler_plate.mobile.managers.AnalyticsHelper.AnalyticsHelper;

import dagger.Module;
import dagger.Provides;
/**
 * A module to wrap the Activity state and expose it to the graph.
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@Module
public class ActivityModule {
  private final Activity activity;
  private final AnalyticsHelper analyticsHelper;

  public ActivityModule(Activity activity) {
    this.activity = activity;
    this.analyticsHelper = new AnalyticsHelper((Application)activity.getApplication());
  }

  /**
  * Expose the activity to dependents in the graph.
  */
  @Provides
  @PerActivity
  Activity activity() {
    return this.activity;
  }

  /**
   * Expose the analytics helper to dependents in the graph.
   */
  @Provides
  @PerActivity
  public AnalyticsHelper provideAnalyticsHelper() {
    return this.analyticsHelper;
  }

}
