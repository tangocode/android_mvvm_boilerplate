package com.boiler_plate.mobile.view.mvvmSample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boiler_plate.mobile.R;
import com.boiler_plate.mobile.di.components.DummyComponent;
import com.boiler_plate.mobile.util.BaseFragment;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.HandlerScheduler;
import rx.schedulers.Schedulers;

/**
 * Dummy Fragment that subscribe to an Obaservable
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class DummyFragment extends BaseFragment {
    private TextView dummyLabel;
    private Button btnUnsibscribe;
    private Button btnNetworkRequest;
    @Inject
    DummyViewModel dummyViewModel;
    public static DummyFragment newInstance() {
        Bundle args = new Bundle();
        DummyFragment fragment = new DummyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getComponent(DummyComponent.class).inject(this);
        return inflater.inflate(R.layout.dummy_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        dummyLabel = (TextView) view.findViewById(R.id.dummyLabel);
        btnNetworkRequest = (Button) view.findViewById(R.id.btnNetworkRequest);
        btnNetworkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummyViewModel.networkRequest();
            }
        });
        btnUnsibscribe = (Button) view.findViewById(R.id.btnUnsubscribe);
        btnUnsibscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummyViewModel.unsubscribe();
            }
        });
        final Handler handler = new Handler(); // bound to this thread
        dummyViewModel.dummyObservable().subscribeOn(Schedulers.newThread())
                .observeOn(HandlerScheduler.from(handler))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        dummyLabel.setText("Demo is done..");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        dummyLabel.setText(s);
                    }
                });

        dummyViewModel.networkRequest();
    }
}
