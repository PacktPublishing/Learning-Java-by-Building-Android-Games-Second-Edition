package com.gamecodeschool.c24platformer;

import android.graphics.PointF;
import android.graphics.RectF;

public class Transform {
    RectF mCollider;
    private PointF mLocation;
    private float mSpeed;
    private float mObjectHeight;
    private float mObjectWidth;
    private PointF mStartingPosition;
    private boolean mHeadingUp = false;
    private boolean mHeadingDown = false;

    private boolean mFacingRight = true;
    private boolean mHeadingLeft = false;
    private boolean mHeadingRight = false;

    Transform(float speed, float objectWidth,
              float objectHeight, PointF startingLocation) {

        mCollider = new RectF();
        mSpeed = speed;
        mObjectHeight = objectHeight;
        mObjectWidth = objectWidth;
        mLocation = startingLocation;

        // This tells movable blocks their starting position
        mStartingPosition = new PointF(mLocation.x, mLocation.y);
    }


    public void updateCollider() {
        mCollider.top = mLocation.y;
        mCollider.left = mLocation.x ;
        mCollider.bottom = (mCollider.top + mObjectHeight);
        mCollider.right = (mCollider.left + mObjectWidth);
   }


    public RectF getCollider() {
        return mCollider;
    }

    void headUp() {
        mHeadingUp = true;
        mHeadingDown = false;
    }

    void headDown() {
        mHeadingDown = true;
        mHeadingUp = false;
    }

    boolean headingUp() {
        return mHeadingUp;
    }

    boolean headingDown() {
        return mHeadingDown;
    }

    float getSpeed() {
        return mSpeed;
    }

    PointF getLocation() {
        return mLocation;
    }

    PointF getSize() {
        return new PointF((int) mObjectWidth, (int) mObjectHeight);
    }

    void headRight() {
        mHeadingRight = true;
        mHeadingLeft = false;
        mFacingRight = true;

    }

    void headLeft() {
        mHeadingLeft = true;
        mHeadingRight = false;
        mFacingRight = false;
    }

    boolean headingRight() {
        return mHeadingRight;
    }

    boolean headingLeft() {
        return mHeadingLeft;
    }

    void stopHorizontal() {
        mHeadingLeft = false;
        mHeadingRight = false;
    }

    void stopMovingLeft() {
        mHeadingLeft = false;
    }

    void stopMovingRight() {
        mHeadingRight = false;
    }

    boolean getFacingRight() {
        return mFacingRight;
    }

    PointF getStartingPosition(){
        return mStartingPosition;
    }
}
