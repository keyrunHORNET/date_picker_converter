package com.hornet.dateconverter;

import android.app.Application;

import com.bugfender.sdk.Bugfender;

/**
 * Created by Kiran Gyawali on 5/3/2019.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bugfender.init(this, "7b9ahvW1f8R7zYTdHLvjajjqkKldDo9f", BuildConfig.DEBUG);
        Bugfender.enableCrashReporting();
        Bugfender.enableUIEventLogging(this);
        Bugfender.enableLogcatLogging();
    }

}
