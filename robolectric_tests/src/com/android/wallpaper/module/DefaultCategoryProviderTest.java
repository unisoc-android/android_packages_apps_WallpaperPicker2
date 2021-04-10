package com.android.wallpaper.module;

import android.content.Context;

import com.android.wallpaper.R;
import com.android.wallpaper.model.Category;
import com.android.wallpaper.model.CategoryProvider;
import com.android.wallpaper.model.CategoryReceiver;
import com.android.wallpaper.util.Wait;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
public class DefaultCategoryProviderTest {

    private InjectorProvider mInjectorProvider;
    private CategoryProvider mCategoryProvider;

    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = RuntimeEnvironment.application.getApplicationContext();

        mInjectorProvider = mock(InjectorProvider.class);

        mCategoryProvider = mInjectorProvider.getInjector().getCategoryProvider(mContext);
        Assert.assertNotNull(mCategoryProvider);
    }

    @Test
    public void testFetchCategories() {
        final boolean[] doneFetching = {false};
        mCategoryProvider.fetchCategories(new CategoryReceiver() {
            @Override
            public void onCategoryReceived(Category category) {
                doneFetching[0] = true;
            }

            @Override
            public void doneFetchingCategories() {
                // todo
            }
        }, true);

        Wait.atMost("Don't fetch the wallpaper categories in a certain time",
                () -> doneFetching[0], 200);

        Assert.assertTrue(mCategoryProvider.getSize() > 0);


        Assert.assertNotNull(mCategoryProvider.getCategory(0));
        Assert.assertNotNull(mCategoryProvider.getCategory
                (mContext.getString(R.string.image_wallpaper_collection_id)));
    }
}