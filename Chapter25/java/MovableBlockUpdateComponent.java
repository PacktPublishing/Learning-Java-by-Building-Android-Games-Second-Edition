package com.gamecodeschool.c25platformer;

import android.graphics.PointF;

class MovableBlockUpdateComponent implements UpdateComponent {

    @Override
    public void update(long fps,
                       Transform t,
                       Transform playerTransform) {

        PointF location = t.getLocation();
        if (t.headingUp()) {
            location.y -= t.getSpeed() / fps;
        } else if (t.headingDown()) {
            location.y += t.getSpeed() / fps;
        } else {
            // Must be first update of the game
            // so start with going down
            t.headDown();
        }

        // Check if the platform needs
        // to change direction
        if (t.headingUp() && location.y <=
                t.getStartingPosition().y) {
            // Back at the start
            t.headDown();
        } else if (t.headingDown() && location.y >=
                (t.getStartingPosition().y
                        + t.getSize().y * 10)) {
            // Moved ten times the height downwards
            t.headUp();
        }

        // Update the colliders with the new position
        t.updateCollider();
    }
}
