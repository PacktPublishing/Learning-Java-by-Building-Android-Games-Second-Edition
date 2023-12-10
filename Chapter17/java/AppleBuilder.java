package com.gamecodeschoolc17.workingsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public interface AppleBuilder {
    Point getLocation();
    public void spawn(Point spawnRange, boolean hide);
    public void setSize(int size);
//    public void draw(Canvas canvas, Paint paint);
    public void setBitmap(Context context, int resourceId);
//    public void build();
};

