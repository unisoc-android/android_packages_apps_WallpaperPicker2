package com.android.wallpaper.picker;

import android.app.WallpaperManager;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.android.wallpaper.R;
import com.android.wallpaper.config.Flags;
import com.android.wallpaper.util.TestViewHelpers;
import com.android.wallpaper.util.Utilities;
import com.android.wallpaper.util.Wait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class PreviewActivityTest extends AbstractWallpaperUiTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    private void openPreviewWallpaperFromIndividual(int index) {
        UiObject2 imageGrid = TestViewHelpers.findViewById(R.id.wallpaper_grid);
        assertNotNull(imageGrid);

        UiObject2 itemView = TestViewHelpers.findViewById(R.id.wallpaper_grid).getChildren().get(index);
        assertNotNull(itemView);
        UiObject2 tileLayout = itemView.findObject(TestViewHelpers.getSelectorForId(R.id.tile));
        tileLayout.click();

        Wait.atMost(null,
                () -> mDevice.wait(Until.hasObject(By.text("SET WALLPAPER")), DEFAULT_UI_TIMEOUT),
                DEFAULT_UI_TIMEOUT);
    }

    private void setWallpaper(String wallpaperType, int wallpaperIndex) {
        openPreviewWallpaperFromIndividual(wallpaperIndex);
        mDevice.wait(Until.findObject(By.text("SET WALLPAPER")), DEFAULT_UI_TIMEOUT).click();
        mDevice.wait(Until.findObject(By.text(wallpaperType)), DEFAULT_UI_TIMEOUT).click();

        Utilities.sleep(3000);
        mDevice.pressHome();
    }

    @Test
    public void testPreviewActivityUI() {
        startIndividualWallpaper();
        openPreviewWallpaperFromIndividual(0);

        UiObject2 preview = mDevice.wait(Until.findObject(By.text("Preview")), DEFAULT_UI_TIMEOUT);
        preview.click();
        assertTrue(preview.isChecked());
        Utilities.sleep(1000);
        assertFalse(mDevice.wait(Until.hasObject(By.text("SET WALLPAPER")), DEFAULT_UI_TIMEOUT));

        preview.click();
        assertFalse(preview.isChecked());
        Utilities.sleep(1000);
        assertTrue(mDevice.wait(Until.hasObject(By.text("SET WALLPAPER")), DEFAULT_UI_TIMEOUT));
    }

    @Test
    public void testSeparateSetWallpaper() {
        assumeTrue(Flags.SPRD_ENABLE_LOCK_WALLPAPER);

        startIndividualWallpaper();
        openPreviewWallpaperFromIndividual(0);

        mDevice.wait(Until.findObject(By.text("SET WALLPAPER")), DEFAULT_UI_TIMEOUT).click();
        assertTrue(mDevice.wait(Until.hasObject(By.text("Home screen")), DEFAULT_UI_TIMEOUT));
        assertTrue(mDevice.wait(Until.hasObject(By.text("Lock screen")), DEFAULT_UI_TIMEOUT));
        assertTrue(mDevice.wait(Until.hasObject(By.text("Home screen and lock screen")), DEFAULT_UI_TIMEOUT));
    }

    @Test
    public void testSetHomeWallpaper() {
        startIndividualWallpaper();
        runTestSetWallpaper("Home screen", WallpaperManager.FLAG_SYSTEM);
    }

    @Test
    public void testSetLockWallpaper() {
        startIndividualWallpaper();
        runTestSetWallpaper("Lock screen", WallpaperManager.FLAG_LOCK);
    }

    @Test
    public void testSetBothWallpaper() {
        startIndividualWallpaper();
        runTestSetWallpaper("Home screen and lock screen", WallpaperManager.FLAG_SYSTEM);
    }

    private void runTestSetWallpaper(String wallpaperType, int which) {
        setWallpaper(wallpaperType, 1);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mTargetContext);
        int beforeWallpaperId = wallpaperManager.getWallpaperId(which);

        startIndividualWallpaper();
        setWallpaper(wallpaperType, 2);
        int afterWallpaperId = wallpaperManager.getWallpaperId(which);
        assertNotEquals(beforeWallpaperId, afterWallpaperId);
    }

    @After
    public void tearDown() {
    }
}
