package com.android.wallpaper.picker;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.android.wallpaper.R;
import com.android.wallpaper.util.MainThreadExecutor;
import com.android.wallpaper.util.TestViewHelpers;
import com.android.wallpaper.util.Utilities;
import com.android.wallpaper.util.Wait;

import org.junit.Before;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractWallpaperUiTest {

    protected static final long DEFAULT_UI_TIMEOUT = 6000;

    protected MainThreadExecutor mMainThreadExecutor = new MainThreadExecutor();

    protected final UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

    protected Context mTargetContext;
    protected String mTargetPackage;

    public UiDevice getDevice() {
        return mDevice;
    }

    @Before
    public void setUp() throws Exception {
        mTargetContext = InstrumentationRegistry.getTargetContext();
        mTargetPackage = mTargetContext.getPackageName();

        // Unlock the phone
        mDevice.executeShellCommand("input keyevent 82");
    }

    protected void startIndividualWallpaper() {
        Utilities.startWallpaperApp();
        Wait.atMost(null,
                () -> mDevice.wait(Until.hasObject(By.text("Wallpapers")), DEFAULT_UI_TIMEOUT),
                DEFAULT_UI_TIMEOUT);

        UiObject2 fragmentContainer = TestViewHelpers.findViewById(R.id.fragment_container);
        UiObject2 onDevicesWallpapers = TestViewHelpers.scrollAndFind(fragmentContainer, By.text("On-device wallpapers"));
        assertNotNull(onDevicesWallpapers);
        onDevicesWallpapers.click();
        Wait.atMost(null,
                () -> mDevice.wait(Until.hasObject(By.text("On-device wallpapers")), DEFAULT_UI_TIMEOUT),
                DEFAULT_UI_TIMEOUT);
    }
}
