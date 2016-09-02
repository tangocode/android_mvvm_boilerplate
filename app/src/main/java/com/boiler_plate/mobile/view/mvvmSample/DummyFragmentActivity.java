package com.boiler_plate.mobile.view.mvvmSample;

import android.os.Bundle;

import com.boiler_plate.mobile.di.HasComponent;
import com.boiler_plate.mobile.di.modules.DummyTestingModule;
import com.boiler_plate.mobile.util.BaseActivity;
import com.boiler_plate.mobile.R;
import com.boiler_plate.mobile.di.components.DaggerDummyComponent;
import com.boiler_plate.mobile.di.components.DummyComponent;

/**
 * Activity that contains the implementations Dummy MVVM proof of concept demo
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class DummyFragmentActivity extends BaseActivity implements HasComponent<DummyComponent> {
    private DummyComponent dummyComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        setContentView(R.layout.dummy_fragment_activity);
        this.getComponent().inject(this);
    }

    private void initializeInjector() {
        this.dummyComponent = DaggerDummyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .dummyTestingModule(new DummyTestingModule())
                .build();
    }

    @Override public DummyComponent getComponent() {
        return dummyComponent;
    }

}
