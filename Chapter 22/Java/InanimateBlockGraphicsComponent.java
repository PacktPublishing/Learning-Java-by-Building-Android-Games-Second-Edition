package com.gamecodeschool.c22platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.gamecodeschool.c22platformer.GOSpec.GameObjectSpec;

class InanimateBlockGraphicsComponent implements GraphicsComponent {

    private String mBitmapName;

    @Override
    public void initialize(Context context,
                           GameObjectSpec spec,
                           PointF objectSize,
                           int pixelsPerMetre) {

        mBitmapName = spec.getBitmapName();

        BitmapStore.addBitmap(context,
                mBitmapName, objectSize,
                pixelsPerMetre,
                false);

    }


    @Override
    public void draw(Canvas canvas,
                     Paint paint,
                     Transform t,
                     Camera cam) {

        Bitmap bitmap = BitmapStore.getBitmap(mBitmapName);
        // Use the camera to translate the real world
        // coordinates relative to the player-
        // into screen coordinates
        Rect screenCoordinates = cam.worldToScreen(
                t.getLocation().x,
                t.getLocation().y,
                t.getSize().x,
                t.getSize().y);


        canvas.drawBitmap(
                bitmap,
                screenCoordinates.left,
                screenCoordinates.top,
                paint);


    }
}
