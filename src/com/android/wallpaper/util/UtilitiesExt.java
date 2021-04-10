package com.android.wallpaper.util;

import android.os.AsyncTask;
import android.os.Build;

public class UtilitiesExt {

    public static boolean isRoboUnitTest() {
        return isASRoboUnitTest() || Build.UNKNOWN.equals(Build.DEVICE);
    }

    public static boolean isASRoboUnitTest() {
        return "robolectric".equals(Build.DEVICE);
    }

    public static void executeAsyncTask(AsyncTask<Void, Void, ?> task) {
        if (task == null) {
            return;
        }

        if (isRoboUnitTest()) {
            task.execute();
        } else {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}
