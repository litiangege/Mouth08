package bwie.com.mouth08;

import android.app.Application;

/**
 * Created by Administrator on 2017/8/22.
 */

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
