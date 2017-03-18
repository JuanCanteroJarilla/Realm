package com.example.juan.realm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Juan on 14/03/2017.
 */

public class RealmInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }
}
