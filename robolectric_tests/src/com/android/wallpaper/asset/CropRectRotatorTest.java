package com.android.wallpaper.asset;

import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CropRectRotatorTest {

    @Test
    public void TestRotateCropRectForExifOrientation() {
        Point dimensions = new Point(1080, 1920);
        Rect srcRect = new Rect(0, 0, 720, 1440);

        Rect orientationNormalRect = CropRectRotator.rotateCropRectForExifOrientation(
                dimensions, srcRect, ExifInterface.ORIENTATION_NORMAL);
        Assert.assertEquals(srcRect, orientationNormalRect);

        Rect orientationRotate90Rect = CropRectRotator.rotateCropRectForExifOrientation(
                dimensions, srcRect, ExifInterface.ORIENTATION_ROTATE_90);
        Rect tempRect1 = new Rect(0, 360, 1440, 1080);
        Assert.assertEquals(tempRect1, orientationRotate90Rect);

        Rect orientationRotate180Rect = CropRectRotator.rotateCropRectForExifOrientation(
                dimensions, srcRect, ExifInterface.ORIENTATION_ROTATE_180);
        Rect tempRect2 = new Rect(360, 480, 1080, 1920);
        Assert.assertEquals(tempRect2, orientationRotate180Rect);

        Rect orientationRotate270Rect = CropRectRotator.rotateCropRectForExifOrientation(
                dimensions, srcRect, ExifInterface.ORIENTATION_ROTATE_270);
        Rect tempRect3 = new Rect(480, 0, 1920, 720);
        Assert.assertEquals(tempRect3, orientationRotate270Rect);
    }
}
