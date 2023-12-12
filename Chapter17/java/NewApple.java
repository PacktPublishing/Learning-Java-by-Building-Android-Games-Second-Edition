package com.gamecodeschool.c17snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class NewApple {
    public Bitmap mBitmapApple;
    public Point location;
    public Point mSpawnRange;
    public int mSize;

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.mBitmapApple,
                this.location.x * this.mBitmapApple.getWidth(),
                this.location.y * this.mBitmapApple.getHeight(), paint);
    };
}
