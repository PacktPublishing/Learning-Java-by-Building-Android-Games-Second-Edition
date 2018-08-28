package com.gamecodeschool.c24platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.gamecodeschool.c24platformer.GOSpec.GameObjectSpec;

class BackgroundGraphicsComponent implements GraphicsComponent {

    private String mBitmapName;

    @Override
    public void initialize(Context context,
                           GameObjectSpec spec,
                           PointF objectSize,
                           int pixelsPerMetre) {

        mBitmapName = spec.getBitmapName();

        BitmapStore.addBitmap(context,
                mBitmapName,
                objectSize,
                pixelsPerMetre,
                true);
    }

    @Override
    public void draw(Canvas canvas,
                     Paint paint,
                     Transform t,
                     Camera cam) {

        // Cast to a background transform
        BackgroundTransform bt = (BackgroundTransform)t;

        Bitmap bitmap = BitmapStore.getBitmap(mBitmapName);
        Bitmap bitmapRevesed = BitmapStore
                .getBitmapReversed(mBitmapName);

        int scaledxClip = (int)(bt.getXClip()
                * cam.getPixelsPerMetre());

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        PointF position = t.getLocation();

        float floatstartY = ((cam.getyCentre() -
                ((cam.getCameraWorldCentreY() - position.y) *
                        cam.getPixelsPerMetreY())));

        int startY = (int) floatstartY;

        float floatendY = ((cam.getyCentre() -
                ((cam.getCameraWorldCentreY() -
                        position.y - t.getSize().y) *
                        cam.getPixelsPerMetreY())));

        int endY = (int) floatendY;

        // Position the regular bitmap
        Rect fromRect1 = new Rect(0, 0, width - scaledxClip, height);
        Rect toRect1 = new Rect(scaledxClip, startY, width, endY);

        // Position the reversed bitmap
        Rect fromRect2 = new Rect(width - scaledxClip, 0, width, height);
        Rect toRect2 = new Rect(0, startY, scaledxClip, endY);

        // Draw the two bitmaps
        if (!bt.getReversedFirst()) {
            canvas.drawBitmap(bitmap, fromRect1, toRect1, paint);
            canvas.drawBitmap(bitmapRevesed, fromRect2, toRect2, paint);
        } else {
            canvas.drawBitmap(bitmap, fromRect2, toRect2, paint);
            canvas.drawBitmap(bitmapRevesed, fromRect1, toRect1, paint);
        }
    }
}
