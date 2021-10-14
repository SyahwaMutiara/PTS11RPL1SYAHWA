package com.example.pts11rpl1syahwa;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("sport.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

}
