package com.gamecodeschool.c17snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class GameObject {


    // The location of the object on the grid
    // Not in pixels
    protected Point location = new Point();

    // The range of values we can choose from
    // to spawn an object
    protected Point mSpawnRange;
    protected int mSize;
    // How big is the entire grid
    protected Point mRange;

    // An image to represent the object
    protected Bitmap mbitmapobject;
    GameObject(Point spawnRange, int size, Bitmap bitmapApple){

        // Make a note of the passed in spawn range
        mRange = spawnRange;
        // Make a note of the size of an object
        mSize = size;
        // Hide the object off-screen until the game starts
        location.x = -10;

    }

    void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mbitmapobject,
                location.x * mSize, location.y * mSize, paint);

    }
    //let game know where the location of the object is
    Point getLocation(){
        return location;
    }

}