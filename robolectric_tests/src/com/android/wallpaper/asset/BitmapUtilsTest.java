package com.android.wallpaper.asset;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class BitmapUtilsTest {

    @Test
    public void testCalculateInSampleSize() {
        final Point source = new Point(500, 500);
        final Point target = new Point(1000, 1000);
        int inSampleSize = BitmapUtils.calculateInSampleSize(source.x, source.y, target.x, target.y);
        assertEquals(1, inSampleSize);

        source.set(2000, 2000);
        inSampleSize = BitmapUtils.calculateInSampleSize(source.x, source.y, target.x, target.y);
        assertEquals(2, inSampleSize);

        source.set(4000, 4000);
        inSampleSize = BitmapUtils.calculateInSampleSize(source.x, source.y, target.x, target.y);
        assertEquals(4, inSampleSize);
    }

    @Test
    public void testGenerateHashCode() {
        Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.HARDWARE);
        long bitmapHash = BitmapUtils.generateHashCode(bitmap);
        assertTrue(bitmapHash > 0);
        bitmap.recycle();
    }

    @Test
    public void testCalculateHorizontalAlignment() {
        final Point size = new Point(100, 100);
        final Rect rect = new Rect(0, 0, 100, 100);
        float alignment = BitmapUtils.calculateHorizontalAlignment(size, rect);
        assertThat(alignment, is(0.5f));

        rect.set(50, 0, 100, 100);
        alignment = BitmapUtils.calculateHorizontalAlignment(size, rect);
        assertThat(alignment, is(1f));

        rect.set(0, 0, 50, 100);
        alignment = BitmapUtils.calculateHorizontalAlignment(size, rect);
        assertThat(alignment, is(0f));
    }

    @Test
    public void testCalculateVerticalAlignment() {
        final Point size = new Point(100, 100);
        final Rect rect = new Rect(0, 0, 100, 100);
        float alignment = BitmapUtils.calculateVerticalAlignment(size, rect);
        assertThat(alignment, is(0.5f));

        rect.set(0, 50, 100, 100);
        alignment = BitmapUtils.calculateVerticalAlignment(size, rect);
        assertThat(alignment, is(1f));

        rect.set(0, 0, 100, 50);
        alignment = BitmapUtils.calculateVerticalAlignment(size, rect);
        assertThat(alignment, is(0f));
    }
}