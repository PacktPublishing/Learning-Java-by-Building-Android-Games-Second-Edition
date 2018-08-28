package com.gamecodeschool.c24platformer.GOSpec;


import android.graphics.PointF;

public class LamppostTileSpec extends GameObjectSpec {
    private static final String tag = "Inert Tile";
    private static final String bitmapName = "lamppost";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(1f, 4f);
    private static final String[] components = new String[]{
            "InanimateBlockGraphicsComponent",
            "DecorativeBlockUpdateComponent"};

    public LamppostTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
