package com.gamecodeschool.c25platformer.GOSpec;

import android.graphics.PointF;

public class BackgroundCitySpec extends GameObjectSpec {
    // This is all the unique specifications for the city background
    private static final String tag = "Background";
    private static final String bitmapName = "city";
    private static final int framesOfAnimation = 1;
    private static final float speed = 3f;
    private static final PointF size = new PointF(100, 70);
    private static final String[] components = new String [] {
            "BackgroundGraphicsComponent","BackgroundUpdateComponent"};

    public BackgroundCitySpec() {
        super(tag, bitmapName, speed, size, components, framesOfAnimation);
    }
}
