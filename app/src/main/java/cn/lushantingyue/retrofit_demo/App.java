package cn.lushantingyue.retrofit_demo;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by Lushantingyue on 2017/12/26.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class App extends Application {

    private App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init("retrofit_demo")                 // default PRETTYLOGGER or use just init()
                .setMethodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .setMethodOffset(2);                // default 0

        instance = this;
    }

    public App getInstance() {
        return instance;
    }
}
