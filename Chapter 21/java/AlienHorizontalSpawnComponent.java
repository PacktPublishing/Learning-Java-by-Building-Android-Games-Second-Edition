package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;
import java.util.Random;

class AlienHorizontalSpawnComponent implements SpawnComponent {
    @Override
    public void spawn(Transform playerLTransform, Transform t) {
        // Get the screen size
        PointF ss = t.getmScreenSize();

        // Spawn just off screen randomly left or right
        Random random = new Random();
        boolean left = random.nextBoolean();
        // How far away?
        float distance =  random.nextInt(2000)
                + t.getmScreenSize().x;

        // Generate a height to spawn at where
        // the entire ship is vertically on-screen
        float spawnHeight = random.nextFloat()
                * ss.y - t.getSize().y;

        // Spawn the ship
        if(left){
            t.setLocation(-distance, spawnHeight);
            t.headRight();
        }else{
            t.setLocation(distance, spawnHeight);
            t.headingLeft();
        }

    }
}
