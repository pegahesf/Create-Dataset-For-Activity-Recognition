package app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by pegah on 8/30/2017.
 */

public class app {

    public static class main {
        public static  final String TAG = "finalfordataset";
           }

           public static void l(String message){

               Log.e(main.TAG,message);
           }

    public static void t(Context context, String message){

        Toast.makeText(context,message,Toast.LENGTH_LONG);
    }
}
