package com.github.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by zhanggang on 2017/10/18.
 */

public class RXBusBean {
    Subject bus;
    private static RXBusBean instance;

    public RXBusBean() {
        bus = new SerializedSubject(PublishSubject.create());
    }

    //单列模式
    public static synchronized RXBusBean getInstance() {
        if (instance == null) {
            instance = new RXBusBean();
        }
        return instance;
    }
    //发送
    public void post(Object o){
        if (bus.hasObservers()){
            bus.onNext(o);
        }
    }
    //接收
    public <T>Observable<T> toObservable(Class<T> clazz){
        return bus.ofType(clazz);
    }
}
