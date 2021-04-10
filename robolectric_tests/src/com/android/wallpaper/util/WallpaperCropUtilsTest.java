package com.android.wallpaper.util;

import android.graphics.Point;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.Matchers.is;

/**
 * Test for {@link WallpaperCropUtils}
 */
@RunWith(RobolectricTestRunner.class)
public class WallpaperCropUtilsTest {

    @Test
    public void testCalculateCenterPosition() {
        Point outer = new Point(1080, 1920);
        Point inner = new Point(720, 1280);

        Point relativePosition =
                WallpaperCropUtils.calculateCenterPosition(outer, inner, false, false);
        Assert.assertThat(relativePosition.x, is(180));
        Assert.assertThat(relativePosition.y, is(320));

        relativePosition =
                WallpaperCropUtils.calculateCenterPosition(outer, inner, true, false);
        Assert.assertThat(relativePosition.x, is(0));
    }

    @Test
    public void testCalculateMinZoom() {
        Point outer = new Point(1440, 1280);
        Point inner = new Point(1080, 1920);

        float zoom = WallpaperCropUtils.calculateMinZoom(outer, inner);
        Assert.assertThat(zoom, is(1.5f));

        inner.x = 2880;
        zoom = WallpaperCropUtils.calculateMinZoom(outer, inner);
        Assert.assertThat(zoom, is(2f));
    }
}