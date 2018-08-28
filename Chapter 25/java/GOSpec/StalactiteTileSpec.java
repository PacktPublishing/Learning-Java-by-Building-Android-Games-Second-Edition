package com.gamecodeschool.c25platformer.GOSpec;

import android.graphics.PointF;

public class StalactiteTileSpec extends GameObjectSpec {
    private static final String tag = "Inert Tile";
    private static final String bitmapName = "stalactite";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(2f, 4f);
    private static final String[] components = new String [] {
            "InanimateBlockGraphicsComponent",
            "DecorativeBlockUpdateComponent"};

    public StalactiteTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
