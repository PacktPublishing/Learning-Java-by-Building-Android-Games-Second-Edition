package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;
import java.util.Random;

class AlienDiverMovementComponent implements MovementComponent {

    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {

        // Where is the ship?
        PointF location = t.getLocation();
        // How fast is the ship?
        float speed = t.getSpeed();

        // Relative speed difference with player
        float slowDownRelativeToPlayer = 1.8f;

        // Compensate for movement relative to player-
        // but only when in view.
        // Otherwise alien will disappear miles off to one side
        if(!playerTransform.getFacingRight()){
            location.x += speed * slowDownRelativeToPlayer / fps;
        } else{
            location.x -=  speed * slowDownRelativeToPlayer / fps;
        }

        // Fall down then respawn at the top
        location.y += speed / fps;

        if(location.y > t.getmScreenSize().y){
            // Respawn at top
            Random random = new Random();
            location.y = random.nextInt(300) - t.getObjectHeight();
            location.x = random.nextInt((int)t.getmScreenSize().x);
        }

        // Update the collider
        t.updateCollider();

        return true;
    }
}
