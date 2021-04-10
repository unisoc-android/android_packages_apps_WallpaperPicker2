/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.wallpaper.config;

import com.android.wallpaper.util.SystemPropertiesUtils;

abstract class BaseFlags {
    public static boolean skipDailyWallpaperButtonEnabled = true;
    public static boolean desktopUiEnabled = false;
    public static boolean dynamicStartRotationTileEnabled = true;
    public static boolean stagingBackdropContentEnabled = false;
    public static boolean performanceMonitoringEnabled = true;

    public static final boolean SPRD_ENABLE_LOCK_WALLPAPER =
            SystemPropertiesUtils.getBoolean("ro.lockwallpaper.enable", true);

    public static final boolean DEBUG =
            SystemPropertiesUtils.getBoolean("persist.sys.wallpaper.debug", false);
}
