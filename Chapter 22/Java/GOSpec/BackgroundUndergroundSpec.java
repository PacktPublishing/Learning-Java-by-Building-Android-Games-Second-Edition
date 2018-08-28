package com.gamecodeschool.c22platformer.GOSpec;

import android.graphics.PointF;

public class BackgroundUndergroundSpec extends GameObjectSpec {
    // This is all the specifications for the underground
    private static final String tag = "Background";
    private static final String bitmapName = "underground";
    private static final int framesOfAnimation = 1;
    private static final float speed = 3f;
    private static final PointF size = new PointF(100, 70f);
    private static final String[] components = new String [] {
            "BackgroundGraphicsComponent",
            "BackgroundUpdateComponent"};

    public BackgroundUndergroundSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
