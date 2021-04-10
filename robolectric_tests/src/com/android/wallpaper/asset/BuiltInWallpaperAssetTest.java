package com.android.wallpaper.asset;

import android.app.Application;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class BuiltInWallpaperAssetTest {

    private BuiltInWallpaperAsset mAsset;

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;

        final Application application = RuntimeEnvironment.application;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(application);
        ShadowApplication shadowApplication = Shadows.shadowOf(application);
        shadowApplication.setSystemService(Context.WALLPAPER_SERVICE, wallpaperManager);

        mAsset = new BuiltInWallpaperAsset(application);
    }

    @After
    public void tearDown() throws Exception {
        mAsset = null;
        ShadowWallpaperManager.reset();
    }

    @Test
    @Config(shadows = ShadowWallpaperManager.class)
    public void testDecodeRawDimensions() {
        mAsset.decodeRawDimensions(null,  dimensions -> {
            assertNotNull(dimensions);
            assertTrue(dimensions.x > 0);
            assertTrue(dimensions.y > 0);
        });
    }

    @Test
    @Config(shadows = ShadowWallpaperManager.class)
    public void testDecodeBitmapRegion() {
        final Rect rect = new Rect(0, 0, 100, 100);

        mAsset.decodeBitmapRegion(rect, rect.width(), rect.height(), bitmap -> {
            assertNotNull(bitmap);
            assertEquals(rect.width(), bitmap.getWidth());
            assertEquals(rect.height(), bitmap.getHeight());
        });
    }

    @Test
    @Config(shadows = ShadowWallpaperManager.class)
    public void testDecodeBitmap() {
        final int size = 200;
        mAsset.decodeBitmap(size, size, bitmap -> {
            assertNotNull(bitmap);
            assertEquals(size, bitmap.getWidth());
            assertEquals(size, bitmap.getHeight());
        });
    }

    @Implements(WallpaperManager.class)
    public static class ShadowWallpaperManager extends
        org.robolectric.shadows.ShadowWallpaperManager {

        private static Drawable mDrawable;
        private static Drawable mCropDrawable;

        static void reset() {
            mDrawable = null;
            mCropDrawable = null;
        }

        @Implementation
        public Drawable getBuiltInDrawable() {
            if (mDrawable == null) {
                final Context context = RuntimeEnvironment.application;
                Resources sysRes = Resources.getSystem();
                int resId = sysRes.getIdentifier("default_wallpaper", "drawable", "android");
                mDrawable = context.getDrawable(resId);
            }
            return mDrawable;
        }

        @Implementation
        public Drawable getBuiltInDrawable(int outWidth, int outHeight,
            boolean scaleToFit, float horizontalAlignment, float verticalAlignment) {
            if (mCropDrawable == null) {
                getBuiltInDrawable();

                Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
                Bitmap cropBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
                mCropDrawable = new BitmapDrawable(cropBitmap);

                bitmap.recycle();
                cropBitmap.recycle();
            }
            return mCropDrawable;
        }

        @Implementation
        public void forgetLoadedWallpaper() {
            // Do nothing
        }
    }
}