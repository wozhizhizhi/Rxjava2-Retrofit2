package com.aji.retrofitlib;

import android.app.Application;
import android.content.Context;

import com.aji.retrofitlib.util.Utils;

/**
 * Application
 */

public class App extends Application
{
    private static App app;
    public static Context getAppContext()
    {
        if (app == null)
        {
            synchronized(App.class)
            {
                if (app == null)
                {
                    app = new App();
                }
            }
        }
        return app;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        Utils.init(this);
        app=this;
    }
}
