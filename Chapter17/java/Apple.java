package com.gamecodeschool.c17snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import java.util.Random;

public class Apple extends GameObject{

    private Point location = new Point();
    private Bitmap mBitmapApple;

    // Private constructor to enforce the use of the builder
    private Apple(Point spawnRange, int size, Bitmap bitmapApple) {
        super(spawnRange, size, bitmapApple);
        location.x = -10; // Hide the apple off-screen until the game starts
        mbitmapobject = bitmapApple;
        mbitmapobject = Bitmap.createScaledBitmap(mbitmapobject, size, size, false);
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
    // Builder class for Apple
    public static class AppleBuilder {
        private Point mSpawnRange;
        private int mSize;
        private Bitmap mBitmapApple;

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