package com.gamecodeschool.c24platformer;


import android.graphics.PointF;

class BackgroundTransform extends Transform {

    private float xClip;
    private boolean reversedFirst = false;

    public BackgroundTransform(float speed,
                               float objectWidth,
                               float objectHeight,
                               PointF startingLocation) {

        super(speed, objectWidth,
                objectHeight,
                startingLocation);
    }

    boolean getReversedFirst() {
        return reversedFirst;
    }

    void flipReversedFirst() {
        reversedFirst = !reversedFirst;
    }

    float getXClip() {
        return xClip;
    }

    void setXClip(float newXClip) {
        xClip = newXClip;
    }
}
