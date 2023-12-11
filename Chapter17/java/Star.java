package com.gamecodeschool.c17snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Star {

    private Point location = new Point();
    private Bitmap mBitmapStar;
    private Point mSpawnRange;
    private int mSize;

    Star(Context context, Point sr, int s) {
        // spawn range
        mSpawnRange = sr;
        // size of a star
        mSize = s;

        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);

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

    // draw the star
    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapStar,
                location.x * mSize, location.y * mSize, paint);
    }

    // consume star >> get boosted!
    void applySpeedBoost(Snake snake) {
        snake.applySpeedBoost();
    }
}
