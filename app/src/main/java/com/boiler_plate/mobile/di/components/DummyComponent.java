package com.boiler_plate.mobile.di.components;

import com.boiler_plate.mobile.di.PerActivity;
import com.boiler_plate.mobile.di.modules.ActivityModule;
import com.boiler_plate.mobile.di.modules.DummyTestingModule;
import com.boiler_plate.mobile.view.mvvmSample.DummyFragment;
import com.boiler_plate.mobile.view.mvvmSample.DummyFragmentActivity;

import dagger.Component;

/**
 * A dummy domain component whose lifetime is the life of the activity that initialized it.
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DummyTestingModule.class})
public interface DummyComponent extends ActivityComponent {
    void inject(DummyFragmentActivity dummyFragmentActivity);
    void inject(DummyFragment dummyFragment);
}
