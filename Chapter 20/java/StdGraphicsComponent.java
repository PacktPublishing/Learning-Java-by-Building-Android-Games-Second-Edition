package com.gamecodeschool.c20scrollingshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

class StdGraphicsComponent implements GraphicsComponent {

    private Bitmap mBitmap;
    private Bitmap mBitmapReversed;

    @Override
    public void initialize(Context context,
                           ObjectSpec spec,
                           PointF objectSize){

        // Make a resource id out of the string of the file name
        int resID = context.getResources()
                .getIdentifier(spec.getBitmapName(),
                "drawable",
                context.getPackageName());

        // Load the bitmap using the id
        mBitmap = BitmapFactory.decodeResource(
                context.getResources(), resID);

        // Resize the bitmap
        mBitmap = Bitmap
                .createScaledBitmap(mBitmap,
                        (int)objectSize.x,
                        (int)objectSize.y,
                        false);

        // Create a mirror image of the bitmap if required
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        mBitmapReversed = Bitmap.createBitmap(mBitmap,
                0, 0,
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                matrix, true);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Transform t) {
        if(t.getFacingRight())
            canvas.drawBitmap(mBitmap,
                    t.getLocation().x,
                    t.getLocation().y,
                    paint);

        else
            canvas.drawBitmap(mBitmapReversed,
                    t.getLocation().x,
                    t.getLocation().y,
                    paint);
    }
}
