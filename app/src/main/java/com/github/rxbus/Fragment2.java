package com.github.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhanggang on 2017/10/18.
 */

public class Fragment2 extends Fragment {


    private static final String TAG = "TAG";
    private View view;
    private CompositeSubscription compositeSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2,container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView textView = view.findViewById(R.id.textview);

        Observable<String> observable = RXBusBean.getInstance().toObservable(String.class);
        Subscription subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String string) {
                        textView.setText(string);
                        Log.e(TAG, "onNext: " + string);
                    }
                });
        addSubscription(subscription);
    }
    public void addSubscription(Subscription subscription){
        if (compositeSubscription==null){
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeSubscription!=null){
            compositeSubscription.unsubscribe();
        }
    }
}
