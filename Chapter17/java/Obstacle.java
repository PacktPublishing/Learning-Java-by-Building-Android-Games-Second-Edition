package com.gamecodeschool.c17snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Obstacle {

    private Point location = new Point();
    private Bitmap mBitmapObstacle;
    private Point mSpawnRange;
    private int mSize;

    Obstacle(Context context, Point sr, int s) {
        // spawn range
        mSpawnRange = sr;
        // size of an obstacle
        mSize = s;

        mBitmapObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);
        mBitmapObstacle = Bitmap.createScaledBitmap(mBitmapObstacle, s, s, false);

        // initial spawn range
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x);
        location.y = random.nextInt(mSpawnRange.y);

    }

    void spawn() {
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    Point getLocation() {
        return location;
    }

    // draw the obstacle
    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapObstacle,
                location.x * mSize, location.y * mSize, paint);
    }

}
