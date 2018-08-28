package com.gamecodeschool.c22platformer.GOSpec;

import android.graphics.PointF;

public class ObjectiveTileSpec extends GameObjectSpec {

    private static final String tag = "Objective Tile";
    private static final String bitmapName = "objective";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(3f, 3f);
    private static final String[] components = new String[]{
            "InanimateBlockGraphicsComponent",
            "InanimateBlockUpdateComponent"};

    public ObjectiveTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
