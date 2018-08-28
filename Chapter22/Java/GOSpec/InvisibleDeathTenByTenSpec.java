package com.gamecodeschool.c22platformer.GOSpec;
import android.graphics.PointF;

public class InvisibleDeathTenByTenSpec extends GameObjectSpec {
    private static final String tag = "Death";
    private static final String bitmapName = "death_visible";
    private static final int framesOfAnimation = 1;
    private static final float speed = 0f;
    private static final PointF size = new PointF(10f, 10f);
    private static final String[] components = new String[]{"InanimateBlockGraphicsComponent", "InanimateBlockUpdateComponent"};

    public InvisibleDeathTenByTenSpec() {
        super(tag, bitmapName, speed, size, components, framesOfAnimation);
    }
}
