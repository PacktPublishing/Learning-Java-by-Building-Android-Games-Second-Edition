package com.gamecodeschoolc17.workingsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class BadAppleBuilder implements AppleBuilder {
    private NewApple Apple;

    // constructor for BadAppleBuilder
    public BadAppleBuilder(Bitmap mBitmapApple, Point location, Point mSpawnRange, int mSize){
        this.Apple = new NewApple();
        this.Apple.location = location;
        this.Apple.mBitmapApple = mBitmapApple;
        this.Apple.mSpawnRange = mSpawnRange;
        this.Apple.mSize = mSize;
    };

    @Override
    public Point getLocation() {
        return this.Apple.location;
    }

//    @Override
//    public void draw(Canvas canvas, Paint paint) {
//        canvas.drawBitmap(this.Apple.mBitmapApple,
//                this.Apple.location.x * this.Apple.mBitmapApple.getWidth(),
//                this.Apple.location.y * this.Apple.mBitmapApple.getHeight(), paint);
//    }

    @Override
    public void spawn(Point spawnRange, boolean hide) {
        if (hide == true) {
            this.Apple.location.x = -10; // Hide the apple off-screen until the game starts
        } else {
            this.Apple.mSpawnRange = spawnRange;
            Random random = new Random();
            this.Apple.location.x = random.nextInt(this.Apple.mSpawnRange.x) + 1;
            this.Apple.location.y = random.nextInt(this.Apple.mSpawnRange.y - 1) + 1;
        }
    }

    @Override
    public void setSize(int size) {
        this.Apple.mSize = size;
    }

    @Override
    public void setBitmap(Context context, int resourceId) {
        this.Apple.mBitmapApple = BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public NewApple returnApple() {
        return this.Apple;
    }

//    @Override
//    public NewApple build() {
//        return Apple(Apple.mSpawnRange, Apple.mSize, Apple.mBitmapApple);
//    }

}