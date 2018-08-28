package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;
import java.util.Random;

class AlienPatrolMovementComponent implements MovementComponent {

    private AlienLaserSpawner alienLaserSpawner;
    private Random mShotRandom = new Random();

    AlienPatrolMovementComponent(AlienLaserSpawner als){
        alienLaserSpawner = als;
    }

    @Override
    public boolean move(long fps, Transform t,
                        Transform playerTransform) {

        final int TAKE_SHOT = 0; // Arbitrary
        // 1 in 100 chance of shot being fired
        // when in line with player
        final int SHOT_CHANCE = 100;

        // Where is the player
        PointF playerLocation = playerTransform.getLocation();

        // The top of the screen
        final float MIN_VERTICAL_BOUNDS = 0;
        // The width and height of the screen
        float screenX = t.getmScreenSize().x;
        float screenY = t.getmScreenSize().y;

        // How far ahead can the alien see?
        float mSeeingDistance = screenX * .5f;

        // Where is the alien?
        PointF loc = t.getLocation();
        // How fast is the alien?
        float speed = t.getSpeed();
        // How tall is the alien
        float height = t.getObjectHeight();

        // Stop the alien going too far away
        float MAX_VERTICAL_BOUNDS = screenY- height;
        final float MAX_HORIZONTAL_BOUNDS = 2 * screenX;
        final float MIN_HORIZONTAL_BOUNDS = 2 * -screenX;

        // Adjust the horizontal speed relative
        // to the player's heading
        // Default is no horizontal speed adjustment
        float horizontalSpeedAdjustmentRelativeToPlayer = 0 ;
        // How much to speed up or slow down relative
        // to player's heading
        float horizontalSpeedAdjustmentModifier = .8f;

        // Can the Alien "see" the player? If so make speed relative
        if (Math.abs(loc.x - playerLocation.x)
                < mSeeingDistance) {
            if(playerTransform.getFacingRight()
                    != t.getFacingRight()){

                // Facing a different way speed up the alien
               horizontalSpeedAdjustmentRelativeToPlayer =
                       speed * horizontalSpeedAdjustmentModifier;
            } else{
                // Facing the same way slow it down
                horizontalSpeedAdjustmentRelativeToPlayer =
                        -(speed * horizontalSpeedAdjustmentModifier);
            }
        }

        // Move horizontally taking into account the speed modification
        if(t.headingLeft()){
            loc.x -= (speed + horizontalSpeedAdjustmentRelativeToPlayer) / fps;
            // Turn the ship around when it reaches the
            // extent of its horizontal patrol area
            if(loc.x < MIN_HORIZONTAL_BOUNDS){
                loc.x = MIN_HORIZONTAL_BOUNDS;
                t.headRight();
            }
        }
        else{
            loc.x += (speed + horizontalSpeedAdjustmentRelativeToPlayer) / fps;
            // Turn the ship around when it reaches the
            // extent of its horizontal patrol area
            if(loc.x > MAX_HORIZONTAL_BOUNDS){
                loc.x = MAX_HORIZONTAL_BOUNDS;
                t.headLeft();
            }
        }

        // Vertical speed remains same,
        // Not affected by speed adjustment
        if(t.headingDown()){
            loc.y += (speed) / fps;
            if(loc.y > MAX_VERTICAL_BOUNDS){
                t.headUp();
            }
        }
        else{
            loc.y -= (speed) / fps;
            if(loc.y < MIN_VERTICAL_BOUNDS){
                t.headDown();
            }
        }
        // Update the collider
        t.updateCollider();

       // Shoot if the alien within a ships height above,
        // below, or in line with the player?
        // This could be a hit or a miss
        if(mShotRandom.nextInt(SHOT_CHANCE) == TAKE_SHOT) {
            if (Math.abs(playerLocation.y - loc.y) < height) {
                // is the alien facing the right direction
                // and close enough to the player
                if ((t.getFacingRight() && playerLocation.x > loc.x
                        || !t.getFacingRight() && playerLocation.x < loc.x)
                        && Math.abs(playerLocation.x - loc.x) < screenX) {
                    // Fire!
                    alienLaserSpawner.spawnAlienLaser(t);
                }

            }
        }

        return true;
    }
}
