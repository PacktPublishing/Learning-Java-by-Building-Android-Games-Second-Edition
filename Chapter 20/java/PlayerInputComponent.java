package com.gamecodeschool.c20scrollingshooter;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

class PlayerInputComponent implements InputComponent, InputObserver {

    private Transform mTransform;
    private PlayerLaserSpawner mPLS;

    PlayerInputComponent(GameEngine ger) {
        ger.addObserver(this);
        mPLS = ger;
    }

    @Override
    public void setTransform(Transform transform) {
        mTransform = transform;
    }

    // Required method of InputObserver interface called from the onTouchEvent method
    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> buttons) {
       // In each MotionEvent object, every active pointer is present. Therefore looping through them all for an event on only one of them is obviously wrong.
        // The getActionIndex returns the index in the array of the pointer that performed/trigged the action/method call. So using getX(i) and getY(i) only gets a true result
        // on the button that was actually pressed/removed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:
                if (buttons.get(HUD.UP).contains(x,y)
                        || buttons.get(HUD.DOWN).contains(x,y)) {

                    // Player has released either up or down
                    mTransform.stopVertical();
                }
                break;

            case MotionEvent.ACTION_DOWN:
                if (buttons.get(HUD.UP).contains(x,y)) {
                    // Player has pressed up
                    mTransform.headUp();
                } else if (buttons.get(HUD.DOWN).contains(x,y)) {
                    // Player has pressed down
                    mTransform.headDown();
                } else if (buttons.get(HUD.FLIP).contains(x,y)) {
                    // Player has released the flip button
                    mTransform.flip();
                } else if (buttons.get(HUD.SHOOT).contains(x,y)) {
                    mPLS.spawnPlayerLaser(mTransform);
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                if (buttons.get(HUD.UP).contains(x, y)
                        || buttons.get(HUD.DOWN).contains(x, y)) {
                    // Player has released either up or down
                    mTransform.stopVertical();
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (buttons.get(HUD.UP).contains(x, y)) {
                    // Player has pressed up
                    mTransform.headUp();
                } else if (buttons.get(HUD.DOWN).contains(x, y)) {
                    // Player has pressed down
                    mTransform.headDown();
                } else if (buttons.get(HUD.FLIP).contains(x, y)) {
                    // Player has released the flip button
                    mTransform.flip();
                } else if (buttons.get(HUD.SHOOT).contains(x, y)) {
                    mPLS.spawnPlayerLaser(mTransform);
                }
                break;
        }
    }
}
