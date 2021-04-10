package com.android.wallpaper.model;

import android.content.Context;

import com.android.wallpaper.R;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class AppResourceWallpaperInfoTest {

    @Test
    public void testGetAll() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        List<WallpaperInfo> resourceWpList = AppResourceWallpaperInfo.getAll(context,
                context.getApplicationInfo(), R.array.wallpapers);
        Assert.assertTrue(resourceWpList.size() > 0);
    }
}