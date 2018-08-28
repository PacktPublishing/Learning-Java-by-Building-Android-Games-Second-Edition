package com.gamecodeschool.c25platformer;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

class PlayerTransform extends Transform {

    private ArrayList<RectF> mColliders;

    private final float TENTH = .1f;
    private final float HALF = .5f;
    private final float THIRD = .3f;
    private final float FIFTH = .2f;
    private final float FEET_PROTRUSION = 1.2f;

    private RectF mHeadRectF = new RectF();
    private RectF mRightRectF = new RectF();
    private RectF mFeetRectF = new RectF();
    private RectF mLeftRectF = new RectF();

    private boolean mJumpTriggered = false;
    private boolean mBumpedHeadTriggered = false;

    private boolean mGrounded;

    PlayerTransform(float speed,
                    float objectWidth,
                    float objectHeight,
                    PointF startingLocation) {

        super(speed, objectWidth,
                objectHeight,
                startingLocation);

        mColliders = new ArrayList<RectF>();
        // Load up the colliders ArrayList with
        // player specific colliders
        mColliders.add(mFeetRectF);
        mColliders.add(mHeadRectF);
        mColliders.add(mRightRectF);
        mColliders.add(mLeftRectF);
    }

    public ArrayList<RectF> getColliders(){
        updateColliders();
        return mColliders;
    }

    public void updateColliders(){

        PointF location = getLocation();
        float objectHeight = getSize().y;
        float objectWidth = getSize().x;

        // Feet
        mColliders.get(0).left = location.x
                + (objectWidth * THIRD);

        mColliders.get(0).top = location.y
                + objectHeight - (objectHeight
                * TENTH);

        mColliders.get(0).right = location.x
                + objectWidth - (objectWidth
                * THIRD);

        mColliders.get(0).bottom = location.y
                + objectHeight + (objectHeight
                * FEET_PROTRUSION);

        // Head
        mColliders.get(1).left = location.x
                + ((objectWidth * THIRD));

        mColliders.get(1).top = location.y;
        mColliders.get(1).right = location.x
                +objectWidth - (objectWidth
                * THIRD);

        mColliders.get(1).bottom = location.y
                + (objectHeight * TENTH);

        // Right
        mColliders.get(2).left = location.x
                + objectWidth - (objectWidth
                * TENTH);

        mColliders.get(2).top = location.y
                + (objectHeight * THIRD);
        mColliders.get(2).right = location.x
                + objectWidth;

        mColliders.get(2).bottom = location.y
                + (objectHeight - (objectHeight
                * HALF));

        // Left
        mColliders.get(3).left = location.x;
        mColliders.get(3).top = location.y
                + (objectHeight * FIFTH);
        mColliders.get(3).right = location.x
                + (objectWidth * TENTH);

        mColliders.get(3).bottom = location.y
                + (objectHeight - (objectHeight
                * FIFTH));
    }

    // Called by handle input component to
    // let us know a jump has been triggered
    void triggerJump() {
        mJumpTriggered = true;
    }

    // Called by movement component to let transform
    // know that movement component is aware
    // jump was triggered
    void handlingJump() {
        mJumpTriggered = false;
    }

    // Used by movement component to find
    // out if jump has been triggered
    boolean jumpTriggered() {
        return mJumpTriggered;
    }

    void setNotGrounded(){
        mGrounded=false;
    }

    void triggerBumpedHead() {
        mBumpedHeadTriggered = true;
    }

    void handlingBumpedHead() {
        mBumpedHeadTriggered = false;
    }

    boolean bumpedHead() {
        return mBumpedHeadTriggered;
    }

    void notGrounded() {
        mGrounded = false;
    }

    void grounded() {
        mGrounded = true;
    }

    boolean isGrounded() {
        return mGrounded;
    }
}
