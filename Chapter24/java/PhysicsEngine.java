package com.gamecodeschool.c24platformer;

import java.util.ArrayList;

class PhysicsEngine {

    void update(long fps, ArrayList<GameObject> objects, GameState gs) {
        for (GameObject object : objects) {
            object.update(fps,
                    objects.get(LevelManager.PLAYER_INDEX)
                            .getTransform());
        }

        detectCollisions(gs, objects);
    }

    private void detectCollisions(GameState gs, ArrayList<GameObject> objects) {
        // More code here soon
    }
}
