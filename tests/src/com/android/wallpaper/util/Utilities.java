package com.android.wallpaper.util;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.InstrumentationRegistry;

import static androidx.test.InstrumentationRegistry.getInstrumentation;

public class Utilities {

    public static void startWallpaperApp() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_SET_WALLPAPER);
        intent.setPackage(InstrumentationRegistry.getTargetContext().getPackageName());
        getInstrumentation().startActivitySync(intent);
    }

    public static void sleep(long ms) {
        SystemClock.sleep(ms);
    }
}
