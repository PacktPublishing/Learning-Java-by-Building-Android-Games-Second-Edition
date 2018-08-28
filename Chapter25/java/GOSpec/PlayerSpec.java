package com.gamecodeschool.c25platformer.GOSpec;

import android.graphics.PointF;

public class PlayerSpec extends GameObjectSpec {
    // This is all the unique specifications for a player
    private static final String tag = "Player";
    private static final String bitmapName = "player";
    private static final int framesOfAnimation = 5;
    private static final float speed = 10f;
   private static final PointF size = new PointF(1f, 2f);
    private static final String[] components = new String [] {
            "PlayerInputComponent",
            "AnimatedGraphicsComponent",
            "PlayerUpdateComponent"};

    public PlayerSpec() {
        super(tag, bitmapName, speed,
                size, components, framesOfAnimation);
    }
}
