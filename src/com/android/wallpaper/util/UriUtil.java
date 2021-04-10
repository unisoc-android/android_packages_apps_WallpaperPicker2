package com.android.wallpaper.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

public class UriUtil {
    private static final String TAG = "UriUtil";

    private static final String STORAGE_NAME = "storage";

    public static String getAbsolutePath(Context context, Uri uri) {
        if (context == null || uri == null) {
            return null;
        }

        final String scheme = uri.getScheme();
        if (scheme == null) {
            return uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            return uri.getPath();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                return getExtStorageDocUriPath(uri);
            } else if (isDownloadsDocument(uri)) {
                return getDownloadsDocUriPath(context, uri);
            } else if (isMediaDocument(uri)) {
                return getMediaDocumentUriPath(context, uri);
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            if (isGooglePlayPhotosUri(uri)) {
                // Could not get the absolute path from google photos' uri, just print log here.
                Log.d(TAG, "The path of google photos' uri is: " + uri.getPath());
            } else if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else {
                return getDataColumn(context, uri, null, null);
            }
        }

        Log.w(TAG, "Couldn't get the absolute path of uri: " + uri);
        return null;
    }

    private static String getExtStorageDocUriPath(final Uri uri) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];
        if ("primary".equalsIgnoreCase(type)) {
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        } else {
            return STORAGE_NAME + "/" + type +"/" + split[1];
        }
    }

    private static String getDownloadsDocUriPath(final Context context, final Uri uri) {
        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        return getDataColumn(context, contentUri, null, null);
    }

    private static String getMediaDocumentUriPath(final Context context, final Uri uri) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        if (contentUri == null) {
            Log.w(TAG, "The contentUri is null: type = " + type);
            return null;
        } else {
            final String selection = MediaStore.Images.Media._ID + "=?";
            final String[] selectionArgs = new String[] { split[1] };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        }
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } catch(Exception e){
            Log.w(TAG, "Get the absolute path of the uri failed!", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Play Photos.
     */
    private static boolean isGooglePlayPhotosUri(Uri uri) {
        return "com.google.android.apps.photos.contentprovider".equals(uri.getAuthority());
    }


}

