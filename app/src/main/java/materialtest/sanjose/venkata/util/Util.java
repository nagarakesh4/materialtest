package materialtest.sanjose.venkata.util;

import android.os.Build;

/**
 * Created by buddhira on 5/1/2015.
 */
public class Util {
    public static boolean greaterThanOrEqualToLollipop() {
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }

    public static boolean greaterThanOrEqualToJellyBean() {
        return Build.VERSION.SDK_INT >= 16 ? true : false;
    }
}
