package com.gamecodeschool.c20scrollingshooter;


import android.graphics.PointF;

class AlienDiverSpec extends ObjectSpec {
    // This is all the unique specifications
    // for an alien that dives
    private static final String tag = "Alien";
    private static final String bitmapName = "alien_ship3";
    private static final float speed = 4f;
    private static final PointF relativeScale =
            new PointF(60f, 30f);

    private static final String[] components = new String [] {
            "StdGraphicsComponent",
            "AlienDiverMovementComponent",
            "AlienVerticalSpawnComponent"};

    AlienDiverSpec(){
        super(tag, bitmapName, speed, relativeScale, components);
    }
}
