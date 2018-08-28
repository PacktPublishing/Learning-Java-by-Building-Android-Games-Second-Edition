package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;

class LaserSpawnComponent implements SpawnComponent {
    @Override
    public void spawn(Transform playerTransform, Transform t) {

        PointF startPosition =
                playerTransform.getFiringLocation(t.getSize().x);

        t.setLocation((int)startPosition.x, (int)startPosition.y);

        if(playerTransform.getFacingRight()){
           t.headRight();
        }
         else{
            t.headLeft();
        }

    }
}
