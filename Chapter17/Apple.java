package com.gamecodeschool.CSC133final;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

class Apple extends GameObject{


    /// Set up the apple in the constructor
    Apple(Context context, Point sr, int s){
        super(context, sr, s);
        // Load the image to the bitmap
        mbitmapobject = BitmapFactory.decodeResource(context.getResources(), drawable.);
        //Matt here. R Seems to be an undefined value. FIX? Apple resource in drawable table. Will have to ask professor later.

        // Resize the bitmap
        mbitmapobject = Bitmap.createScaledBitmap(mbitmapobject, s, s, false);
    }

    // This is called every time an apple is eaten
    void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }
}