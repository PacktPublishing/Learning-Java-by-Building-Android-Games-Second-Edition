package com.gamecodeschool.c22platformer.GOSpec;

import android.graphics.PointF;

public class StalagmiteTileSpec extends GameObjectSpec {
    private static final String tag = "Inert Tile";
    private static final String bitmapName = "stalagmite";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(2f, 4f);
    private static final String[] components = new String [] {
            "InanimateBlockGraphicsComponent",
            "DecorativeBlockUpdateComponent"};

    public StalagmiteTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
