package com.gamecodeschool.c25platformer.GOSpec;


import android.graphics.PointF;

public class CartTileSpec extends GameObjectSpec {
    private static final String tag = "Inert Tile";
    private static final String bitmapName = "cart";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(2f, 1f);
    private static final String[] components = new String[]{
            "InanimateBlockGraphicsComponent",
            "InanimateBlockUpdateComponent"};

    public CartTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
