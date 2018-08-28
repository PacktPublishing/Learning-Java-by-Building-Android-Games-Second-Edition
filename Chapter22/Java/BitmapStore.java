package com.gamecodeschool.c22platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;

import java.util.HashMap;
import java.util.Map;

class BitmapStore {
    private static Map<String, Bitmap> mBitmapsMap;
    private static Map<String, Bitmap> mBitmapsReversedMap;
    private static BitmapStore mOurInstance;

    // Calling this method is the only way to get a BitmapStore
    static BitmapStore getInstance(Context context) {

        mOurInstance = new BitmapStore(context);
        return mOurInstance;
    }

    // Can't be called using new BitmapStore()
    private BitmapStore(Context c) {
        mBitmapsMap = new HashMap();
        mBitmapsReversedMap = new HashMap();

        // Put a default bitmap in each of the maps
        // to return in case a bitmap doesn't exist
        addBitmap(c,
                "death_visible",
                new PointF(1, 1),
                128,
                true);
    }

    static Bitmap getBitmap(String bitmapName) {

        if (mBitmapsMap.containsKey(bitmapName)) {
            return mBitmapsMap.get(bitmapName);
        } else {
            return mBitmapsMap.get("death_visible");
        }
    }

    static Bitmap getBitmapReversed(String bitmapName) {

        if (mBitmapsReversedMap.containsKey(bitmapName)) {
            return mBitmapsReversedMap.get(bitmapName);
        } else {
            return mBitmapsReversedMap.get("death_visible");
        }
    }

    static void addBitmap(Context c,
                          String bitmapName,
                          PointF objectSize,
                          int pixelsPerMetre,
                          boolean needReversed) {

        Bitmap bitmap;
        Bitmap bitmapReversed;

        // Make a resource id out of the string of the file name
        int resID = c.getResources().getIdentifier(bitmapName,
                "drawable", c.getPackageName());

        // Load the bitmap using the id
        bitmap = BitmapFactory
                .decodeResource(c.getResources(), resID);

        // Resize the bitmap
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) objectSize.x * pixelsPerMetre,
                (int) objectSize.y * pixelsPerMetre,
                false);

        mBitmapsMap.put(bitmapName, bitmap);

        if (needReversed) {
            // Create a mirror image of the bitmap
            Matrix matrix = new Matrix();
            matrix.setScale(-1, 1);
            bitmapReversed = Bitmap.createBitmap(
                    bitmap,
                    0, 0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    matrix, true);

            mBitmapsReversedMap.put(bitmapName, bitmapReversed);
        }

    }

    static void clearStore() {
        mBitmapsMap.clear();
        mBitmapsReversedMap.clear();
    }
}
