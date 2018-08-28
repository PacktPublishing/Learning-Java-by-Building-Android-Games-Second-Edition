package com.gamecodeschool.c22platformer.GOSpec;

import android.graphics.PointF;

public class BackgroundMountainSpec extends GameObjectSpec {
    // This is all the specifications for the mountain
    private static final String tag = "Background";
    private static final String bitmapName = "mountain";
    private static final int framesOfAnimation = 1;
    private static final float speed = 3f;
    private static final PointF size = new PointF(100, 70);
    private static final String[] components = new String [] {
            "BackgroundGraphicsComponent",
            "BackgroundUpdateComponent"};

    public BackgroundMountainSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
