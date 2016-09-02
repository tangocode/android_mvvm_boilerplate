package com.boiler_plate.mobile.util;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.boiler_plate.mobile.di.HasComponent;

/**
 * BaseFragment that contains common behaviour that will be reused accross the other fragments
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class BaseFragment extends Fragment{
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
    }
}
