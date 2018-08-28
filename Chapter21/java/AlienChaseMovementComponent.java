package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;
import java.util.Random;

class AlienChaseMovementComponent implements MovementComponent {

    private Random mShotRandom = new Random();

    // Gives this class the ability to tell the game engine
    // to spawn a laser
    private AlienLaserSpawner alienLaserSpawner;

    AlienChaseMovementComponent(AlienLaserSpawner als){
        alienLaserSpawner = als;
    }

    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {

        // 1 in 100 chance of shot being fired when in line with player
        final int TAKE_SHOT=0; // Arbitrary
        final int SHOT_CHANCE = 100;

        // How high is the screen?
        float screenWidth = t.getmScreenSize().x;
        // Where is the player?
        PointF playerLocation = playerTransform.getLocation();

        // How tall is the ship
        float height = t.getObjectHeight();
        // Is the ship facing right?
        boolean facingRight =t.getFacingRight();
        // How far off before the ship doesn't bother chasing?
        float mChasingDistance = t.getmScreenSize().x / 3f;
        // How far can the AI see?
        float mSeeingDistance = t.getmScreenSize().x / 1.5f;
        // Where is the ship?
        PointF location = t.getLocation();
        // How fast is the ship?
        float speed = t.getSpeed();

        // Relative speed difference with player
        float verticalSpeedDifference = .3f;
        float slowDownRelativeToPlayer = 1.8f;
        // Prevent the ship locking on too accurately
        float verticalSearchBounce = 20f;

        // move in the direction of the player
        // but relative to the player's direction of travel
        if (Math.abs(location.x - playerLocation.x)
                > mChasingDistance) {

            if (location.x < playerLocation.x) {
                t.headRight();
            } else if (location.x > playerLocation.x) {
                t.headLeft();
            }
        }

        // Can the Alien "see" the player? If so try and align vertically
        if (Math.abs(location.x - playerLocation.x)
                <= mSeeingDistance) {

            // Use a cast to get rid of unnecessary floats that make ship judder
            if ((int) location.y - playerLocation.y
                    < -verticalSearchBounce) {

                t.headDown();
            } else if ((int) location.y - playerLocation.y
                    > verticalSearchBounce) {

                t.headUp();
            }

            // Compensate for movement relative to player-
            // but only when in view.
            // Otherwise alien will disappear miles off to one side
            if(!playerTransform.getFacingRight()){
                location.x += speed * slowDownRelativeToPlayer / fps;
            } else{
                location.x -=  speed * slowDownRelativeToPlayer / fps;
            }
        }
        else{
            // stop vertical movement otherwise alien will
            // disappear off the top or bottom
            t.stopVertical();
        }

        // Moving vertically is slower than horizontally
        // Change this to make game harder
        if(t.headingDown()){
            location.y += speed * verticalSpeedDifference / fps;
        }
        else if(t.headingUp()){
            location.y -= speed * verticalSpeedDifference / fps;
        }

        // Move horizontally
        if(t.headingLeft()){
            location.x -= (speed) / fps;
        }
        if(t.headingRight()){
            location.x += (speed) / fps;
        }

        // Update the collider
        t.updateCollider();

        // Shoot if the alien is within a ships height above,
        // below, or in line with the player?
        // This could be a hit or a miss
        if(mShotRandom.nextInt(SHOT_CHANCE) == TAKE_SHOT) {
            if (Math.abs(playerLocation.y - location.y) < height) {
                // Is the alien facing the right direction
                // and close enough to the player
                if ((facingRight && playerLocation.x > location.x
                        || !facingRight && playerLocation.x < location.x)
                        && Math.abs(playerLocation.x - location.x)
                        < screenWidth) {
                    // Fire!
                    alienLaserSpawner.spawnAlienLaser(t);
                }

            }
        }

        return true;
    }
}
