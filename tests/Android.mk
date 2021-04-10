#
# Build rule for WallpaperPicker2Tests
#
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := tests

LOCAL_STATIC_JAVA_LIBRARIES := \
    androidx.test.runner \
    androidx.test.rules \
    androidx.test.uiautomator_uiautomator \
    mockito-target-minus-junit4 \
    platform-test-annotations \
    ub-uiautomator \
    truth-prebuilt \

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := WallpaperPicker2Tests
LOCAL_PRIVATE_PLATFORM_APIS := true

LOCAL_INSTRUMENTATION_FOR := WallpaperPicker2

include $(BUILD_PACKAGE)
