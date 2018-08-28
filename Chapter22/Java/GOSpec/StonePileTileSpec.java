package com.gamecodeschool.c22platformer.GOSpec;

import android.graphics.PointF;

public class StonePileTileSpec extends GameObjectSpec {
        private static final String tag = "Inert Tile";
        private static final String bitmapName = "stone_pile";
        private static final int framesOfAnimation = 1;
        private static final float speed = 0f;
        private static final PointF size = new PointF(2f, 1f);
        private static final String[] components = new String [] {
                "InanimateBlockGraphicsComponent",
                "InanimateBlockUpdateComponent"};

        public StonePileTileSpec() {
            super(tag, bitmapName, speed, size,
                    components, framesOfAnimation);
        }
}
