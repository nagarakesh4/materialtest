package materialtest.sanjose.venkata.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by buddhira on 5/1/2015.
 */
public class Logger {

    public static void showLogInfo(String message) {
        Log.i("this is logInfo", "" + message);
    }

    public static void showLogDebug(String message) {
        Log.d("this is logDebug", "" + message);
    }

    public static void showLogError(String message) {
        Log.e("this is logErr", "" + message);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
}
