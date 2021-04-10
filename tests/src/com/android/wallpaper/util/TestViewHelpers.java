package com.android.wallpaper.util;

import android.content.Context;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.fail;

public class TestViewHelpers {
    private static final long DEFAULT_UI_TIMEOUT = 6000;

    private static UiDevice getDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

    public static UiObject2 findViewById(int id) {
        return getDevice().wait(Until.findObject(getSelectorForId(id)), DEFAULT_UI_TIMEOUT);
    }

    public static BySelector getSelectorForId(int id) {
        final Context targetContext = getTargetContext();
        String name = targetContext.getResources().getResourceEntryName(id);
        return By.res(targetContext.getPackageName(), name);
    }

    public static UiObject2 scrollAndFind(UiObject2 container, BySelector condition) {
        int i = 0;
        for (; ; ) {
            // findObject can only execute after spring settles.
            getDevice().wait(Until.findObject(condition), DEFAULT_UI_TIMEOUT);
            UiObject2 widget = container.findObject(condition);
            if (widget != null && widget.getVisibleBounds().intersects(
                    0, 0, getDevice().getDisplayWidth(),
                    getDevice().getDisplayHeight())) {
                return widget;
            }
            if (++i > 40) fail("Too many attempts");
            container.scroll(Direction.DOWN, 1f);
        }
    }
}
