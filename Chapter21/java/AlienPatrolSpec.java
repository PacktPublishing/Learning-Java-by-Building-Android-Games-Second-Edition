package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;

class AlienPatrolSpec extends ObjectSpec {
    // This is all the unique specifications
    // for a patrolling alien
    private static final String tag = "Alien";
    private static final String bitmapName = "alien_ship2";
    private static final float speed = 5f;
    private static final PointF relativeScale =
            new PointF(15f, 15f);

    private static final String[] components = new String [] {
            "StdGraphicsComponent",
            "AlienPatrolMovementComponent",
            "AlienHorizontalSpawnComponent"};

    AlienPatrolSpec(){
        super(tag, bitmapName,
                speed, relativeScale,
                components);
    }
}
