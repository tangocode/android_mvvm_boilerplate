package com.boiler_plate.mobile.di.components;

import android.app.Activity;
import com.boiler_plate.mobile.di.PerActivity;
import com.boiler_plate.mobile.di.modules.ActivityModule;
import com.boiler_plate.mobile.managers.AnalyticsHelper.AnalyticsHelper;

import dagger.Component;
/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  Activity activity();
  void inject(AnalyticsHelper analyticsHelper);
}
