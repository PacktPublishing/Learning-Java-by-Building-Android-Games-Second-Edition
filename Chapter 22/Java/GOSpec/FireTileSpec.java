package com.gamecodeschool.c22platformer.GOSpec;


import android.graphics.PointF;

public class FireTileSpec extends GameObjectSpec {
    private static final String tag = "Death";
    private static final String bitmapName = "fire";
    private static final int framesOfAnimation = 3;
    private static final float speed = 0f;
    private static final PointF size = new PointF(1f, 1f);
    private static final String[] components = new String[]{
            "AnimatedGraphicsComponent",
            "InanimateBlockUpdateComponent"};

    public FireTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
