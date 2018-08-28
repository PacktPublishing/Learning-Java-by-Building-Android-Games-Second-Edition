package com.gamecodeschool.c24platformer.GOSpec;

import android.graphics.PointF;

public class SnowyTreeTileSpec extends GameObjectSpec {
    private static final String tag = "Inert Tile";
    private static final String bitmapName = "snowy_tree";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(3f, 6f);
    private static final String[] components = new String [] {
            "InanimateBlockGraphicsComponent",
            "DecorativeBlockUpdateComponent"};

    public SnowyTreeTileSpec() {
        super(tag, bitmapName, speed, size,
                components, framesOfAnimation);
    }
}
