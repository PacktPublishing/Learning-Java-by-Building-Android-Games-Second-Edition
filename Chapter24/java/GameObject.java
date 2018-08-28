package com.gamecodeschool.c24platformer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.gamecodeschool.c24platformer.GOSpec.GameObjectSpec;

class GameObject {

    private Transform mTransform;


    private boolean mActive = true;
    private String mTag;

    private GraphicsComponent mGraphicsComponent;
    private UpdateComponent mUpdateComponent;

    void setGraphics(GraphicsComponent g,
                     Context c,
                     GameObjectSpec spec,
                     PointF objectSize,
                     int pixelsPerMetre) {

        mGraphicsComponent = g;
        g.initialize(c, spec, objectSize, pixelsPerMetre);
    }

    void setMovement(UpdateComponent m) {
        mUpdateComponent = m;
    }



    void setPlayerInputTransform(PlayerInputComponent s) {
        s.setTransform(mTransform);
    }


    void setTransform(Transform t) {
        mTransform = t;
    }

    void draw(Canvas canvas, Paint paint, Camera cam) {
        mGraphicsComponent.draw(canvas,
                paint,
                mTransform, cam);
    }

    void update(long fps, Transform playerTransform) {
        mUpdateComponent.update(fps,
                mTransform,
                playerTransform);
    }

    boolean checkActive() {
        return mActive;
    }

    String getTag() {
        return mTag;
    }

    void setInactive() {
        mActive = false;
    }

    Transform getTransform() {
        return mTransform;
    }

    void setTag(String tag) {
        mTag = tag;
    }
}
