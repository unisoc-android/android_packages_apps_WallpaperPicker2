package com.android.wallpaper.picker.individual;

import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.android.wallpaper.R;
import com.android.wallpaper.model.AppResourceWallpaperInfo;
import com.android.wallpaper.model.WallpaperInfo;
import com.android.wallpaper.picker.AbstractWallpaperUiTest;
import com.android.wallpaper.util.TestViewHelpers;
import com.android.wallpaper.util.Wait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class IndividualWallpaperTest extends AbstractWallpaperUiTest {
    private UiObject2 mImageGrid;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        startIndividualWallpaper();
    }

    public void openPreviewWallpaper(int index) {
        mImageGrid = TestViewHelpers.findViewById(R.id.wallpaper_grid);
        assertNotNull(mImageGrid);

        UiObject2 itemView = TestViewHelpers.findViewById(R.id.wallpaper_grid).getChildren().get(index);
        assertNotNull(itemView);
        UiObject2 tileLayout = itemView.findObject(TestViewHelpers.getSelectorForId(R.id.tile));
        tileLayout.click();
    }

    @Test
    public void testIndividualWallpaperCount() {
        mImageGrid = TestViewHelpers.findViewById(R.id.wallpaper_grid);
        assertNotNull(mImageGrid);

        List<WallpaperInfo> resourceWpList = AppResourceWallpaperInfo.getAll(mTargetContext,
                mTargetContext.getApplicationInfo(), R.array.wallpapers);
        assertEquals(1, mImageGrid.getChildren().size(), resourceWpList.size());
    }

    @Test
    public void testCanOpenPreviewActivity() {
        openPreviewWallpaper(0);

        Wait.atMost(null,
                () -> mDevice.wait(Until.hasObject(By.text("SET WALLPAPER")), DEFAULT_UI_TIMEOUT),
                DEFAULT_UI_TIMEOUT);
        assertNotNull(mDevice.wait(Until.findObject(By.text("Preview")), DEFAULT_UI_TIMEOUT));
    }

    @After
    public void tearDown() {
    }
}
