package com.gamecodeschool.c17snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

public class Apple {
    // The location of the apple
    private Point location = new Point();

    private Bitmap mBitmapApple;

    // Private constructor to enforce the use of the builder
    private Apple(Point spawnRange, int size, Bitmap bitmapApple) {
        location.x = -10; // Hide the apple off-screen until the game starts
        mBitmapApple = bitmapApple;
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, size, size, false);
    }

    // This is called every time an apple is eaten
    void spawn(Point spawnRange) {
        Random random = new Random();
        location.x = random.nextInt(spawnRange.x) + 1;
        location.y = random.nextInt(spawnRange.y - 1) + 1;
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    Point getLocation() {
        return location;
    }

    // Draw the apple
    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapApple, location.x * mBitmapApple.getWidth(), location.y * mBitmapApple.getHeight(), paint);
    }

    // Builder class for Apple
    public static class AppleBuilder {
        private Point mSpawnRange;
        private int mSize;
        private Bitmap mBitmapApple;

        // Constructor to initialize context parameter
        public AppleBuilder(Context context) {
            mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        }

        public AppleBuilder setSpawnRange(Point spawnRange) {
            mSpawnRange = spawnRange;
            return this;
        }

        public AppleBuilder setSize(int size) {
            mSize = size;
            return this;
        }

        public AppleBuilder setBitmap(Context context, int resourceId) {
            mBitmapApple = BitmapFactory.decodeResource(context.getResources(), resourceId);
            return this;
        }

        public Apple build() {
            return new Apple(mSpawnRange, mSize, mBitmapApple);
        }
    }
}