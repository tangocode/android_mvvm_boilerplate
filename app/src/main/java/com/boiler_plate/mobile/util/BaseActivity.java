package com.boiler_plate.mobile.util;

import android.support.v7.app.AppCompatActivity;

import com.boiler_plate.mobile.Application;
import com.boiler_plate.mobile.di.modules.ActivityModule;
import com.boiler_plate.mobile.di.components.ApplicationComponent;

/**
 * BaseActivity that contains common behaviour that will be reused accross the other activities
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((Application)getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
