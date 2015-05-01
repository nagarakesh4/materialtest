package materialtest.sanjose.venkata.instance;

import android.app.Application;
import android.content.Context;

/**
 * Created by buddhira on 4/30/2015.
 */
public class ApplicationInstance extends Application {

    private static ApplicationInstance sInstance;

    //called before anything runs in the app - onCreate()
    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
    }

    public static ApplicationInstance getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

}
