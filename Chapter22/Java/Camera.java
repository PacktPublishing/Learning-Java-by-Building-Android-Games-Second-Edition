package com.gamecodeschool.c22platformer;

import android.graphics.PointF;
import android.graphics.Rect;

class Camera {

    private PointF mCurrentCameraWorldCentre;
    private Rect mConvertedRect;
    private int mPixelsPerMetre;
    private int mScreenCentreX;
    private int mScreenCentreY;

    Camera(int screenXResolution, int screenYResolution){
        // Locate the centre of the screen
        mScreenCentreX = screenXResolution / 2;
        mScreenCentreY = screenYResolution / 2;

        // How many metres of world space does
        // the screen width show
        // Change this value to zoom in and out
        final int pixelsPerMetreToResolutionRatio = 48;
        mPixelsPerMetre = screenXResolution /
                pixelsPerMetreToResolutionRatio;

        mConvertedRect = new Rect();
        mCurrentCameraWorldCentre = new PointF();
    }

    int getPixelsPerMetreY(){
        return mPixelsPerMetre;
    }

    int getyCentre(){
        return mScreenCentreY;
    }

    float getCameraWorldCentreY(){
        return mCurrentCameraWorldCentre.y;
    }

    // Set the camera to the player. Called each frame
    void setWorldCentre(PointF worldCentre){
        mCurrentCameraWorldCentre.x  = worldCentre.x;
        mCurrentCameraWorldCentre.y  = worldCentre.y;
    }

    int getPixelsPerMetre(){
        return mPixelsPerMetre;
    }

    // Return a Rect of the screen coordinates relative to a world location
    Rect worldToScreen(float objectX,
                       float objectY,
                       float objectWidth,
                       float objectHeight){

        int left = (int) (mScreenCentreX -
                ((mCurrentCameraWorldCentre.x - objectX)
                        * mPixelsPerMetre));

        int top =  (int) (mScreenCentreY -
                ((mCurrentCameraWorldCentre.y - objectY)
                        * mPixelsPerMetre));

        int right = (int) (left + (objectWidth * mPixelsPerMetre));
        int bottom = (int) (top + (objectHeight * mPixelsPerMetre));
        mConvertedRect.set(left, top, right, bottom);
        return mConvertedRect;
    }

}

